package game.ecs;

import game.ecs.entity.Entity;
import game.ecs.entity.EntityManager;
import game.ecs.component.ComponentManager;
import game.ecs.system.SystemManager;
import game.events.Event;
import game.util.Observer;

import java.util.ArrayList;

public class ECS {

    private final EntityManager m_EntityManager;
    private final ComponentManager m_ComponentManager;
    private final SystemManager m_SystemManager;
    private ArrayList<Observer> m_Observers;

    public ECS() {
        m_EntityManager = new EntityManager();
        m_ComponentManager = new ComponentManager();
        m_SystemManager = new SystemManager();
    }

    public Entity createEntity() {
        return m_EntityManager.createEntity();
    }

    public Entity createEntity(ArrayList<Component> components) {
        Entity entity = m_EntityManager.createEntity();
        for (Component component : components) {
            m_ComponentManager.addComponent(entity, component);
        }
        return entity;
    }

    public void destroyEntity(Entity entity) {
        m_EntityManager.destroyEntity(entity);
        m_ComponentManager.destroyEntityComponents(entity);
    }

    public void notifyObservers(Event event) {
        for (Observer observer : m_Observers) {
            observer.onNotify(event);
        }
    }

    public EntityManager getEntityManager() {
        return m_EntityManager;
    }

    public ComponentManager getComponentManager() {
        return m_ComponentManager;
    }

    public SystemManager getSystemManager() {
        return m_SystemManager;
    }
}
