package koji.developerkit.utils.duplet;

import java.util.ArrayList;
import java.util.List;

public class TripletList<A, B, C> extends ArrayList<Triplet<A, B, C>> {
    public boolean add(A a, B b, C c) {
        return add(Tuple.of(a, b, c));
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

    public List<C> thirdToList() {
        List<C> list = new ArrayList<>();
        this.forEach(duplet -> list.add(duplet.getThird()));
        return list;
    }
}
