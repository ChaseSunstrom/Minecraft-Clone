package game.events;

public class KeyRepeatedEvent extends Event {

    public int m_KeyCode;

    public KeyRepeatedEvent(int keyCode) {
        super(EventType.KEY_REPEATED_EVENT);
        m_KeyCode = keyCode;
    }

    public int getKeyCode() {
        return m_KeyCode;
    }
}
