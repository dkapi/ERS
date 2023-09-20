package com.spring.ERSBackend.Services;
import java.util.*;

public class SlapQueue<T> implements Queue {
    private int size;
    private List<T> cardIDs;
    private int back;

    public SlapQueue() {
        this.size = 0;
        this.cardIDs = new LinkedList<>();
        this.back = 0;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
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

    @Override
    public Object poll() {
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    public T peek() {
        return cardIDs.get(0);
    }

    public T peekLast() {
        return cardIDs.get(back);
    }

    public int getBackIndex() {
        return back;
    }

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
            System.out.println("Tried to add wrong type to Integer list.");
            return false;
        }
    }

    public T dequeue() {
        if (size == 0) throw new ArrayIndexOutOfBoundsException("There is nothing in the Queue");
        T firstElement;
        firstElement = cardIDs.get(0);
        cardIDs.remove(0);
        size--;
        return firstElement;
    }

    public void printQueue() {
        System.out.println(cardIDs.toString());
    }
}
