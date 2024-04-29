package game.events;

public class MouseScrolledEvent extends Event {

    public float m_XOffset, m_YOffset;

    public MouseScrolledEvent(float xOffset, float yOffset) {
        super(EventType.MOUSE_SCROLLED_EVENT);
        m_XOffset = xOffset;
        m_YOffset = yOffset;
    }

    public float getXOffset() {
        return m_XOffset;
    }

    public float getYOffset() {
        return m_YOffset;
    }
}
