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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.BannerAdapter;
import com.lewabo.lewabo.adapter.SliderAdapter;
import com.lewabo.lewabo.adapter.TagAdapter;
import com.lewabo.lewabo.data.TagModel;
import com.lewabo.lewabo.databinding.FragmentDashboaredPageBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class DashboaredPage extends Fragment {

    Utility utility;
    Context context;
    FragmentDashboaredPageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    BannerAdapter bannerAdapter;
    List<String> bannerlist = new ArrayList<>();

    TagAdapter tagAdapter;
    List<TagModel> tagModelList = new ArrayList<>();
    List<String> tagMovieList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentDashboaredPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                bannerwork();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    public void bannerwork() {
        try {
            bannerlist.add("https://i.ibb.co/yn6Dxgg/banner1.png");
            bannerlist.add("https://i.ibb.co/VwjHG1H/banner2.png");
            bannerlist.add("https://i.ibb.co/ykFD8jF/banner3.png");
            bannerlist.add("https://i.ibb.co/S0V7NDm/banner4.jpg");
            bannerAdapter = new BannerAdapter(context);
            bannerAdapter.renewItems(bannerlist);
            binding.bannerSlider.setSliderAdapter(bannerAdapter);
            binding.bannerSlider.startAutoCycle();
            initial_tag();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    void initial_tag() {
        try {
            tagAdapter = new TagAdapter(tagModelList, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.bannerTag.setLayoutManager(mLayoutManager);
            binding.bannerTag.setItemAnimator(new DefaultItemAnimator());
            binding.bannerTag.setAdapter(tagAdapter);
            gettaglist();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    void gettaglist() {
        tagMovieList.add("https://i.ibb.co/CHTr73z/tag2.png");
        tagMovieList.add("https://i.ibb.co/zRngStB/tag1.png");
        tagMovieList.add("https://i.ibb.co/ykFD8jF/banner3.png");
        tagMovieList.add("https://i.ibb.co/S0V7NDm/banner4.jpg");
        tagModelList.add(new TagModel("Trending now", tagMovieList));
        tagModelList.add(new TagModel("Action & adventure", bannerlist));
        tagModelList.add(new TagModel("Drama", tagMovieList));
        tagModelList.add(new TagModel("Food", bannerlist));
        tagAdapter.notifyDataSetChanged();
    }
}