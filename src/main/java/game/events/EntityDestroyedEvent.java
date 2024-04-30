package game.events;

import game.ecs.entity.Entity;

public class EntityDestroyedEvent extends Event {

    private final Entity m_Entity;

    public EntityDestroyedEvent(Entity entity) {
        super(EventType.ENTITY_DESTROYED_EVENT);
        m_Entity = entity;
    }

    public Entity getEntity() {
        return m_Entity;
    }
}
