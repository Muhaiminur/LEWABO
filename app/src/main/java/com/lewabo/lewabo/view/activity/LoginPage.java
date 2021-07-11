package com.lewabo.lewabo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lewabo.lewabo.databinding.ActivityLoginPageBinding;
import com.lewabo.lewabo.utility.Utility;

public class LoginPage extends AppCompatActivity {

    Context context;
    Utility utility;
    ActivityLoginPageBinding binding;

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
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}