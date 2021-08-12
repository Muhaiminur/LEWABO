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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.MylistAdapter;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.databinding.FragmentMylistPageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MylistPage extends Fragment {

    Utility utility;
    Context context;
    FragmentMylistPageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    MylistAdapter adapter;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();
    List<Content> list = new ArrayList<>();

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

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            getmylist();
        }
    }

    /*void getmylist() {
        list.add("https://i.ibb.co/CHTr73z/tag2.png");
        list.add("https://i.ibb.co/zRngStB/tag1.png");
        list.add("https://i.ibb.co/ykFD8jF/banner3.png");
        list.add("https://i.ibb.co/S0V7NDm/banner4.jpg");
        adapter.notifyDataSetChanged();
    }*/

    private void getmylist() {
        try {
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.get_mylist(utility.getAuthToken(), utility.getuserid());
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
                                Type listType = new TypeToken<List<Content>>() {
                                }.getType();
                                List<Content> pList = gson.fromJson(api_response.getData().toString(), listType);
                                utility.logger("my list" + pList.size());
                                if (pList.size() > 0) {
                                    list.clear();
                                    list.addAll(pList);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    list.clear();
                                    adapter.notifyDataSetChanged();
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