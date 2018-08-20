package me.devnatan.events4m.fight.java;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Stream;

public class GenericTypeArray<E> {

    @Getter @Setter private E[] elements;

    public GenericTypeArray(int i) {
        elements = (E[]) new Object[i];
    }

    public int append(E e) {
        final int N = elements.length;
        elements = Arrays.copyOf(elements, N + 1);
        elements[N] = e;
        return N;
    }

    public int len() {
        return elements.length;
    }

    public E get(int i) {
        return (E) elements[i];
    }

    public E remaining(int i) {
        return (elements.length % i == 0) ? null : (E) elements[elements.length - 1];
    }

    public void reset(int i) {
        elements = (E[]) new Object[i];
    }

    public Stream<E> stream() {
        return Arrays.stream(elements);
    }

}
