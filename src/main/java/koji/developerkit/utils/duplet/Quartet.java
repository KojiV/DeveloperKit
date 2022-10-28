package koji.developerkit.utils.duplet;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Quartet<A, B, C, D> extends Tuple {

    private A first;

    private B second;

    private C third;

    private D fourth;

    private Quartet(A first, B second, C third, D fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public static <A, B, C, D, E> Function<Quartet<A, B, C, D>, Stream<E>> flat(
            Function<? super A, ? extends E> mapFirst,
            Function<? super B, ? extends E> mapSecond,
            Function<? super C, ? extends E> mapThird,
            Function<? super D, ? extends E> mapFourth) {

        return quartet -> quartet.stream(mapFirst, mapSecond, mapThird, mapFourth);
    }

    public <T> boolean contains(T object) {
        return getFirst().equals(object) ||
                getSecond().equals(object) ||
                getThird().equals(object) ||
                getFourth().equals(object);
    }

    public <E> Stream<E> stream(Function<? super A, ? extends E> mapFirst,
                                Function<? super B, ? extends E> mapSecond,
                                Function<? super C, ? extends E> mapThird,
                                Function<? super D, ? extends E> mapFourth) {

        return Stream.of(mapFirst.apply(first),
                mapSecond.apply(second),
                mapThird.apply(third),
                mapFourth.apply(fourth));
    }

    private <E> Quintet<A, B, C, D, E> add(E fifth) {
        return Quintet.of(first, second, third, fourth, fifth);
    }

    public static <A, B, C, D> Quartet<A, B, C, D> of(A first, B second, C third, D fourth) {
        return new Quartet<>(first, second, third, fourth);
    }

    public static <A, B, C, D, E> Function<Quartet<A, B, C, D>, Quintet<A, B, C, D, E>> mapToQuintet(
            QuatroFunction<? super A, ? super B, ? super C, ? super D, ? extends E> function
    ) {
        return quartet -> quartet.add(function.apply(quartet.first, quartet.second, quartet.third, quartet.fourth));
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

    public D getFourth() {
        return fourth;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public void setThird(C third) {
        this.third = third;
    }

    public void setFourth(D fourth) {
        this.fourth = fourth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Quartet{");
        sb.append("first=").append(first);
        sb.append(", second=").append(second);
        sb.append(", third=").append(third);
        sb.append(", fourth=").append(fourth);
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
        Quartet<?, ?, ?, ?> quartet = (Quartet<?, ?, ?, ?>) o;
        return Objects.equals(first, quartet.first) &&
                Objects.equals(second, quartet.second) &&
                Objects.equals(third, quartet.third) &&
                Objects.equals(fourth, quartet.fourth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, fourth);
    }
}
