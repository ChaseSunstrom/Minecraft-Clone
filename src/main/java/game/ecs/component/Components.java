package game.ecs.component;

import game.util.MathData;

import java.util.ArrayList;

public class Components {

    public class MeshComponent {

        private Mesh m_Mesh;

        public MeshComponent(ArrayList<MathData.Vertex> vertices) {

            m_Mesh = new Mesh(vertices);

        }

    }

}
