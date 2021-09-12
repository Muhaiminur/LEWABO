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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.LoginModel;
import com.lewabo.lewabo.databinding.FragmentReg6PageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.HomePage;
import com.lewabo.lewabo.view.activity.LoginPage;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reg6Page extends Fragment {
    Utility utility;
    Context context;
    FragmentReg6PageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();

    private String paymentIntentClientSecret;
    private Stripe stripe;

    String email = "";
    String pass = "";
    String planid = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentReg6PageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.freg_container_view);
                navController = navHostFragment.getNavController();
                stripe = new Stripe(context, Objects.requireNonNull("pk_test_TYooMQauvdEDq54NiTphI7jx"));
                if (getArguments() != null && getArguments().containsKey("email")) {
                    email = getArguments().getString("email");
                    pass = getArguments().getString("pass");
                    planid = getArguments().getString("planid");
                    if (!TextUtils.isEmpty(email)) {
                        startCheckout();
                       /* binding.reg5Continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putString("email", email);
                                bundle.putString("pass", pass);
                                bundle.putString("planid", planid);
                                navController.navigate(R.id.reg6Fragment, bundle);
                            }
                        });*/
                    } else {
                        utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                    }
                } else {
                    utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                }

                binding.reg6Privacy.setOnClickListener(new View.OnClickListener() {
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
                binding.reg6Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context, LoginPage.class));
                        getActivity().finish();
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    private void payment_one() {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("planId", planid);
            hashMap.put("email", email);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.get_payment_client(utility.getAuthToken(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("get mylist " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                JSONObject jObj = new JSONObject(api_response.getData().toString());
                                paymentIntentClientSecret = jObj.getString("clientSecret");
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

    public void startCheckout() {
        binding.reg6Paynow.setOnClickListener((View view) -> {
            if (binding.reg6Check.isChecked()) {
                PaymentMethodCreateParams params = binding.cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    stripe.confirmPayment(this, confirmParams);
                }
            } else {
                utility.showDialog(context.getResources().getString(R.string.reg64_string));
            }
        });
        payment_one();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

    private final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<Reg6Page> activityRef;

        PaymentResultCallback(@NonNull Reg6Page activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final Reg6Page activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                /*activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent)
                );*/
                Log.d("payment", gson.toJson(paymentIntent));
                Log.d("payment id", paymentIntent.getId().toString());
                registration(paymentIntent.getId().toString());
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final Reg6Page activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.create().show();
    }

    private void registration(String i) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", i);
            hashMap.put("password", pass);
            hashMap.put("planId", planid);
            hashMap.put("email", email);
            hashMap.put("name", "");
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.set_registration(utility.getAuthToken(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("registration " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                LoginModel loginModel = gson.fromJson(api_response.getData(), LoginModel.class);
                                utility.logger("registration" + loginModel.toString());
                                if (!TextUtils.isEmpty(loginModel.getId().toString())) {
                                    if (loginModel.getStatus().equalsIgnoreCase("ACTIVE")) {
                                        Long tstamp = System.currentTimeMillis();
                                        Long sys = Long.parseLong(loginModel.getExpireTime());
                                        int retval = tstamp.compareTo(sys);
                                        if (retval > 0) {
                                            utility.showDialog(context.getResources().getString(R.string.subsexpired_string));
                                            System.out.println("obj1 is greater than obj2");
                                        } else if (retval < 0) {
                                            if (binding.reg6Check.isChecked()) {
                                                utility.clearUserprofile();
                                                utility.clearuserid();
                                                utility.setuserid(loginModel.getId().toString());
                                                utility.setUserprofile(gson.toJson(loginModel));
                                                startActivity(new Intent(context, HomePage.class));
                                                getActivity().finish();
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