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

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.FragmentReg2PageBinding;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.LoginPage;

public class Reg2Page extends Fragment {
    Utility utility;
    Context context;
    FragmentReg2PageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentReg2PageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.freg_container_view);
                navController = navHostFragment.getNavController();
                binding.reg2Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utility.hideKeyboard(view);
                        if (!TextUtils.isEmpty(binding.userMobile.getEditableText().toString())) {
                            if (!TextUtils.isEmpty(binding.userPassword.getEditableText().toString())) {
                                if (utility.isNetworkAvailable()) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", binding.userMobile.getEditableText().toString());
                                    bundle.putString("pass", binding.userPassword.getEditableText().toString());
                                    navController.navigate(R.id.reg3Fragment, bundle);
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

                binding.reg2Privacy.setOnClickListener(new View.OnClickListener() {
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
                binding.reg2Login.setOnClickListener(new View.OnClickListener() {
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
}