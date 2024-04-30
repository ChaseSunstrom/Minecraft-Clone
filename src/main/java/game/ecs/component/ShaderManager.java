package game.ecs.component;

import java.util.HashMap;

public class ShaderManager {

    // Keys are concated names of the shader paths
    private HashMap<String, Shader> m_Shaders;

    public ShaderManager() {
        m_Shaders = new HashMap<>();
    }

    public void addShader(String name, Shader shader) {
        m_Shaders.put(name, shader);
    }

    public Shader createShader(String vertexPath, String fragmentPath) throws Exception {
        String name = vertexPath + fragmentPath;
        Shader shader = new Shader(vertexPath, fragmentPath);
        m_Shaders.put(name, shader);
        return shader;
    }

    public Shader getShader(String name) {
        return m_Shaders.get(name);
    }

    public void removeShader(String name) {
        m_Shaders.remove(name);
    }

    public void clear() {
        m_Shaders.clear();
    }

    public int size() {
        return m_Shaders.size();
    }

    public boolean contains(String name) {
        return m_Shaders.containsKey(name);
    }
}
