package game.window;

import game.Game;
import game.ecs.ECS;
import game.ecs.component.*;
import game.ecs.entity.Entity;
import game.events.EntityCreatedEvent;
import game.events.EntityDestroyedEvent;
import game.events.EntityUpdatedEvent;
import game.events.Event;
import game.util.Observer;
import game.util.Pair;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL31C.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL31C.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class Instancer implements Observer {

    private class Transforms {

        private ArrayList<Transform> m_Transforms;
        private HashMap<Entity, Transform> m_EntityTransforms;
        private final int m_VBO;

        public Transforms() {
            m_Transforms = new ArrayList<>();
            m_EntityTransforms = new HashMap<>();
            m_VBO = GL15.glGenBuffers();
        }

        public void prepare() {
            // Bind the buffer
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_VBO);

            // Create a FloatBuffer to hold all the transformation matrices
            // Assuming each Transform class has a method to get the matrix as an array of floats
            FloatBuffer buffer = MemoryUtil.memAllocFloat(m_Transforms.size() * 16); // 16 floats per 4x4 matrix

            for (Transform transform : m_Transforms) {
                buffer.put(transform.getTransformArray()); // getMatrix() should return float[16]
            }
            buffer.flip();

            // Send the buffer data to the GPU
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);

            // Set up attribute pointers for matrix (4 vec4 attributes)
            int stride = 16 * Float.BYTES; // Stride between matrices in the buffer
            int offset = 0;
            for (int i = 0; i < 4; i++) { // Setting up 4 attributes to handle the 4x4 matrix
                glEnableVertexAttribArray(i);
                glVertexAttribPointer(i, 4, GL15.GL_FLOAT, false, stride, offset);
                glVertexAttribDivisor(i, 1); // Tell OpenGL this is instanced data
                offset += 4 * Float.BYTES; // Move to the next vec4 in the matrix
            }

            // Unbind the buffer when done to avoid unintended modifications
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

            // Free the allocated memory for the buffer
            MemoryUtil.memFree(buffer);
        }

        public void addTransform(Transform transform) {
            m_Transforms.add(transform);
        }

        public void addEntityTransform(Entity entity, Transform transform) {
            m_Transforms.add(transform);
            m_EntityTransforms.put(entity, transform);
        }

        public void updateTransforms() {
            m_Transforms.clear();
            for (Transform transform : m_EntityTransforms.values()) {
                m_Transforms.add(transform);
            }
        }

        public Transform getTransform(Entity entity) {
            return m_EntityTransforms.get(entity);
        }

    }

    private HashMap<String, HashMap<String, Transforms>> m_Renderables;
    private HashMap<Entity, Pair<String, String>> m_EntityMeshMaterials;

    public Instancer() {
        super();
        m_Renderables = new HashMap<>();
        m_EntityMeshMaterials = new HashMap<>();
    }

    private void addRenderable(Entity entity, String meshName, String materialName, Transform transform) {
        m_Renderables.computeIfAbsent(meshName, k -> new HashMap<>())
                .computeIfAbsent(materialName, k -> new Transforms())
                .addEntityTransform(entity, transform);
        m_EntityMeshMaterials.put(entity, new Pair<>(meshName, materialName));
    }

    private void updateRenderable(ECS ecs, Entity e) {
        Pair<String, String> pair = m_EntityMeshMaterials.get(e);
        Transforms transforms = m_Renderables.get(pair.getKey()).get(pair.getValue());
        Transform transform = ecs.getComponent(Transform.class, e);
        transforms.addEntityTransform(e, transform);
        transforms.updateTransforms();
    }

    private void removeRenderable(Entity entity) {
        Pair<String, String> pair = m_EntityMeshMaterials.remove(entity);
        if (pair != null) {
            Map<String, Transforms> materials = m_Renderables.get(pair.getKey());
            if (materials != null) {
                Transforms transforms = materials.get(pair.getValue());
                if (transforms != null) {
                    transforms.m_EntityTransforms.remove(entity);
                    if (transforms.m_EntityTransforms.isEmpty()) {
                        materials.remove(pair.getValue());
                    }
                    if (materials.isEmpty()) {
                        m_Renderables.remove(pair.getKey());
                    }
                }
            }
        }
    }

    private void bindRenderables(String meshName, String materialName) {

        HashMap<String, Transforms> materials = m_Renderables.get(meshName);
        if (materials == null) return;

        Transforms transforms = materials.get(materialName);
        if (transforms == null) return;

        MeshManager meshManager = Game.m_MeshManager;
        MaterialManager materialManager = Game.m_MaterialManager;
        TextureManager textureManager = materialManager.getTextureManager();

        int shader = materialManager.getMaterial(materialName)
                .getShader()
                .getProgramID();

        glUseProgram(shader);

        int projectionMatrixLocation = glGetUniformLocation(shader, "projection");
        int viewMatrixLocation = glGetUniformLocation(shader, "view");

        glUniformMatrix4fv(projectionMatrixLocation, false, Game.m_Camera.getProjectionMatrix());
        glUniformMatrix4fv(viewMatrixLocation, false, Game.m_Camera.getViewMatrix());

        Material material = materialManager.getMaterial(materialName);
        glUniform4fv(glGetUniformLocation(shader, "material.color"), material.getColor().getArray());
        glUniform1i(glGetUniformLocation(shader, "material.diffuse"), material.getDiffuse());
        glUniform1i(glGetUniformLocation(shader, "material.specular"), material.getSpecular());
        glUniform1i(glGetUniformLocation(shader, "material.ambient"), material.getAmbient());
        glUniform1f(glGetUniformLocation(shader, "material.shininess"), material.getShininess());
        glUniform1i(glGetUniformLocation(shader, "material.texture"), materialManager.getTextureManager().getTexture(material.getTexture()).getTextureID());

        glBindVertexArray(meshManager.getMesh(meshName).getVAO());
    }

    @Override
    public void onNotify(ECS ecs, Event event) {

        switch (event.getType()) {
            case ENTITY_CREATED_EVENT:
                EntityCreatedEvent entityCreatedEvent = (EntityCreatedEvent) event;
                Entity e = entityCreatedEvent.getEntity();

                if (ecs.hasComponent(Components.TransformComponent.class, e) &&
                        ecs.hasComponent(Components.MeshComponent.class, e) &&
                        ecs.hasComponent(Components.MaterialComponent.class, e)) {
                    String meshName = ecs.getComponent(Components.MeshComponent.class, e).getMeshName();
                    String materialName = ecs.getComponent(Components.MaterialComponent.class, e).getMaterialName();
                    Transform transform = ecs.getComponent(Components.TransformComponent.class, e);

                    addRenderable(e, meshName, materialName, transform);
                }
                break;
            case ENTITY_UPDATED_EVENT:
                EntityUpdatedEvent entityUpdatedEvent = (EntityUpdatedEvent) event;
                e = entityUpdatedEvent.getEntity();

                updateRenderable(ecs, e);
                break;
            case ENTITY_DESTROYED_EVENT:
                EntityDestroyedEvent entityDestroyedEvent = (EntityDestroyedEvent) event;
                e = entityDestroyedEvent.getEntity();

                removeRenderable(e);
                break;
        }
    }

    public void render() {
        for (Map.Entry<String, HashMap<String, Transforms>> entry : m_Renderables.entrySet()) {
            String meshName = entry.getKey();
            HashMap<String, Transforms> materials = entry.getValue();
            for (Map.Entry<String, Transforms> materialEntry : materials.entrySet()) {
                String materialName = materialEntry.getKey();
                Transforms transforms = materialEntry.getValue();

                bindRenderables(meshName, materialName);
                transforms.prepare();

                Mesh mesh = Game.m_MeshManager.getMesh(meshName);

                if (mesh.getIndices().isEmpty()) {
                    glDrawArraysInstanced(GL_TRIANGLES, 0, mesh.getVertexCount(), transforms.m_Transforms.size());
                } else {
                    glDrawElementsInstanced(GL_TRIANGLES, Game.m_MeshManager.getMesh(meshName).getVertexCount(), GL_UNSIGNED_INT, 0, transforms.m_Transforms.size());
                }
            }
        }
    }
}
