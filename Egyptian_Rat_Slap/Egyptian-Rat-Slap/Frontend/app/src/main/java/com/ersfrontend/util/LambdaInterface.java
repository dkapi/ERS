package com.ersfrontend.util;

public interface LambdaInterface<T> {
    /**
     * Implemented to do an operation with the given result of type T
     * @param result
     */
    public void doOperation(T result);
}
