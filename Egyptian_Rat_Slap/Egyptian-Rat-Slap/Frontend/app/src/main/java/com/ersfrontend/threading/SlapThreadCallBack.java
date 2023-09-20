package com.ersfrontend.threading;

import java.util.ArrayList;

public interface SlapThreadCallBack<T> {
    /**
     * Can be implemented to do something with a result of type T
     * @param result
     */
    void onSuccess(T result);

    /**
     * Can be implemented to do something with an arraylist of type T resulting object
     * @param result
     */
    void onSuccessArrayList(ArrayList<T> result);

    /**
     * Implemented to execute a statement on a failure
     * @param throwable
     */
    void onFailure(Throwable throwable);
}
