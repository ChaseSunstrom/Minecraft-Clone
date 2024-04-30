package game.ecs;

import game.ecs.component.Transform;
import game.ecs.entity.Entity;
import game.ecs.entity.EntityManager;
import game.ecs.component.ComponentManager;
import game.ecs.system.SystemManager;
import game.events.EntityCreatedEvent;
import game.events.Event;
import game.events.EventType;
import game.util.Observer;
import game.util.ThreadPool;

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
        m_Observers = new ArrayList<>();
    }

    public Entity createEntity() {
        Entity entity = m_EntityManager.createEntity();
        ThreadPool.enqueue(ThreadPool.TaskPriority.HIGH, false,
                () -> {
                    notifyObservers(new EntityCreatedEvent(entity));
                    return null;
                }
        );
        return entity;
    }

    public Entity createEntity(Object... components) {
        Entity entity = m_EntityManager.createEntity();
        for (Object component : components) {
            m_ComponentManager.addComponent(entity, component);
        }
        ThreadPool.enqueue(ThreadPool.TaskPriority.HIGH, false,
                () -> {
                        notifyObservers(new EntityCreatedEvent(entity));
                        return null;
                }
        );
        return entity;
    }

    public void destroyEntity(Entity entity) {
        m_EntityManager.destroyEntity(entity);
        m_ComponentManager.destroyEntityComponents(entity);
    }

    public void notifyObservers(Event event) {
        for (Observer observer : m_Observers) {
            observer.onNotify(this, event);
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

    public boolean hasComponent(Class<?> componentType, Entity entity) {
        return m_ComponentManager.hasComponent(componentType, entity);
    }

    public <ComponentType> ComponentType getComponent(Class<ComponentType> componentType, Entity entity) {
        return m_ComponentManager.getComponent(entity, componentType);
    }
}
