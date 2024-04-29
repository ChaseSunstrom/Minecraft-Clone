package game.events;

public class MouseReleasedEvent extends Event {

    public int m_Button;
    public int m_Action;
    public int m_Modifiers;

    public MouseReleasedEvent(int button, int action, int modifiers) {
        super(EventType.MOUSE_RELEASED_EVENT);
        m_Button = button;
        m_Action = action;
        m_Modifiers = modifiers;
    }

    public int getButton() {
        return m_Button;
    }

    public int getAction() {
        return m_Action;
    }

    public int getModifiers() {
        return m_Modifiers;
    }
}
