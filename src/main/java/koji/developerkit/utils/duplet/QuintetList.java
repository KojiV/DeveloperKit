package koji.developerkit.utils.duplet;

import java.util.ArrayList;
import java.util.List;

public class QuintetList<A, B, C, D, E> extends ArrayList<Quintet<A, B, C, D, E>> {
    public boolean add(A a, B b, C c, D d, E e) {
        return add(Tuple.of(a, b, c, d, e));
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

    public List<E> fifthToList() {
        List<E> list = new ArrayList<>();
        this.forEach(duplet -> list.add(duplet.getFifth()));
        return list;
    }
}
