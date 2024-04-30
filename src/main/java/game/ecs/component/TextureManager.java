package game.ecs.component;

import java.util.HashMap;

public class TextureManager {

    private HashMap<String, Texture> m_Textures;

    public TextureManager() {
        m_Textures = new HashMap<>();
    }

    public void addTexture(String name, Texture texture) {
        m_Textures.put(name, texture);
    }

    public void createTexture(String name, String path) {
        m_Textures.put(name, new Texture(path));
    }

    public Texture getTexture(String name) {
        return m_Textures.get(name);
    }

    public void removeTexture(String name) {
        m_Textures.remove(name);
    }
}
