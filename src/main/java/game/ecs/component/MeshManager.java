package game.ecs.component;

import game.util.MathData;

import java.util.ArrayList;
import java.util.HashMap;

public class MeshManager {

    private HashMap<String, Mesh> m_Meshes;

    public MeshManager() {
        m_Meshes = new HashMap<>();
    }

    public Mesh createMesh(String name, ArrayList<MathData.Vertex> vertices) {
        Mesh mesh = new Mesh(vertices);
        m_Meshes.put(name, mesh);
        return mesh;
    }

    public Mesh createMesh(String name, ArrayList<MathData.Vertex> vertices, ArrayList<Integer> indices) {
        Mesh mesh = new Mesh(vertices, indices);
        m_Meshes.put(name, mesh);
        return mesh;
    }

    public Mesh getMesh(String name) {
        return m_Meshes.get(name);
    }

    public void removeMesh(String name) {
        m_Meshes.remove(name);
    }

    public void clear() {
        m_Meshes.clear();
    }

    public int size() {
        return m_Meshes.size();
    }

    public boolean contains(String name) {
        return m_Meshes.containsKey(name);
    }

    public boolean isEmpty() {
        return m_Meshes.isEmpty();
    }

    public ArrayList<String> getMeshNames() {
        return new ArrayList<>(m_Meshes.keySet());
    }
}
