package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.SubPlan;
import com.lewabo.lewabo.databinding.FragmentReg4PageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.LoginPage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reg4Page extends Fragment {
    Utility utility;
    Context context;
    FragmentReg4PageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();
    List<SubPlan> list = new ArrayList<>();

    String email = "";
    String pass = "";
    String planid = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentReg4PageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.freg_container_view);
                navController = navHostFragment.getNavController();
                if (getArguments() != null && getArguments().containsKey("email")) {
                    email = getArguments().getString("email");
                    pass = getArguments().getString("pass");
                    if (!TextUtils.isEmpty(email)) {
                        binding.reg4Continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putString("email", email);
                                bundle.putString("pass", pass);
                                bundle.putString("planid", planid);
                                navController.navigate(R.id.reg5Fragment, bundle);
                            }
                        });
                    } else {
                        utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                    }
                } else {
                    utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                }
                binding.reg4Privacy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String url = context.getResources().getString(R.string.privacy_url);
                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            builder.setToolbarColor(ContextCompat.getColor(context, R.color.red));
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(context, Uri.parse(url));
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            try {
                                Uri uri = Uri.parse(context.getResources().getString(R.string.privacy_url)); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                context.startActivity(intent);
                            } catch (Exception e2) {
                                Log.d("Error Line Number", Log.getStackTraceString(e2));
                                utility.showDialog(context.getResources().getString(R.string.no_browser_string));
                            }
                        }
                    }
                });
                binding.reg4Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, LoginPage.class));
                        getActivity().finish();
                    }
                });
                pac_check();
                //getlist();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    public void pac_check() {
        try {
            binding.reg4Pacbutton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    planid = "1";
                    /*if (binding.reg4Pac1.isChecked()) {
                        planid = "1";
                        binding.reg4Pac1.setChecked(true);
                        binding.reg4Pac2.setChecked(false);
                        binding.reg4Pac3.setChecked(false);
                    }*/
                }
            });
            binding.reg4Pacbutton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (binding.reg4Pac2.isChecked()) {
                        binding.reg4Pac1.setChecked(false);
                        binding.reg4Pac2.setChecked(true);
                        binding.reg4Pac3.setChecked(false);
                    }*/
                    planid = "2";
                }
            });
            binding.reg4Pacbutton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (binding.reg4Pac3.isChecked()) {
                        binding.reg4Pac1.setChecked(false);
                        binding.reg4Pac2.setChecked(false);
                        binding.reg4Pac3.setChecked(true);
                    }*/
                    planid = "3";
                }
            });
            binding.reg4Pacbutton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*if (binding.reg4Pac3.isChecked()) {
                        binding.reg4Pac1.setChecked(false);
                        binding.reg4Pac2.setChecked(false);
                        binding.reg4Pac3.setChecked(true);
                    }*/
                    planid = "4";
                }
            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void getlist() {
        try {
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.get_sub_list(utility.getAuthToken());
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("subplan list " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                Type listType = new TypeToken<List<SubPlan>>() {
                                }.getType();
                                List<SubPlan> pList = gson.fromJson(api_response.getData().toString(), listType);
                                utility.logger("subplan list" + pList.size());
                                if (pList.size() > 0) {
                                    list.clear();
                                    list.addAll(pList);
                                    if (list.get(0) != null) {
                                        binding.reg4Pactittle1.setText("$" + list.get(0).getPrice() + " /\\n " + list.get(0).getDay() + " " + list.get(0).getDuration());
                                    }
                                    if (list.get(1) != null) {
                                        binding.reg4Pactittle2.setText("$" + list.get(1).getPrice() + " /\\n " + list.get(1).getDay() + " " + list.get(1).getDuration());
                                    }
                                    if (list.get(2) != null) {
                                        binding.reg4Pactittle3.setText("$" + list.get(2).getPrice() + " /\\n " + list.get(2).getDay() + " " + list.get(2).getDuration());
                                    }
                                } else {
                                    list.clear();
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