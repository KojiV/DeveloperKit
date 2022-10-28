package koji.developerkit.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Consumer;

public class GenericEvent<T extends Event> extends Event implements Listener {

    private final Consumer<? super T> mainThing;
    private final Class<? extends Event>[] exclusions = new Class<? extends Event>[0];

    public GenericEvent(Consumer<? super T> mainThing) {
        this.mainThing = mainThing;
    }

    public GenericEvent(Consumer<? super T> mainThing, Class<? extends Event>... exclusions) {
        this(mainThing);
        this.exclusions = exclusions;
    }

    public GenericEvent(Consumer<? super T> mainThing, Class<? extends Event>[] exclusions) {
        this(mainThing);
        this.exclusions = exclusions;
    }

    private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @ExpandEventHandler(exclusions)
    public void event(T generic) {
        mainThing.accept();
    }
}