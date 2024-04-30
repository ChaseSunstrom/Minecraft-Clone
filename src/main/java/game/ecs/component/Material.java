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

    public Material() {
        m_Color = new MathData.Vec4(1.0f, 1.0f, 1.0f, 1.0f);
        m_Diffuse = 0;
        m_Specular = 0;
        m_Ambient = 0;
        m_Shininess = 0.0f;
        m_Texture = "";
        m_Shader = null;
    }

    public Material(String name, Shader shader) {
        m_Color = new MathData.Vec4(1.0f, 1.0f, 1.0f, 1.0f);
        m_Diffuse = 0;
        m_Specular = 0;
        m_Ambient = 0;
        m_Shininess = 0.0f;
        m_Texture = "";
        m_Shader = shader;
    }

    public Material(String name, Shader shader, String texture) {
        m_Color = new MathData.Vec4(1.0f, 1.0f, 1.0f, 1.0f);
        m_Diffuse = 0;
        m_Specular = 0;
        m_Ambient = 0;
        m_Shininess = 0.0f;
        m_Texture = texture;
        m_Shader = shader;
    }

    public Material(MathData.Vec4 color, int diffuse, int specular, int ambient, float shininess, Shader shader) {
        m_Color = color;
        m_Diffuse = diffuse;
        m_Specular = specular;
        m_Ambient = ambient;
        m_Shininess = shininess;
        m_Texture = "";
        m_Shader = shader;
    }

    public Material(MathData.Vec4 color, int diffuse, int specular, int ambient, float shininess, String texture) {
        m_Color = color;
        m_Diffuse = diffuse;
        m_Specular = specular;
        m_Ambient = ambient;
        m_Shininess = shininess;
        m_Texture = texture;
        m_Shader = null;
    }

    public Material(MathData.Vec4 color, int diffuse, int specular, int ambient, float shininess) {
        m_Color = color;
        m_Diffuse = diffuse;
        m_Specular = specular;
        m_Ambient = ambient;
        m_Shininess = shininess;
        m_Texture = "";
        m_Shader = null;
    }

    public Material(MathData.Vec4 color, int diffuse, int specular, int ambient, float shininess, String texture, Shader shader) {
        m_Color = color;
        m_Diffuse = diffuse;
        m_Specular = specular;
        m_Ambient = ambient;
        m_Shininess = shininess;
        m_Texture = texture;
        m_Shader = shader;
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
