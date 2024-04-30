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

}
