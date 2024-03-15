package com.example.feesapp.ui.adts;

import java.lang.reflect.Array;

public class Stack<T> {

    private final T[] array;
    private final int sizeInt;
    private int topElement = -1;

    public Stack(Class<T> T, int size) {
        // Array Of Type Given
        this.array = (T[]) Array.newInstance(T, size);
        this.sizeInt = size;
    }

    public void push(T object) {
        // Error Checking To Insure Size Is Not Exceeded
        if (isFull())
            throw new Error("Stack Is Full! Cannot Push To Stack!");

        topElement++;
        array[topElement] = object;
    }

    public T pop() {
        // Error Checking. Cannot Pop From An Empty Stack
        if (isEmpty())
            throw new Error("Stack Is Empty! Cannot Pop To Stack!");

        topElement--;
        return array[topElement + 1];
    }

    public T peek() {
        return array[topElement];
    }

    public int size() {
        return sizeInt;
    }

    public boolean isFull() {
        return topElement >= sizeInt - 1;
    }

    public boolean isEmpty() {
        return topElement == -1;
    }

}
