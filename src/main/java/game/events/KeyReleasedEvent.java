package game.events;

public class KeyReleasedEvent extends Event {

    public int m_KeyCode;

    public KeyReleasedEvent(int keyCode) {
        super(EventType.KEY_RELEASED_EVENT);
        m_KeyCode = keyCode;
    }

    public int getKeyCode() {
        return m_KeyCode;
    }
}
