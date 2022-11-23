package koji.developerkit.utils.duplet;

import java.util.ArrayList;

public class QuintetList<A, B, C, D, E> extends ArrayList<Quintet<A, B, C, D, E>> {
    public boolean add(A a, B b, C c, D d, E e) {
        return add(Tuple.of(a, b, c, d, e));
    }
}
