package com.ersfrontend.util;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickCallBack<T> implements Callback<T> {
    String logtag;
    LambdaInterface<T> lambdaInterface;

    /**
     * Constructor for a QuickCallBack, no logtag
     * @param lambdaInterface
     */
    public QuickCallBack(LambdaInterface<T> lambdaInterface) {
        this.lambdaInterface = lambdaInterface;
    }

    /**
     * Constructor for a QuickCallBack with a logtag
     * @param lambdaInterface
     * @param logtag
     */
    public QuickCallBack(LambdaInterface<T> lambdaInterface, String logtag) {
        this.lambdaInterface = lambdaInterface;
        this.logtag = logtag;
    }

    /**
     * On a successful response call lambdaInterface.doOperation() with the received response.body().
     * If the response is not successful log the incident.
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            Log.d(logtag, response.toString());
            lambdaInterface.doOperation(response.body());
        } else {
            Log.d(logtag, "Code:  "+response.code() + "    Msg:  "+response.message());
        }
    }

    /**
     * If the call back receives a total failure, throw the error message with the logtag
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(logtag, "Thrown:  "+t.getMessage());
    }
}
