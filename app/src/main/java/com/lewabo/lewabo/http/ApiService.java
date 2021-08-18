package com.lewabo.lewabo.http;

import com.lewabo.lewabo.utility.API_RESPONSE;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    //1 user login
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("user/login")
    Call<API_RESPONSE> user_login(@Header("Authorization") String apiKey/*, @Header("usersId") String usersId*/, @Body HashMap id);

    //2 Tag all home page
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("tag/all")
    Call<API_RESPONSE> get_tag_all(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //3 add to mylist
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/me/add")
    Call<API_RESPONSE> add_to_mylist(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //4 delete to mylist
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/me/remove")
    Call<API_RESPONSE> delete_to_mylist(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);


    //5 all mylist
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("content/me/all")
    Call<API_RESPONSE> get_mylist(@Header("Authorization") String apiKey, @Header("userId") String usersId);

    //6 search list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/search")
    Call<API_RESPONSE> get_searchlist(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //7 add to mylike
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/like/update")
    Call<API_RESPONSE> add_to_mylike(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //8 genre list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("genre/all")
    Call<API_RESPONSE> generi_list(@Header("Authorization") String apiKey, @Header("userId") String usersId);

    //9 get recent list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("content/log/all")
    Call<API_RESPONSE> recent_list(@Header("Authorization") String apiKey, @Header("userId") String usersId);

    //10 add to recent list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/log/add")
    Call<API_RESPONSE> add_to_recent(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //11 get landing list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("content/image/all")
    Call<API_RESPONSE> landing_list(@Header("Authorization") String apiKey);


    //12 comming soon list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/category")
    Call<API_RESPONSE> get_coming_soon(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //13 suggestion list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("content/suggestion")
    Call<API_RESPONSE> get_suggestion_list(@Header("Authorization") String apiKey, @Header("userId") String usersId, @Body HashMap id);

    //14 get subscription list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("subscription/plan/all")
    Call<API_RESPONSE> get_sub_list(@Header("Authorization") String apiKey);

    //15 Payment 1
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("payment-intent")
    Call<API_RESPONSE> get_payment_client(@Header("Authorization") String apiKey, @Body HashMap id);

}
