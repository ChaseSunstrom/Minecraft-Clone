package game;

import game.ecs.ECS;
import game.ecs.component.MaterialManager;
import game.ecs.component.MeshManager;
import game.window.Camera;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import game.window.Window;

public class Game {

    public static final Camera m_Camera = new Camera(45.0f, 1280.0f / 720.0f, 0.1f, 1000.0f);
    public static final Window m_Window = new Window("Minecraft Clone", 1280, 720);

    public static final ECS m_ECS = new ECS();
    public static final MeshManager m_MeshManager = new MeshManager();
    public static final MaterialManager m_MaterialManager = new MaterialManager();



    public void run() {
        System.out.println("Build Finished!");

        loop();

        long glfwWindow = m_Window.getGlfwWindow();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        while (!m_Window.shouldClose()) {
            m_Window.onUpdate();
        }
    }
}
