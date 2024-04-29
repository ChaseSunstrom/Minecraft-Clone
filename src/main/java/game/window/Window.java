package game.window;

import game.events.*;
import org.lwjgl.glfw.*;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11C.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long m_GlfwWindow;
    private final WindowData m_WindowData;

    public Window(String title, int width, int height) {
        m_WindowData = new WindowData(title, width, height, Window::eventCallback);
        init();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        m_GlfwWindow = glfwCreateWindow(m_WindowData.getWidth(), m_WindowData.getHeight(), m_WindowData.getTitle(), NULL, NULL);

        if (m_GlfwWindow == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(m_GlfwWindow, new GLFWKeyCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2, int i3) {
                keyCallback(l, i, i1, i2, i3);
            }
        });

        glfwSetCursorPosCallback(m_GlfwWindow, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                cursorPosCallback(window, xpos, ypos);
            }
        });

        glfwSetMouseButtonCallback(m_GlfwWindow, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long l, int i, int i1, int i2) {
                mouseButtonCallback(l, i, i1, i2);
            }
        });

        glfwSetScrollCallback(m_GlfwWindow, new GLFWScrollCallback() {
            @Override
            public void invoke(long l, double v, double v1) {
                scrollCallback(l, v, v1);
            }
        });
        glfwSetFramebufferSizeCallback(m_GlfwWindow, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long l, int i, int i1) {
                framebufferSizeCallback(l, i, i1);
            }
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(m_GlfwWindow);
        glfwSwapInterval(1); // Enable v-sync

        // Make the window visible
        glfwShowWindow(m_GlfwWindow);
    }

    public static void terminate() {
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public static void eventCallback(Event event) {
        EventSubscription.publishToTopic(SubscriptionTopic.WINDOW_EVENT_TOPIC, event);
    }

    public void framebufferSizeCallback(long window, int width, int height) {
        glViewport(0, 0, width, height);
    }

    public void keyCallback(long window, int key, int scancode, int action, int mods) {

        switch (action) {
            case GLFW_PRESS:
                m_WindowData.invokeCallback(new KeyPressedEvent(key));
                break;
            case GLFW_RELEASE:
                m_WindowData.invokeCallback(new KeyReleasedEvent(key));
                break;
            case GLFW_REPEAT:
                m_WindowData.invokeCallback(new KeyRepeatedEvent(key));
                break;
        }
    }

    public void mouseButtonCallback(long window, int button, int action, int mods) {
        m_WindowData.invokeCallback(new MousePressedEvent(button, action, mods));
    }

    public void cursorPosCallback(long window, double xpos, double ypos) {
        m_WindowData.invokeCallback(new MouseMovedEvent(xpos, ypos));
    }

    public void scrollCallback(long window, double xoffset, double yoffset) {
        m_WindowData.invokeCallback(new MouseScrolledEvent(0, 0));
    }

    public long getGlfwWindow() {
        return m_GlfwWindow;
    }

    public WindowData getWindowData() {
        return m_WindowData;
    }
}
