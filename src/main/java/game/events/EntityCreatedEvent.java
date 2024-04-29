package game.events;

import game.ecs.entity.Entity;

public class EntityCreatedEvent extends Event {

    private Entity m_Entity;

    public EntityCreatedEvent(Entity entity) {
        super(EventType.ENTITY_CREATED_EVENT);
        m_Entity = entity;
    }

    public Entity getEntityID() {
        return m_Entity;
    }
}
