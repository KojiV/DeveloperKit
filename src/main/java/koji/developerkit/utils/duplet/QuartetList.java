package koji.developerkit.utils.duplet;

import java.util.ArrayList;
import java.util.List;

public class QuartetList<A, B, C, D> extends ArrayList<Quartet<A, B, C, D>> {
    public boolean add(A a, B b, C c, D d) {
        return add(Tuple.of(a, b, c, d));
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

    public List<D> fourthToList() {
        List<D> list = new ArrayList<>();
        this.forEach(duplet -> list.add(duplet.getFourth()));
        return list;
    }
}
