package com.lewabo.lewabo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.LoginModel;
import com.lewabo.lewabo.databinding.ActivityLoginPageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    Context context;
    Utility utility;
    ActivityLoginPageBinding binding;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            context = this;
            utility = new Utility(context);
            binding.loginReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, RegistrationPage.class));
                }
            });
            binding.loginWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utility.hideKeyboard(view);
                    if (!TextUtils.isEmpty(binding.userMobile.getEditableText().toString())) {
                        if (!TextUtils.isEmpty(binding.userPassword.getEditableText().toString())) {
                            if (utility.isNetworkAvailable()) {
                                login(binding.userMobile.getEditableText().toString(), binding.userPassword.getEditableText().toString());
                            } else {
                                context.getResources().getString(R.string.no_internet);
                            }
                        } else {
                            binding.userPassword.setError(context.getResources().getString(R.string.pass_string));
                            binding.userPassword.requestFocusFromTouch();
                        }
                    } else {
                        binding.userMobile.setError(context.getResources().getString(R.string.email_string));
                        binding.userMobile.requestFocusFromTouch();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void login(String m, String p) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", m);
            hashMap.put("password", p);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.user_login(utility.getAuthToken(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("login " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                LoginModel loginModel = gson.fromJson(api_response.getData(), LoginModel.class);
                                utility.logger("login" + loginModel.toString());
                                if (!TextUtils.isEmpty(loginModel.getId().toString())) {
                                    if (loginModel.getStatus().equalsIgnoreCase("ACTIVE")) {
                                        Long tstamp = System.currentTimeMillis();
                                        Long sys = Long.parseLong(loginModel.getExpireTime());
                                        int retval = tstamp.compareTo(sys);
                                        if (retval > 0) {
                                            utility.showDialog(context.getResources().getString(R.string.subsexpired_string));
                                            System.out.println("obj1 is greater than obj2");
                                        } else if (retval < 0) {
                                            if (binding.loginRemember.isChecked()) {
                                                utility.clearUserprofile();
                                                utility.clearuserid();
                                                utility.setuserid(loginModel.getId().toString());
                                                utility.setUserprofile(gson.toJson(loginModel));
                                                startActivity(new Intent(context, HomePage.class));
                                                finish();
                                            }
                                            System.out.println("obj1 is less than obj2");
                                        } else {
                                            utility.showDialog(context.getResources().getString(R.string.subsexpired_string));
                                            System.out.println("obj1 is equal to obj2");
                                        }
                                    } else {
                                        utility.showDialog(context.getResources().getString(R.string.subsexpired_string));
                                    }
                                } else {
                                    utility.showToast(context.getResources().getString(R.string.something_went_wrong));
                                }
                            } else {
                                utility.showDialog(api_response.getData().toString());
                            }
                        } else {
                            utility.showToast(context.getResources().getString(R.string.something_went_wrong));
                        }
                    } catch (Exception e) {
                        utility.hideProgress();
                        Log.d("Failed to hit api", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                    Log.d("On Failure to hit api", t.toString());
                    utility.hideProgress();
                }
            });
        } catch (Exception e) {
            utility.hideProgress();
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}