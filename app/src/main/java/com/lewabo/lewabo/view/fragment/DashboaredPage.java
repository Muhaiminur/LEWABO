package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.TagAdapter;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.data.moviecontent.Tagdata;
import com.lewabo.lewabo.databinding.FragmentDashboaredPageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.PlayerDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboaredPage extends Fragment {

    Utility utility;
    Context context;
    FragmentDashboaredPageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    TagAdapter tagAdapter;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();
    List<Tagdata> list = new ArrayList<>();
    Content bannerdata;


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
                initial_tag();
                binding.dashMylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.mylist_frag);
                        }
                    }
                });
                binding.dashSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.search_frag);
                        }
                    }
                });
                binding.dashProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.profilepage);
                        }
                    }
                });
                binding.dashBanner.bannerAddlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bannerdata != null) {
                            add_mylist(bannerdata.getId().toString());
                        }
                    }
                });
                binding.dashBanner.bannerPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bannerdata != null) {
                            startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", bannerdata.getResolutions().getPath240p()));
                        }
                    }
                });
                binding.dashBanner.bannerInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bannerdata != null && navController != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("content_details", gson.toJson(bannerdata));
                            navController.navigate(R.id.player_frag, bundle);
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tagAdapter != null) {
            get_tagdata("", "");
        }
    }

    void initial_tag() {
        try {
            tagAdapter = new TagAdapter(list, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.bannerTag.setLayoutManager(mLayoutManager);
            binding.bannerTag.setItemAnimator(new DefaultItemAnimator());
            binding.bannerTag.setAdapter(tagAdapter);
            //gettaglist();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void get_tagdata(String m, String p) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("categoryId", m);
            hashMap.put("genreId", p);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.get_tag_all(utility.getAuthToken(), utility.getuserid(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("login " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                Type listType = new TypeToken<List<Tagdata>>() {
                                }.getType();
                                List<Tagdata> pList = gson.fromJson(api_response.getData().toString(), listType);
                                utility.logger("tagdata list" + pList.size());
                                if (pList.size() > 0) {
                                    list.clear();
                                    list.addAll(pList);
                                    tagAdapter.notifyDataSetChanged();
                                    if (list.get(0).getContents() != null && list.get(0).getContents().size() > 0) {
                                        bannerdata = list.get(0).getContents().get(0);
                                        if (bannerdata != null) {
                                            Glide.with(context)
                                                    .load(bannerdata.getImage())
                                                    .fitCenter()
                                                    .into(binding.dashBanner.bannerImage);
                                        }
                                    } else {
                                        binding.dashBanner.bannerAddlist.setVisibility(View.GONE);
                                        binding.dashBanner.bannerPlay.setVisibility(View.GONE);
                                        binding.dashBanner.bannerInfo.setVisibility(View.GONE);
                                        binding.dashBanner.bannerImage.setVisibility(View.GONE);
                                    }
                                } else {
                                    list.clear();
                                    tagAdapter.notifyDataSetChanged();
                                    utility.showDialog(context.getResources().getString(R.string.no_internet));
                                    binding.dashBanner.bannerAddlist.setVisibility(View.GONE);
                                    binding.dashBanner.bannerPlay.setVisibility(View.GONE);
                                    binding.dashBanner.bannerInfo.setVisibility(View.GONE);
                                    binding.dashBanner.bannerImage.setVisibility(View.GONE);
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

    private void add_mylist(String m) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", m);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.add_to_mylist(utility.getAuthToken(), utility.getuserid(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("add my list " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                utility.showDialog(api_response.getData().getAsString().toString());
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

    /*tagMovieList.add("https://i.ibb.co/CHTr73z/tag2.png");
        tagMovieList.add("https://i.ibb.co/zRngStB/tag1.png");
        tagMovieList.add("https://i.ibb.co/ykFD8jF/banner3.png");
        tagMovieList.add("https://i.ibb.co/S0V7NDm/banner4.jpg");
        bannerlist.add("https://i.ibb.co/yn6Dxgg/banner1.png");
            bannerlist.add("https://i.ibb.co/VwjHG1H/banner2.png");
            bannerlist.add("https://i.ibb.co/ykFD8jF/banner3.png");
            bannerlist.add("https://i.ibb.co/S0V7NDm/banner4.jpg");*/
}