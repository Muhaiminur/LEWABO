package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.FragmentProfilePageBinding;
import com.lewabo.lewabo.databinding.FragmentReg1PageBinding;
import com.lewabo.lewabo.utility.Utility;

public class ProfilePage extends Fragment {
    Utility utility;
    Context context;
    FragmentProfilePageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;

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
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.freg_container_view);
                navController = navHostFragment.getNavController();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }
}