package br.com.beergo.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRestService {
    private static final String BEERGO_BASE_URL = "https://beergo-alexandredsa.c9users.io:8081";
    private static final String BEERGO_DEV_BASE_URL = "http://192.168.255.250:8081";
    private static final String GOOGLE_MAPS_BASE_URL = "https://maps.googleapis.com/";

    public static Object initService(Class serviceClass, String baseUrl) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

    public static Object initBeerGOService(Class serviceClass) {
        return initService(serviceClass, BEERGO_BASE_URL);
    }

    @Deprecated
    public static Object initGoogleMapsService(Class serviceClass) {
        return initService(serviceClass, GOOGLE_MAPS_BASE_URL);
    }
}
