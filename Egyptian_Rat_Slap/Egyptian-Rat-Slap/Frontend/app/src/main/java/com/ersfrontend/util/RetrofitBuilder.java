package com.ersfrontend.util;

import static com.ersfrontend.util.AppURLs.BASE_URL;

import com.ersfrontend.api.GameApi;
import com.ersfrontend.api.PlayerApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static Retrofit.Builder baseApi;
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    /**
     * Builds a retrofit base for an api call
     * @return baseApi.build
     */
    private static Retrofit createBaseApi() {
        if (baseApi == null) {
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
            baseApi = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient);
        } return baseApi.build();
    }

    /**
     * Creates a specified gameApi from a base api by calling createBaseApi()
     * @return createBaseApi().create(GameApi.class)
     */
    public static GameApi createGameApi() {
        return createBaseApi().create(GameApi.class);
    }

    /**
     * Creates a specified playerApi from a base api by calling createBaseApi()
     * @return createBaseApi().create(PlayerApi.class)
     */
    public static PlayerApi createPlayerApi() {
        return createBaseApi().create(PlayerApi.class);
    }
}

