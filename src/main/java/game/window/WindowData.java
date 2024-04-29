package game.window;

import game.events.Event;
import game.events.EventCallback;

public class WindowData {

    private String m_Title;
    private int m_Width, m_Height;
    private EventCallback m_Callback;

    public WindowData(String title, int width, int height, EventCallback callback) {
        m_Title = title;
        m_Width = width;
        m_Height = height;
        m_Callback = callback;
    }

    public void invokeCallback(Event event) {
        if (m_Callback != null) {
            m_Callback.onEvent(event);
        }
    }

    public String getTitle() {
        return m_Title;
    }

    public int getWidth() {
        return m_Width;
    }

    public int getHeight() {
        return m_Height;
    }

    public EventCallback getCallback() {
        return m_Callback;
    }

    public void setCallback(EventCallback callback) {
        m_Callback = callback;
    }
}
