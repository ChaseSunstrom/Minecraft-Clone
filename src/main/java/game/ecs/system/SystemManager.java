package game.ecs.system;

import java.util.ArrayList;

public class SystemManager {

    private final ArrayList<StartSystem> m_StartSystems;
    private final ArrayList<UpdateSystem> m_UpdateSystems;
    private final ArrayList<EndSystem> m_EndSystems;

    public SystemManager() {
        m_StartSystems = new ArrayList<>();
        m_UpdateSystems = new ArrayList<>();
        m_EndSystems = new ArrayList<>();
    }

    public void onStart() {
        for (StartSystem system : m_StartSystems) {
            system.onStart();
        }
    }

    public void onUpdate() {
        for (UpdateSystem system : m_UpdateSystems) {
            system.onUpdate();
        }
    }

    public void onEnd() {
        for (EndSystem system : m_EndSystems) {
            system.onEnd();
        }
    }

    public void addSystem(System system) {
        if (system instanceof StartSystem) {
            m_StartSystems.add((StartSystem) system);
        }
        if (system instanceof UpdateSystem) {
            m_UpdateSystems.add((UpdateSystem) system);
        }
        if (system instanceof EndSystem) {
            m_EndSystems.add((EndSystem) system);
        }
    }
}
