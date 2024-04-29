package game.events;

public class WindowResizedEvent extends Event {

    public int m_Width, m_Height;

    public WindowResizedEvent(int width, int height) {
        super(EventType.WINDOW_RESIZED_EVENT);
        m_Width = width;
        m_Height = height;
    }

    public int getWidth() {
        return m_Width;
    }

    public int getHeight() {
        return m_Height;
    }
}
