package koji.developerkit.utils.duplet;

import java.util.ArrayList;

public class DupletList<A, B> extends ArrayList<Duplet<A, B>> {
    public boolean add(A a, B b) {
        return add(Tuple.of(a, b));
    }
}
