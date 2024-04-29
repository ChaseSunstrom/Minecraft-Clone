package game.util;

public class TypeInfo {
    private Class<?> m_Type;

    public TypeInfo(Class<?> type) {
        m_Type = type;
    }

    public boolean instanceOf(Object obj) {
        return m_Type.isInstance(obj);
    }

    public void display() {
        System.out.println("Type: " + m_Type.getName());
    }
}
