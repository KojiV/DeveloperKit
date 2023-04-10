package koji.developerkit.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SafeList <A> extends ArrayList<A> {
    A defaultValue;

    public SafeList(A defaultValue) {
        this.defaultValue = defaultValue;
    }

    public SafeList(Collection<? extends A> collection) {
        this(collection, null);
    }

    public SafeList(Collection<? extends A> collection, A defaultValue) {
        super(collection);
        this.defaultValue = defaultValue;
    }

    @Override public A get(int index) {
        return get(index, defaultValue);
    }

    public A get(int index, A defaultValue) {
        return KStatic.getOrDefault(this, index, defaultValue);
    }

    @Override public List<A> subList(int fromIndex, int toIndex) {
        return super.subList(fromIndex, Math.min(toIndex, size()));
    }
}
