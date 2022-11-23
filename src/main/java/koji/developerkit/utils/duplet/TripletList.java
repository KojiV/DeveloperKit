package koji.developerkit.utils.duplet;

import java.util.ArrayList;

public class TripletList<A, B, C> extends ArrayList<Triplet<A, B, C>> {
    public boolean add(A a, B b, C c) {
        return add(Tuple.of(a, b, c));
    }
}
