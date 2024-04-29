package game.ecs.component;

public class Component {

    private Object m_Data;

    public Component(Object data) {
        m_Data = data;
    }

    public Object getData() {
        return m_Data;
    }

    public void setData(Object data) {
        m_Data = data;
    }
}
