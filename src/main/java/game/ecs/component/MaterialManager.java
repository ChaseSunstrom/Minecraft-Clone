package game.ecs.component;

import java.util.HashMap;

public class MaterialManager {

    private HashMap<String, Material> m_Materials;
    private TextureManager m_TextureManager;
    private ShaderManager m_ShaderManager;

    public MaterialManager() {
        m_Materials = new HashMap<>();
        m_TextureManager = new TextureManager();
        m_ShaderManager = new ShaderManager();
    }

    public void addMaterial(String name, Material material) {
        m_Materials.put(name, material);
    }

    public Material createMaterial(String name, String vertexPath, String fragmentPath) {
        try {
            m_ShaderManager.createShader(vertexPath, fragmentPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Material material = new Material(name, m_ShaderManager.getShader(vertexPath + fragmentPath));
        m_Materials.put(name, material);
        return material;
    }

    public Material createMaterial(String name, String vertexPath, String fragmentPath, String textureName, String texturePath) {
        try {
            m_ShaderManager.createShader(vertexPath, fragmentPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m_TextureManager.createTexture(textureName, texturePath);
        Material material = new Material(name, m_ShaderManager.getShader(vertexPath + fragmentPath), textureName);
        m_Materials.put(name, material);
        return material;
    }

    public Material getMaterial(String materialName) {
        return m_Materials.get(materialName);
    }

    public void removeMaterial(String name) {
        m_Materials.remove(name);
    }

    public TextureManager getTextureManager() {
        return m_TextureManager;
    }

    public ShaderManager getShaderManager() {
        return m_ShaderManager;
    }
}
