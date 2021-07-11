package com.lewabo.lewabo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.SliderAdapter;
import com.lewabo.lewabo.databinding.ActivitySplashBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class SplashPage extends AppCompatActivity {

    ActivitySplashBinding binding;
    Context context;
    Utility utility;
    SliderAdapter sliderAdapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Themelewabo);
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            context = this;
            utility = new Utility(context);
            sliderwork();
            binding.splashGetstarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, LoginPage.class));
                    finish();
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    public void sliderwork() {
        try {
            list.add("https://i.pinimg.com/564x/c2/ef/51/c2ef51123c764541e2087b0324ee72e7.jpg");
            list.add("https://m.media-amazon.com/images/I/61P-HNp5QsL.jpg");
            list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2qjzOKQJhQiKWlhJZpc-MrnSvLl6MNWyGC1l9tUgAjKLJThOaTsMztqVQQ4NHVKZ5-ak&usqp=CAU");
            sliderAdapter = new SliderAdapter(context);
            sliderAdapter.renewItems(list);
            binding.splashSlider.setSliderAdapter(sliderAdapter);
            binding.splashSlider.startAutoCycle();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}