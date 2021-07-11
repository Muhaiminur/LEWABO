package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.FragmentReg4PageBinding;
import com.lewabo.lewabo.utility.Utility;

public class Reg4Page extends Fragment {
    Utility utility;
    Context context;
    FragmentReg4PageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;

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
                binding.reg4Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(R.id.reg5Fragment);
                    }
                });
                pac_check();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    public void pac_check() {
        try {
            binding.reg4Pac1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.reg4Pac1.isChecked()) {
                        binding.reg4Pac1.setChecked(true);
                        binding.reg4Pac2.setChecked(false);
                        binding.reg4Pac3.setChecked(false);
                    }
                }
            });
            binding.reg4Pac2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.reg4Pac2.isChecked()) {
                        binding.reg4Pac1.setChecked(false);
                        binding.reg4Pac2.setChecked(true);
                        binding.reg4Pac3.setChecked(false);
                    }
                }
            });
            binding.reg4Pac3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.reg4Pac3.isChecked()) {
                        binding.reg4Pac1.setChecked(false);
                        binding.reg4Pac2.setChecked(false);
                        binding.reg4Pac3.setChecked(true);
                    }
                }
            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}