package koji.developerkit.listener;

import koji.developerkit.KBase;
import koji.developerkit.events.GenericEventHandler;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;

public class KListener extends KBase implements Listener {
    public KListener() {
        Class<?> clazz = getClass();
        for(Method method : clazz.getMethods()) {
            GenericEventHandler annotation = method.getDeclaredAnnotation(GenericEventHandler.class);

            if(annotation != null && method.getParameterTypes().length > 0) {
                Class<? extends Event>[] exclusions = annotation.excludedEvents();
                Class<?> listenerType = method.getParameterTypes()[0];

                Bukkit.getPluginManager().registerEvents(new GenericEvent<listenerType>(
                    toConsumer(this, method),
                    exclusions
                ));
            }
        }
    }

    public static <T> Consumer<T> toConsumer(Object obj, Method m) {
        return param -> {
            try {
                m.invoke(annotated, param);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
