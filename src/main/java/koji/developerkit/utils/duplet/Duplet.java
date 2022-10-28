package koji.developerkit.utils.duplet;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Duplet<A, B> extends Tuple {

    private A first;

    private B second;

    private Duplet(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Maps Duplet to Triplet, using provided BiFunction for computing third element of Triplet.
     * Useful in Stream API:
     * <pre>
     * {@code
     *      Stream<Triplet<Integer, Integer, Integer>> s = Stream.of(Tuple.of(1,2))
     *                                .itemsToRun(Duplet.mapToTriplet((a, b) -> a + b);
     * }
     * </pre>
     *
     * @param fun function taking both Duplet's elements as parameters, and returning third element
     * @param <A> first element
     * @param <B> second element
     * @param <C> third element
     * @return Mapping function Duplet to Triplet
     */
    public static <A, B, C> Function<Duplet<A, B>, Triplet<A, B, C>> mapToTriplet(
            BiFunction<? super A, ? super B, ? extends C> fun) {

        return duplet -> duplet.compute(fun);
    }

    public <T> boolean contains(T object) {
        return getFirst().equals(object) || getSecond().equals(object);
    }

    /**
     * Transforms Duplet to Triplet, using provided BiFunction
     *
     * @param fun function taking both Duplet's elements as parameters, and returning third element
     * @param <C> third element
     * @return Triplet where first two elements same as in Duplet, and third value is computed by provided function
     */
    public <C> Triplet<A, B, C> compute(BiFunction<? super A, ? super B, ? extends C> fun) {

        return add(fun.apply(first, second));
    }

    /**
     * Transforms Duplet to Triplet, using provided value
     *
     * @param third third element for triplet
     * @param <C>   third element
     * @return Triplet where first two elements same as in Duplet
     */
    public <C> Triplet<A, B, C> add(C third) {
        return Triplet.of(first, second, third);
    }

    public static <A, B> Duplet<A, B> of(A first, B second) {
        return new Duplet<>(first, second);
    }

    static <A, B> Duplet<A, B> of(Map.Entry<A, B> entry) {
        return new Duplet<>(entry.getKey(), entry.getValue());
    }

    public static <A, B, C> Function<Duplet<A, B>, Stream<C>> flat(Function<? super A, ? extends C> mapFirst,
                                                                   Function<? super B, ? extends C> mapSecond) {
        return duplet -> duplet.stream(mapFirst, mapSecond);
    }

    public <C> Stream<C> stream(Function<? super A, ? extends C> firstMap,
                                Function<? super B, ? extends C> secondMap) {
        return Stream.of(firstMap.apply(first), secondMap.apply(second));
    }

    public <C> Duplet<C, B> mapFirst(Function<? super A, ? extends C> firstMap) {

        return map(firstMap, Function.identity());
    }

    public <C, D> Duplet<C, D> map(Function<? super A, ? extends C> firstMap,
                                   Function<? super B, ? extends D> secondMap) {

        return new Duplet<>(firstMap.apply(first), secondMap.apply(second));
    }

    public <C> Duplet<A, C> mapSecond(Function<? super B, ? extends C> secondMap) {

        return map(Function.identity(), secondMap);
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Duplet{");
        sb.append("first=").append(first);
        sb.append(", second=").append(second);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duplet<?, ?> duplet = (Duplet<?, ?>) o;
        return Objects.equals(first, duplet.first) &&
                Objects.equals(second, duplet.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
