package com.ersfrontend.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SlapQueue<T> implements Queue {
    private int size;
    private LinkedList<T> cardIDs;
    private int back;

    /**
     * Constructor for a new SlapQueue created based on an existing List
     * @param input
     */
    public SlapQueue(List<T> input) {
        cardIDs = new LinkedList<>();
        this.size = input.size();
        this.back = input.size() - 1;
        for (T val : input) {
            this.cardIDs.add(val);
        }
    }

    /**
     * Constructor for a completely new SlapQueue
     */
    public SlapQueue() {
        this.size = 0;
        this.back = 0;
        this.cardIDs = new LinkedList<>();
    }

    /**
     * Returns size
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns empty status
     * @return size() == 0
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public Object[] toArray(@NonNull Object[] objects) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(@NonNull Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection collection) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Nullable
    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    /**
     * Returns the first element of the Queue
     * @return cardIDs.get(0)
     */
    public T peek() {
        return cardIDs.get(0);
    }

    /**
     * Returns the last element of the Queue
     * @return cardIDs.get(back)
     */
    public T peekLast() {
        return cardIDs.get(back);
    }

    /**
     * Returns the index of the back element
     * @return back
     */
    public int getBackIndex() {
        return back;
    }

    /**
     * Gets the element at the specified index
     * @param index
     * @return cardIDs.get(index)
     */
    public T get(int index) {
        return cardIDs.get(index);
    }

    /**
     * Enqueues a new element onto the queue
     * @param cardIdToQueue
     * @return true
     */
    public boolean enqueue(T cardIdToQueue) {
        try {
            cardIDs.add(cardIdToQueue);
            size++;
            if (size == 1) {
                back = 0;
            } else if (size > 1) {
                back++;
            }
            return true;
        } catch (InputMismatchException e) {
            System.out.println("Tried to add wrong type to list.");
            return false;
        }
    }

    /**
     * Dequeues the first element from the queue
     * @return firstElement
     */
    public T dequeue() {
        if (size == 0) throw new ArrayIndexOutOfBoundsException("There is nothing in the Queue");
        T firstElement;
        firstElement = cardIDs.get(0);
        cardIDs.remove(0);
        size--;
        return firstElement;
    }

    /**
     * Prints the queue
     */
    public void printQueue() {
        System.out.println(cardIDs.toString());
    }
}
