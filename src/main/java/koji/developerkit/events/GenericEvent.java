package koji.developerkit.events;

import koji.developerkit.events.ExpandEventHandler;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public class GenericEvent<T extends Event> extends Event implements Listener {

    private final Consumer<? super T> mainThing;
    private final Class<? extends Event>[] exclusions;

    public static <A extends Event> GenericEvent<A> newInstance(Class<A> type, Consumer<? super A> mainThing) {
        return new GenericEvent<A>(mainThing);
    }

    public static <A extends Event> GenericEvent<A> newInstance(Class<A> type, Consumer<? super A> mainThing, Class<? extends Event>... exclusions) {
        return new GenericEvent<A>(mainThing, exclusions);
    }

    public GenericEvent(Consumer<? super T> mainThing) {
        this.mainThing = mainThing;
        this.exclusions = (Class<? extends Event>[]) new Class[0];
        exclusionString = "{}";
    }

    public GenericEvent(Consumer<? super T> mainThing, Class<? extends Event>... exclusions) {
        this.mainThing = mainThing;
        this.exclusions = exclusions;
        StringBuilder sb = new StringBuilder("{ ");
        for(Class<? extends Event> clazz : exclusions) {
            if(sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(clazz.getSimpleName()).append(".class");
        }
        exclusionString = sb.append(" }").toString();
    }

    private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    final String exclusionString;

    @ExpandEventHandler(exclude = exclusionString)
    public void event(T generic) {
        mainThing.accept(generic);
    }
}