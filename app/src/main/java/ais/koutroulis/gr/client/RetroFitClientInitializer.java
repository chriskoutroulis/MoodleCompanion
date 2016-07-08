package ais.koutroulis.gr.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c0nfr0ntier on 7/7/2016.
 */
public class RetroFitClientInitializer<T> {
    private String baseUrl;
    private Class<T> serviceClass;
    private T service;

    public RetroFitClientInitializer(String baseUrl, Class<T> serviceClass) {
        this.baseUrl = baseUrl;
        this.serviceClass = serviceClass;
        service = initialize(baseUrl, this.serviceClass);
    }

    private T initialize(String baseUrl, Class<T> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(serviceClass);
    }

    public T getService() {
        return service;
    }

}
