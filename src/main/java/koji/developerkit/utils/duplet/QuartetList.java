package koji.developerkit.utils.duplet;

import java.util.ArrayList;

public class QuartetList<A, B, C, D> extends ArrayList<Quartet<A, B, C, D>> {
    public boolean add(A a, B b, C c, D d) {
        return add(Tuple.of(a, b, c, d));
    }
}
