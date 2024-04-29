package game.ecs.entity;

public class Entity {

    private int m_ID;
    private Runnable m_DestroyCallback;

    public Entity(int id) {
        m_ID = id;
    }

    public Entity(int id, Runnable destroyCallback) {
        m_ID = id;
        m_DestroyCallback = destroyCallback;
    }

    public int getID() {
        return m_ID;
    }

    public void destroy() {
        if (m_DestroyCallback != null) {
            m_DestroyCallback.run();
        }
    }
}
