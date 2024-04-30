package game.window;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private Instancer m_Instancer;

    public Renderer() {
        m_Instancer = new Instancer();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        m_Instancer.render();
    }
}
