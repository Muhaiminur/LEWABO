package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.SearchAdapter;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.databinding.FragmentSearchPageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPage extends Fragment {

    Utility utility;
    Context context;
    FragmentSearchPageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    SearchAdapter adapter;
    List<Content> list = new ArrayList<>();
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentSearchPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                initial_list();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            getmylist();
        }
    }

    void initial_list() {
        try {
            adapter = new SearchAdapter(list, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.searchRecycler.setLayoutManager(mLayoutManager);
            binding.searchRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.searchRecycler.setAdapter(adapter);
            binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    utility.logger("search " + s);
                    get_movie_search(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    utility.logger("search " + s);
                    get_movie_search(s);
                    return false;
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void get_movie_search(String s) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("search", s);
            Call<API_RESPONSE> call = apiInterface.get_searchlist(utility.getAuthToken(), utility.getuserid(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("search list " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                Type listType = new TypeToken<List<Content>>() {
                                }.getType();
                                List<Content> pList = gson.fromJson(api_response.getData().toString(), listType);
                                utility.logger("search list" + pList.size());
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
                        Log.d("Failed to hit api", Log.getStackTraceString(e));
                    }
                }

                @Override
                public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                    Log.d("On Failure to hit api", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    void getmylist() {
        adapter.notifyDataSetChanged();
    }
}