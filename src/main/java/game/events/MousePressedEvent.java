package game.events;

public class MousePressedEvent extends Event {

        public int m_Button;
        public int m_Action;
        public int m_Modifiers;

        public MousePressedEvent(int button, int action, int modifiers) {
            super(EventType.MOUSE_PRESSED_EVENT);
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
