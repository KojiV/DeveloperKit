package koji.developerkit.events;

import koji.developerkit.events.ExpandEventHandler;
import koji.developerkit.events.GenericEventHandler;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

public class GenericEvent<T extends Event> extends Event implements Listener {

    private final Consumer<? super T> mainThing;
    private final GenericEventHandler annotation;

    public static <A extends Event> GenericEvent<A> newInstance(Class<A> type, Consumer<? super A> mainThing) {
        return new GenericEvent<A>(mainThing);
    }

    public static <A extends Event> GenericEvent<A> newInstance(Class<A> type, Consumer<? super A> mainThing, GenericEventHandler annotation) {
        return new GenericEvent<A>(mainThing, exclusions);
    }

    public GenericEvent(Consumer<? super T> mainThing) {
        this.mainThing = mainThing;
        this.annotation = new GenericEventHandler();
    }

    public GenericEvent(Consumer<? super T> mainThing, GenericEventHandler annotation) {
        this.mainThing = mainThing;
        this.annotation = annotation;
    }

    private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @ExpandEventHandler(exclude = annotation.exclude())
    public void event(T generic) {
        mainThing.accept(generic);
    }
}