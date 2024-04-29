package game.events;

public class MouseMovedEvent extends Event {

    private double m_X, m_Y;

    public MouseMovedEvent(double x, double y) {
        super(EventType.MOUSE_MOVED_EVENT);
        m_X = x;
        m_Y = y;
    }

    public double getX() {
        return m_X;
    }

    public double getY() {
        return m_Y;
    }

    public void setX(double x) {
        m_X = x;
    }

    public void setY(double y) {
        m_Y = y;
    }
}
