package game.events;

import game.ecs.entity.Entity;

public class EntityUpdatedEvent extends Event {

        private final Entity m_Entity;

        public EntityUpdatedEvent(Entity entity) {
            super(EventType.ENTITY_UPDATED_EVENT);
            m_Entity = entity;
        }

        public Entity getEntity() {
            return m_Entity;
        }
}
