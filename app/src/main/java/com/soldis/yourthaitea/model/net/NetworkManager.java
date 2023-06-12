package com.soldis.yourthaitea.model.net;


import android.content.Context;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soldis.yourthaitea.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    public static NetworkManager instance;
    private Context context;
    public static NetworkManager getInstance(Class<NetworkService> networkServiceClass)
    {
        if(instance == null) {
            instance = new NetworkManager();
        }

        return instance;
    }

    static OkHttpClient okHttpClient;
    public static NetworkService getNetworkService(){
        String DOMAIN_URL = AppController.getInstance().getSessionManager().getStringData(SessionManager.DOMAIN_URL);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN_URL + AppConstant.API_VERSION)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }

    public static NetworkService getNetworkServiceGoogle(Context context){
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.DOMAIN_GOOGLE_MAPS)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        return  networkService;
    }
}
