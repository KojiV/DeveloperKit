package koji.developerkit.utils.duplet;

import java.util.ArrayList;
import java.util.List;

public class DupletList<A, B> extends ArrayList<Duplet<A, B>> {
    public boolean add(A a, B b) {
        return add(Tuple.of(a, b));
    }

    public List<A> firstToList() {
        List<A> list = new ArrayList<>();
        this.forEach(duplet -> list.add(duplet.getFirst()));
        return list;
    }

    public List<B> secondToList() {
        List<B> list = new ArrayList<>();
        this.forEach(duplet -> list.add(duplet.getSecond()));
        return list;
    }
}
