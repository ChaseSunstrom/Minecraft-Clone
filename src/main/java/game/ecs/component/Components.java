package game.ecs.component;

import game.util.MathData;

import java.util.ArrayList;

public class Components {

    public static class MeshComponent {

        private String m_MeshName;

        public MeshComponent(String name) {
            m_MeshName = name;
        }

        public String getMeshName() {
            return m_MeshName;
        }
    }

    public static class MaterialComponent {

        private String m_MaterialName;

        public MaterialComponent(String name) {
            m_MaterialName = name;
        }

        public String getMaterialName() {
            return m_MaterialName;
        }
    }

    public static class TransformComponent extends Transform {

        public TransformComponent(MathData.Vec3 position, MathData.Vec3 rotation, MathData.Vec3 scale) {
            super(position, rotation, scale);
        }
    }

}
