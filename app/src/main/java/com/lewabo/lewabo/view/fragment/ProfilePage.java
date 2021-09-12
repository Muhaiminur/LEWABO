package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.LoginModel;
import com.lewabo.lewabo.databinding.FragmentProfilePageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.Utility;

public class ProfilePage extends Fragment {
    Utility utility;
    Context context;
    FragmentProfilePageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();

    LoginModel loginModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentProfilePageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                if (!TextUtils.isEmpty(utility.getUserprofile())) {
                    loginModel = gson.fromJson(utility.getUserprofile(), LoginModel.class);
                    if (loginModel != null) {
                        binding.profileName.setText(loginModel.getEmail());
                    }
                }
                binding.playerBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.popBackStack();
                        }
                    }
                });
                binding.profileMylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.mylist_frag);
                        }
                    }
                });
                binding.profileSignout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        utility.clearuserid();
                        utility.clearUserprofile();
                        Intent i = getActivity().getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        getActivity().finish();
                    }
                });
                binding.profileHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.mylist_frag);
                        }
                    }
                });
                binding.profileShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
                            String sAux = "\n" + context.getResources().getString(R.string.reg42_string) + "\n\n";
                            sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n";
                            i.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(i, "choose one"));
                        } catch (Exception e) {
                            e.toString();
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }
}