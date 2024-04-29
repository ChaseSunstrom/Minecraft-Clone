package game.ecs.entity;

import java.util.ArrayList;
import java.util.Stack;

public class EntityManager {

    ArrayList<Entity> m_Entities;
    Stack<Entity> m_AvailableEntities;

    public EntityManager() {
        m_Entities = new ArrayList<>();
        m_AvailableEntities = new Stack<>();
    }

    public Entity createEntity() {
        Entity entity;
        if (m_AvailableEntities.isEmpty()) {
            entity = new Entity(m_Entities.size());
            m_Entities.add(entity);
        } else {
            entity = m_AvailableEntities.pop();
        }
        return entity;
    }

    public void destroyEntity(Entity entity) {
        m_AvailableEntities.push(entity);

        entity.destroy();
    }
}
