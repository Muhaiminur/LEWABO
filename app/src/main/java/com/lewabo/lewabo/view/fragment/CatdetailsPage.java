package com.lewabo.lewabo.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.lewabo.lewabo.adapter.MylistAdapter;
import com.lewabo.lewabo.adapter.TagAdapter;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.data.moviecontent.Genre;
import com.lewabo.lewabo.data.moviecontent.Tagdata;
import com.lewabo.lewabo.databinding.FragmentCatdetailsPageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.PlayerDetails;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatdetailsPage extends Fragment {
    Utility utility;
    Context context;
    FragmentCatdetailsPageBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    MylistAdapter adapter;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();
    List<Genre> genlist = new ArrayList<>();
    ArrayAdapter<Genre> genadapter;
    String genid = "";

    List<Tagdata> list = new ArrayList<>();
    Content bannerdata;
    TagAdapter tagAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentCatdetailsPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                if (getArguments() != null && getArguments().containsKey("cat_details")) {
                    genid = getArguments().getString("cat_details");
                    if (genid != null && !TextUtils.isEmpty(genid)) {
                        utility.logger("cat details" + genid);
                        if (genid.equalsIgnoreCase("1")) {
                            binding.catdetailsTittle.setText(context.getResources().getText(R.string.movies_string));
                        } else if (genid.equalsIgnoreCase("2")) {
                            binding.catdetailsTittle.setText(context.getResources().getText(R.string.tvshows_string));
                        } else {

                        }
                        spinner();
                        initial_tag();
                        binding.catdetailsBanner.bannerAddlist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (bannerdata != null) {
                                    add_mylist(bannerdata.getId().toString());
                                }
                            }
                        });
                        binding.catdetailsBanner.bannerPlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (bannerdata != null) {
                                    startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", bannerdata.getResolutions().getPath240p()));
                                }
                            }
                        });
                        binding.catdetailsBanner.bannerInfo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (bannerdata != null && navController != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("content_details", gson.toJson(bannerdata));
                                    navController.navigate(R.id.player_frag, bundle);
                                }
                            }
                        });

                        binding.catdetailsBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (navController != null) {
                                    navController.popBackStack();
                                }
                            }
                        });
                        binding.catdetailsProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (navController != null) {
                                    navController.navigate(R.id.profilepage);
                                }
                            }
                        });
                        binding.catdetailsSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (navController != null) {
                                    navController.navigate(R.id.search_frag);
                                }
                            }
                        });
                    } else {
                        utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                    }
                } else {
                    utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                }
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    public void spinner() {
        genlist = new ArrayList<>();
        genadapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, genlist);
        genadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*binding.catdetailsGenere.setAdapter(genadapter);
        binding.catdetailsGenere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    genid = genlist.get(pos).getId().toString();
                    Toast.makeText(context, "Chose[" + genlist.get(pos) + "]", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });*/
        get_genere();
        binding.catdetailsGenere.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int oldIndex, @Nullable String oldItem, int newIndex, String newItem) {
                if (newIndex >= 0) {
                    for (Genre g : genlist) {
                        if (g.getTitle().equalsIgnoreCase(newItem)) {
                            get_tagdata(g.getId().toString());
                        }
                    }
                    //genid = genlist.get(newIndex).getId().toString();
                    //Toast.makeText(context, "Chose[" + newItem + "]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void initial_tag() {
        try {
            tagAdapter = new TagAdapter(list, context);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.catdetailsTag.setLayoutManager(mLayoutManager);
            binding.catdetailsTag.setItemAnimator(new DefaultItemAnimator());
            binding.catdetailsTag.setAdapter(tagAdapter);
            //gettaglist();
            get_tagdata("");
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (tagAdapter != null) {
            get_tagdata("", "");
        }*/
    }

    private void get_genere() {
        try {
            Call<API_RESPONSE> call = apiInterface.generi_list(utility.getAuthToken(), utility.getuserid());
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("genre list " + response.body().toString());
                            API_RESPONSE api_response = response.body();
                            if (api_response.getCode() == 200) {
                                Type listType = new TypeToken<List<Genre>>() {
                                }.getType();
                                List<Genre> pList = gson.fromJson(api_response.getData().toString(), listType);
                                if (pList.size() > 0) {
                                    genlist.clear();
                                    genlist.addAll(pList);
                                    genadapter.notifyDataSetChanged();
                                    List<String> p = new ArrayList<>();
                                    for (Genre c : pList) {
                                        p.add(c.getTitle());
                                    }
                                    //binding.catdetailsGenere.setSelection(1);
                                    binding.catdetailsGenere.setItems(p);
                                }
                            } else if (api_response.getCode() == 422) {
                                JSONObject jObj = new JSONObject(api_response.getData().toString());
                                String errors = jObj.getString("errors");
                                utility.showDialog(errors);
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

    private void get_tagdata(String p) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("categoryId", genid);
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
                                                    .into(binding.catdetailsBanner.bannerImage);
                                        }
                                    } else {
                                        binding.catdetailsBanner.bannerAddlist.setVisibility(View.GONE);
                                        binding.catdetailsBanner.bannerPlay.setVisibility(View.GONE);
                                        binding.catdetailsBanner.bannerInfo.setVisibility(View.GONE);
                                        binding.catdetailsBanner.bannerImage.setVisibility(View.GONE);
                                    }
                                } else {
                                    list.clear();
                                    tagAdapter.notifyDataSetChanged();
                                    utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                                    binding.catdetailsBanner.bannerAddlist.setVisibility(View.GONE);
                                    binding.catdetailsBanner.bannerPlay.setVisibility(View.GONE);
                                    binding.catdetailsBanner.bannerInfo.setVisibility(View.GONE);
                                    binding.catdetailsBanner.bannerImage.setVisibility(View.GONE);
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
}