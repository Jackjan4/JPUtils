/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.collections;

import java.util.Iterator;

/**
 * A stack that overflows if its full. "Overflows" means in this context that
 * the stack is again beginning at the start and is overwriting old entries so on.
 * @author Jackjan
 * @param <T>
 */
public class OverflowStack<T> implements Iterable<T> {

    private final Object[] buffer;
    private int pos;

    public OverflowStack(int maxCapacity) {
        buffer = new Object[maxCapacity];
        pos = 0;
    }

    public void add(T obj) {
        buffer[inc()] = obj;

    }

    public T pop() {

        Object p = buffer[dec()];
        buffer[pos] = null;
        return (T) p;
    }

    public T peek() {
        if (pos != 0) {
            return (T) buffer[pos - 1];
        } else {
            return null;
        }
    }

    /**
     * Increments the pointer
     *
     * @return
     */
    private int inc() {

        // Upper end
        if (pos == buffer.length - 1) {
            pos = 0;
        } else {
            pos++;
        }
        return pos;
    }

    // Decrements the pointer
    private int dec() {
        // Lower end
        if (pos == 0) {
            pos = buffer.length - 1;
        } else {
            pos--;
        }
        return pos;
    }

    public boolean isEmpty() {
        if (buffer[dec()] == null) {
            inc();
            return true;
        } else {
            inc();
            return false;
        }

    }

    /**
     * Returns the capacity
     *
     * @return
     */
    public int getSize() {
        return buffer.length;
    }

    /**
     * Returns the iterator
     *
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return isEmpty();
            }

            @Override
            public T next() {
                return pop();
            }
        };

        return it;
    }
}
