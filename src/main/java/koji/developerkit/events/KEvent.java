package koji.developerkit.events;

import org.bukkit.event.HandlerList;

public interface KEvent {
    public default HandlerList getHandlers() {
        return new HandlerList();
    }

    @SuppressWarnings("unused")
    public static default HandlerList getHandlerList() {
        return new HandlerList();
    }
}