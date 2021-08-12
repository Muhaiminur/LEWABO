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

/*
    //2 user registration
    @POST("register")
    Call<API_RESPONSE> user_registration(*//*@Header("Fcm-Token") String tok, *//*@Body HashMap id);

    //3 forget password
    @POST("forgot-password")
    Call<API_RESPONSE> user_forgetpass(*//*@Header("Fcm-Token") String tok, *//*@Body HashMap id);

    //4 Landing Maditation
    @GET("landing-meditation")
    Call<API_RESPONSE> landing_meditation();

    //5 daily emotion check
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("daily-emotion-check")
    Call<API_RESPONSE> daily_emotion_check(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //6 adjust goal
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("adjust-goal")
    Call<API_RESPONSE> adjust_goal(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //7 my program list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("program-lists")
    Call<API_RESPONSE> my_programList(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //8 resource list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("resource-lists")
    Call<API_RESPONSE> get_resource_list(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Query("type") String page);

    //9 set fvrt
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("favourite")
    Call<API_RESPONSE> set_fvrt(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //10 get fvrt list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("favourite-list")
    Call<API_RESPONSE> get_fvrt_list(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Query("type") String page);


    //11 get contact sub list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("contact-us-subject-list")
    Call<API_RESPONSE> get_contactsuv_list(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //12 send caontact data
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("contact-faq-request")
    Call<API_RESPONSE> send_contact(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //13 send seminder data
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("reminder-set")
    Call<API_RESPONSE> send_reminder(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //14 get privacy page
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("privacy-policy-page")
    Call<API_RESPONSE> get_privacy_page(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //15 send exp data
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("set-experience")
    Call<API_RESPONSE> send_exp(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //16 get firstfell exp page
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("experience-list")
    Call<API_RESPONSE> get_firstexp_page(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //17 send second exp data
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("emotion-recommendation")
    Call<API_RESPONSE> send_second_exp(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //18 get program data
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("practice-suggestion")
    Call<API_RESPONSE> get_program_data(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //19 get program details
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("program-details")
    Call<API_RESPONSE> get_program_details(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //20 get faq list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("faq-list")
    Call<API_RESPONSE> get_faq_list(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Query("category") String page);

    //21 get tip list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("tip-trick-list")
    Call<API_RESPONSE> get_tip_list(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //22 program enrol
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("enroll-program")
    Call<API_RESPONSE> program_enrol(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //23 time update program
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("add-program-time-spent")
    Call<API_RESPONSE> set_program_time(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //24 set User image
    @Multipart
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("upload-image")
    Call<API_RESPONSE> set_user_image(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Part MultipartBody.Part files);

    //25 get achievements list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("achievements")
    Call<API_RESPONSE> get_achvment_list(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //26 get dashboared list
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("dashboard")
    Call<API_RESPONSE> get_dash_list(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //27 get about page
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("about-us-page")
    Call<API_RESPONSE> get_about_page(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //28 get goal time
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @POST("goal-list")
    Call<API_RESPONSE> get_goal_time(@Header("Authorization") String authorization, @Header("fcmToken") String token, @Body HashMap id);

    //29 get about page
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("support-service")
    Call<API_RESPONSE> get_service_page(@Header("Authorization") String authorization, @Header("fcmToken") String token);

    //30 get ourpartner page
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @GET("our-partner")
    Call<API_RESPONSE> get_partner_page(@Header("Authorization") String authorization, @Header("fcmToken") String token);
*/
}
