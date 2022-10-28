package koji.developerkit.utils.duplet;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class Quintet<A, B, C, D, E> extends Tuple {

    private A first;

    private B second;

    private C third;

    private D fourth;

    private E fifth;

    private Quintet(A first, B second, C third, D fourth, E fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
    }

    public <T> boolean contains(T object) {
        return getFirst().equals(object) ||
                getSecond().equals(object) ||
                getThird().equals(object) ||
                getFourth().equals(object) ||
                getFifth().equals(object);
    }

    public static <A, B, C, D, E> Quintet<A, B, C, D, E> of(A first, B second, C third, D fourth, E fifth) {
        return new Quintet<>(first, second, third, fourth, fifth);
    }

    public static <A, B, C, D, E, F> Function<Quintet<A, B, C, D, E>, Stream<F>> flat(
            Function<? super A, ? extends F> mapFirst,
            Function<? super B, ? extends F> mapSecond,
            Function<? super C, ? extends F> mapThird,
            Function<? super D, ? extends F> mapFourth,
            Function<? super E, ? extends F> mapFifth) {

        return quintet -> quintet.stream(mapFirst, mapSecond, mapThird, mapFourth, mapFifth);
    }

    public <F> Stream<F> stream(Function<? super A, ? extends F> mapFirst,
                                Function<? super B, ? extends F> mapSecond,
                                Function<? super C, ? extends F> mapThird,
                                Function<? super D, ? extends F> mapFourth,
                                Function<? super E, ? extends F> mapFifth) {

        return Stream.of(mapFirst.apply(first),
                mapSecond.apply(second),
                mapThird.apply(third),
                mapFourth.apply(fourth),
                mapFifth.apply(fifth));
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

    public E getFifth() {
        return fifth;
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

    public void setFifth(E fifth) {
        this.fifth = fifth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Quintet{");
        sb.append("first=").append(first);
        sb.append(", second=").append(second);
        sb.append(", third=").append(third);
        sb.append(", fourth=").append(fourth);
        sb.append(", fifth=").append(fifth);
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
        Quintet<?, ?, ?, ?, ?> quintet = (Quintet<?, ?, ?, ?, ?>) o;
        return Objects.equals(first, quintet.first) &&
                Objects.equals(second, quintet.second) &&
                Objects.equals(third, quintet.third) &&
                Objects.equals(fourth, quintet.fourth) &&
                Objects.equals(fifth, quintet.fifth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, fourth, fifth);
    }
}
