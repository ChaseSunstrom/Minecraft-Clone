package game.events;

public class EventDispatcher {

    private Event m_Event;

    public EventDispatcher(Event event) {
        m_Event = event;
    }

    public void dispatch(EventType type, EventCallback callback) {
        if (m_Event.getType() == type) {
            callback.onEvent(m_Event);
        }
    }
}
