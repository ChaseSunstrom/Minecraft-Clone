package game.ecs.component;

import game.ecs.entity.Entity;
import game.util.TypeInfo;

import java.util.HashMap;

public class ComponentManager {

    HashMap<TypeInfo, ComponentArray> m_ComponentArrays;

    public ComponentManager() {
        m_ComponentArrays = new HashMap<>();
    }

    public <ComponentType> void registerComponent(Class<ComponentType> componentType) {
        TypeInfo typeInfo = new TypeInfo(componentType);
        m_ComponentArrays.put(typeInfo, new ComponentArray<ComponentType>());
    }

    public <ComponentType> void addComponent(Entity entity, ComponentType component) {
        TypeInfo typeInfo = new TypeInfo(component.getClass());
        ComponentArray<ComponentType> componentArray = m_ComponentArrays.get(typeInfo);
        componentArray.addComponent(entity, component);
    }

    public <ComponentType> void removeComponent(Entity entity, ComponentType component) {
        TypeInfo typeInfo = new TypeInfo(component.getClass());
        ComponentArray<ComponentType> componentArray = m_ComponentArrays.get(typeInfo);
        componentArray.removeComponent(entity, component);
    }

    public <ComponentType> ComponentArray<ComponentType> getComponentArray(Class<ComponentType> componentType) {
        TypeInfo typeInfo = new TypeInfo(componentType);
        return m_ComponentArrays.get(typeInfo);
    }

    public <ComponentType> ComponentType getComponent(Entity entity, Class<ComponentType> componentType) {
        TypeInfo typeInfo = new TypeInfo(componentType);
        ComponentArray<ComponentType> componentArray = m_ComponentArrays.get(typeInfo);
        return componentArray.getComponent(entity);
    }

    public void destroyEntityComponents(Entity entity) {
        for (ComponentArray componentArray : m_ComponentArrays.values()) {
            componentArray.removeComponent(entity, null);
        }
    }
}
