package com.lewabo.lewabo.view.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.ActivityHomePageBinding;
import com.lewabo.lewabo.utility.Utility;

public class HomePage extends AppCompatActivity {

    Context context;
    Utility utility;
    ActivityHomePageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            context = this;
            utility = new Utility(context);
            navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (/*drawerLayout.isDrawerOpen(GravityCompat.START)*/false) {
                //drawerLayout.closeDrawers();
            } else {
                if (navHostFragment != null && navHostFragment.getChildFragmentManager().getBackStackEntryCount() == 0) {
                    if (isTaskRoot()) {
                        //super.onBackPressed();
                        //View bottomview=
                        BottomSheetDialog dialog = new BottomSheetDialog(this);
                        dialog.setContentView(R.layout.dialog_exit);
                        FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        dialog.show();
                        MaterialButton yes = dialog.findViewById(R.id.exit_ok);
                        MaterialButton cancel = dialog.findViewById(R.id.exit_cnl);
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                    } else {
                        super.onBackPressed();
                    }
                } else {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }
        } catch (Exception ex) {
            Log.d("Error Line Number", Log.getStackTraceString(ex));
        }
    }
}