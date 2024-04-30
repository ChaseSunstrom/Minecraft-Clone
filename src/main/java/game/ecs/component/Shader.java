package game.ecs.component;

import org.lwjgl.opengl.GL20;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Shader {
    private final int m_ProgramID;

    public Shader(String vertexShaderPath, String fragmentShaderPath) throws Exception {
        m_ProgramID = GL20.glCreateProgram();
        int vertexShaderId = compileShader(vertexShaderPath, GL20.GL_VERTEX_SHADER);
        int fragmentShaderId = compileShader(fragmentShaderPath, GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(m_ProgramID, vertexShaderId);
        GL20.glAttachShader(m_ProgramID, fragmentShaderId);

        GL20.glLinkProgram(m_ProgramID);
        if (GL20.glGetProgrami(m_ProgramID, GL20.GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(m_ProgramID, 1024));
        }

        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
    }

    private int compileShader(String filePath, int type) throws Exception {
        int shaderId = GL20.glCreateShader(type);
        String shaderCode = Files.readAllLines(Paths.get(filePath)).stream().collect(Collectors.joining("\n"));
        GL20.glShaderSource(shaderId, shaderCode);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024));
        }

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(m_ProgramID);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (m_ProgramID != 0) {
            GL20.glDeleteProgram(m_ProgramID);
        }
    }
}
