package game.events;

public class Event {

    public EventType m_Type;
    public boolean m_Handled;

    public Event(EventType type) {
        m_Type = type;
    }

    public EventType getType() {
        return m_Type;
    }

    public boolean isHandled() {
        return m_Handled;
    }

    public void setIsHandled(boolean handled) {
        m_Handled = handled;
    }

    public String toString() {
        return m_Type.toString();
    }
}

