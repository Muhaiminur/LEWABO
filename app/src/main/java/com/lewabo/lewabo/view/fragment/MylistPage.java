package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.MylistAdapter;
import com.lewabo.lewabo.databinding.FragmentMylistPageBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class MylistPage extends Fragment {

    Utility utility;
    Context context;
    FragmentMylistPageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    MylistAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentMylistPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                initial_mylist();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    void initial_mylist() {
        try {
            adapter = new MylistAdapter(list, context);
            binding.mylistRecycler.setLayoutManager(new GridLayoutManager(context, 3));
            binding.mylistRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.mylistRecycler.setAdapter(adapter);
            getmylist();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    void getmylist() {
        list.add("https://i.ibb.co/CHTr73z/tag2.png");
        list.add("https://i.ibb.co/zRngStB/tag1.png");
        list.add("https://i.ibb.co/ykFD8jF/banner3.png");
        list.add("https://i.ibb.co/S0V7NDm/banner4.jpg");
        adapter.notifyDataSetChanged();
    }
}