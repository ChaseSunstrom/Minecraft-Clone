package game.ecs.component;

import game.util.MathData;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int m_VAO = 0;
    private int m_VBO = 0;
    private int m_EBO = 0;
    private ArrayList<MathData.Vertex> m_Vertices;
    private ArrayList<Integer> m_Indices;


    public Mesh() {
        m_Vertices = new ArrayList<>();
        m_Indices = new ArrayList<>();
        createMesh();
    }

    public Mesh(ArrayList<MathData.Vertex> vertices) {
        m_Vertices = vertices;
        m_Indices = new ArrayList<>();
        createMesh();
    }

    public Mesh(ArrayList<MathData.Vertex> vertices, ArrayList<Integer> indices) {
        m_Vertices = vertices;
        m_Indices = indices;
        createMesh();
    }

    private void createMesh() {
        m_VAO = glGenVertexArrays();
        glBindVertexArray(m_VAO);

        // Create a buffer for vertices
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(m_Vertices.size() * MathData.Vertex.SIZE);
        for (MathData.Vertex vertex : m_Vertices) {
            float[] position = { vertex.m_Position.m_Z, vertex.m_Position.m_Y, vertex.m_Position.m_Z };
            float[] normal = { vertex.m_Normal.m_X, vertex.m_Normal.m_Y, vertex.m_Normal.m_Z };
            float[] texCoords = { vertex.m_TexCoords.m_X, vertex.m_TexCoords.m_Y };
            vertexBuffer.put(position);
            vertexBuffer.put(normal);
            vertexBuffer.put(texCoords);
        }
        vertexBuffer.flip();

        m_VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, m_VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);

        // Create a buffer for indices
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(m_Indices.size());
        for (int index : m_Indices) {
            indexBuffer.put(index);
        }
        indexBuffer.flip();

        m_EBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

        // Set up vertex attributes
        int stride = MathData.Vertex.SIZE * Float.BYTES; // Ensure SIZE is the number of floats per vertex
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0L);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        // Unbind to prevent changes from outside
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        // Free the buffers
        MemoryUtil.memFree(vertexBuffer);
        MemoryUtil.memFree(indexBuffer);
    }

    public int getVAO() {
        return m_VAO;
    }

    public int getVBO() {
        return m_VBO;
    }

    public int getEBO() {
        return m_EBO;
    }

    public ArrayList<MathData.Vertex> getVertices() {
        return m_Vertices;
    }

    public ArrayList<Integer> getIndices() {
        return m_Indices;
    }

    public int getVertexCount() {
        return m_Vertices.size();
    }
}
