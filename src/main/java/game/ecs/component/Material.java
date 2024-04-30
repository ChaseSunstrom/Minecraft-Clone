package game.ecs.component;

import game.util.MathData;

public class Material {

    private final MathData.Vec4 m_Color;
    private final int m_Diffuse;
    private final int m_Specular;
    private final int m_Ambient;
    private final float m_Shininess;
    private final String m_Texture;
    private final Shader m_Shader;

    private Material(Builder builder) {
        m_Color = builder.color;
        m_Diffuse = builder.diffuse;
        m_Specular = builder.specular;
        m_Ambient = builder.ambient;
        m_Shininess = builder.shininess;
        m_Texture = builder.texture;
        m_Shader = builder.shader;
    }

    public static class Builder {
        private MathData.Vec4 color = new MathData.Vec4(1.0f, 1.0f, 1.0f, 1.0f); // Default color
        private int diffuse = 0;
        private int specular = 0;
        private int ambient = 0;
        private float shininess = 0.0f;
        private String texture = "";
        private Shader shader = new Shader("shaders/default.vert", "shaders/default.frag");

        public Builder() throws Exception {
        }

        public Builder color(MathData.Vec4 color) {
            this.color = color;
            return this;
        }

        public Builder diffuse(int diffuse) {
            this.diffuse = diffuse;
            return this;
        }

        public Builder specular(int specular) {
            this.specular = specular;
            return this;
        }

        public Builder ambient(int ambient) {
            this.ambient = ambient;
            return this;
        }

        public Builder shininess(float shininess) {
            this.shininess = shininess;
            return this;
        }

        public Builder texture(String texture) {
            this.texture = texture;
            return this;
        }

        public Builder shader(Shader shader) {
            this.shader = shader;
            return this;
        }

        public Material build() {
            return new Material(this);
        }
    }

    public MathData.Vec4 getColor() {
        return m_Color;
    }

    public int getDiffuse() {
        return m_Diffuse;
    }

    public int getSpecular() {
        return m_Specular;
    }

    public int getAmbient() {
        return m_Ambient;
    }

    public float getShininess() {
        return m_Shininess;
    }

    public String getTexture() {
        return m_Texture;
    }

    public Shader getShader() {
        return m_Shader;
    }


}
