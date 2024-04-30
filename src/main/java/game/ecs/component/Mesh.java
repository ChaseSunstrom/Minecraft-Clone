package game.ecs.component;

import game.util.MathData;
import java.util.ArrayList;

public class Mesh {

    ArrayList<MathData.Vertex> m_Vertices;
    ArrayList<Integer> m_Indices;

    public Mesh() {
        m_Vertices = new ArrayList<>();
        m_Indices = new ArrayList<>();
    }

    public Mesh(ArrayList<MathData.Vertex> vertices) {
        m_Vertices = vertices;
        m_Indices = new ArrayList<>();
    }

    public Mesh(ArrayList<MathData.Vertex> vertices, ArrayList<Integer> indices) {
        m_Vertices = vertices;
        m_Indices = indices;
    }
}
