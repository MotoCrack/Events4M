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

    public E get(int i) {
        return elements[i];
    }

    public int append(E e) {
        final int N = elements.length;
        elements = Arrays.copyOf(elements, N + 1);
        elements[N] = e;
        return N;
    }

    public void remove(E el) {
        if (elements == null) return;

        int indexOf = -1;
        for (int index = 0; index < elements.length; index++) {
            if (elements[index].equals(el)) {
                indexOf = index;
                break;
            }
        }

        if (indexOf > -1) {
            elements = (E[]) new Object[elements.length - 1];
            for (int index = 0; index < indexOf; index++) {
                elements[index] = elements[index];
            }

            for (int index = indexOf + 1; index < elements.length; index++) {
                elements[index - 1] = elements[index];
            }
        }
    }

    public int len() {
        return elements.length;
    }

    public E remaining(int i) {
        return (elements.length % i == 0) ? null : elements[elements.length - 1];
    }

    public void reset(int i) {
        elements = (E[]) new Object[i];
    }

    public Stream<E> stream() {
        return Arrays.stream(elements);
    }

}
