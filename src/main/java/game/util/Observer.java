package game.util;

import game.ecs.ECS;
import game.events.Event;

public interface Observer {
    public void onNotify(ECS ecs, Event event);
}
