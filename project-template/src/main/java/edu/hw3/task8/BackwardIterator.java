package edu.hw3.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class BackwardIterator<E> implements Iterator<E> {

    private final Object[] collection;
    private int pos;

    public BackwardIterator(Collection<E> collection) {
        this.collection = collection.toArray();
        this.pos = collection.size() - 1;
    }

    @Override
    public boolean hasNext() {

        return pos >= 0;
    }

    @Override
    public E next() {
        if (hasNext()) {

            return (E) collection[pos--];
        } else {

            throw new RuntimeException("This is the first element in the collection");
        }
    }
}
