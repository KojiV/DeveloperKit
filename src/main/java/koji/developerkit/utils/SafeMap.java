package koji.developerkit.utils;

import java.util.HashMap;

public class SafeMap <A, B> extends HashMap<A, B> {

    B defaultValue;

    public SafeMap(B defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override public B get(Object key) {
        return get(key, defaultValue);
    }

    public B get(Object key, B defaultValue) {
        return super.getOrDefault(key, defaultValue);
    }

    public B createIfNotPresent(A key) {
        return createIfNotPresent(key, defaultValue);
    }

    public B createIfNotPresent(A key, B defaultValue) {
        if(!containsKey(key)) put(key, defaultValue);
        return get(key, defaultValue);
    }
}
