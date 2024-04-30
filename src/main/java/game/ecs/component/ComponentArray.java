package game.ecs.component;

import game.ecs.entity.Entity;
import game.util.TypeInfo;

import java.util.ArrayList;

public class ComponentArray<ComponentType> {

    private ArrayList<ComponentType> m_Components;

    public ComponentArray() {
        m_Components = new ArrayList<>();
    }

    public void addComponent(Entity entity, ComponentType component) {
        m_Components.add(entity.getID(), component);
    }

    public void removeComponent(Entity entity, ComponentType component) {
        m_Components.remove(entity.getID());
    }

    public ComponentType getComponent(Entity entity) {
        return m_Components.get(entity.getID());
    }

    public boolean hasComponent(Entity e) {
        return m_Components.get(e.getID()) != null;
    }
}
