package game.events;

public class KeyPressedEvent extends Event {

    public int m_KeyCode;

    public KeyPressedEvent(int keyCode) {
        super(EventType.KEY_PRESSED_EVENT);
        m_KeyCode = keyCode;
    }

    public int getKeyCode() {
        return m_KeyCode;
    }

}
