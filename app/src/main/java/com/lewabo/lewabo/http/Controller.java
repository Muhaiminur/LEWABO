package com.lewabo.lewabo.http;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    public static final String BASE_URL = "http://lewabo-env.eba-jji6q3hr.us-east-2.elasticbeanstalk.com/lewabo/";
    private static Retrofit retrofit = null;
    ApiService riderAPI;
    private static Controller controller;

    public static Controller getInstance() {
        if (controller != null)
            return controller;
        else
            return new Controller();
    }

    private Controller() {
        controller = this;
        getBaseClient();
        riderAPI = retrofit.create(ApiService.class);
    }

    public static Retrofit getBaseClient() {
        OkHttpClient client = new OkHttpClient();
        try {
            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
            if (tlsSocketFactory.getTrustManager() != null) {
                client = new OkHttpClient.Builder()
                        .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                        .connectTimeout(10000, TimeUnit.SECONDS)
                        .readTimeout(10000, TimeUnit.SECONDS).build();
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}
