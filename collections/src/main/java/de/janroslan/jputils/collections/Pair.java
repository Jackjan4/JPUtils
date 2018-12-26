package de.janroslan.jputils.collections;


import java.util.Objects;

/**
 * A simple pair
 * @param <X>
 * @param <Y>
 */
public class Pair<X, Y> {

    public final X item1;
    public final Y item2;

    public Pair(X item1, Y item2) {
        this.item1 = item1;
        this.item2 = item2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(item1, pair.item1) &&
                Objects.equals(item2, pair.item2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item1, item2);
    }
}
