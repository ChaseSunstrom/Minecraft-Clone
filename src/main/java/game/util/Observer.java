package game.util;

import game.events.Event;

public interface Observer {
    public void onNotify(Event event);
}
