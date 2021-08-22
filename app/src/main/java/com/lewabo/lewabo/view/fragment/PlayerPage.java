package com.lewabo.lewabo.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.adapter.MylistAdapter;
import com.lewabo.lewabo.adapter.TextAdapter;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.data.moviecontent.Genre;
import com.lewabo.lewabo.databinding.FragmentPlayerPageBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.KeyWord;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.PlayerDetails;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerPage extends Fragment {
    Utility utility;
    Context context;
    FragmentPlayerPageBinding binding;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();
    NavHostFragment navHostFragment;
    NavController navController;
    Content content;

    List<Content> list = new ArrayList<>();
    MylistAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentPlayerPageBinding.inflate(inflater, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                navHostFragment = (NavHostFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.frag_homepage_view);
                navController = navHostFragment.getNavController();
                if (getArguments() != null && getArguments().containsKey("content_details")) {
                    content = gson.fromJson(getArguments().getString("content_details"), Content.class);
                    if (content != null) {
                        utility.logger("content details" + content.toString());
                        binding.playerDate.setText(utility.Hourtomin(content.getDuration()) + " - " + content.getLikes().toString() + "likes");
                        binding.playerTittle.setText(content.getTitle());
                        binding.playerDescription.setText(content.getBrief());
                        Glide.with(context)
                                .load(content.getImage()).error(R.drawable.ic_default).into(binding.playerImage);
                    } else {
                        utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                    }
                } else {
                    utility.showDialog(context.getResources().getString(R.string.something_went_wrong));
                }
                binding.playerMylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (content != null) {
                            add_mylist(content.getId().toString());
                        }
                    }
                });
                binding.playerLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (content != null) {
                            add_tolike(content.getId().toString());
                        }
                    }
                });

                binding.playerPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (content != null) {
                            startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", content.getResolutions().getPath240p()));
                        }
                    }
                });
                binding.playerBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.popBackStack();
                        }
                    }
                });
                binding.playerProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.profilepage);
                        }
                    }
                });
                binding.playerSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (navController != null) {
                            navController.navigate(R.id.search_frag);
                        }
                    }
                });
                binding.playerMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (content != null) {
                            showdetails(content);
                        }
                    }
                });
                initial_related();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return binding.getRoot();
    }

    void initial_related() {
        try {
            adapter = new MylistAdapter(list, context);
            binding.playerRecycler.setLayoutManager(new GridLayoutManager(context, 3));
            binding.playerRecycler.setItemAnimator(new DefaultItemAnimator());
            binding.playerRecycler.setAdapter(adapter);
            //getmylist();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null && content != null) {
            getmylist(content.getId().toString());
        }
    }

    private void getmylist(String p) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("tagId", "");
            hashMap.put("categoryId", "");
            hashMap.put("genreId", "");
            hashMap.put("contentId", p);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.get_suggestion_list(utility.getAuthToken(), utility.getuserid(), hashMap);
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

    private void add_tolike(String m) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", m);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.add_to_mylike(utility.getAuthToken(), utility.getuserid(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("add my like " + response.body().toString());
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

    public void showdetails(Content content) {
        try {

            HashMap<String, Integer> screen = utility.getScreenRes();
            int width = screen.get(KeyWord.SCREEN_WIDTH);
            int height = screen.get(KeyWord.SCREEN_HEIGHT);
            int mywidth = (width / 10) * 9;
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setContentView(R.layout.dialog_details);
            MaterialButton close = dialog.findViewById(R.id.details_close);
            TextView tvMessage = dialog.findViewById(R.id.details_name);
            RecyclerView cast = dialog.findViewById(R.id.details_cast_recycler);
            RecyclerView director = dialog.findViewById(R.id.details_cast_recycler);
            RecyclerView write = dialog.findViewById(R.id.details_writer_recycler);
            RecyclerView genre = dialog.findViewById(R.id.details_genres_recycler);
            LinearLayout ll = dialog.findViewById(R.id.dialog_layout_size);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.width = mywidth;
            ll.setLayoutParams(params);
            tvMessage.setText(content.getTitle());
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            List<String> writerlist = new ArrayList<>();
            List<String> direclist = new ArrayList<>();
            List<String> genlist = new ArrayList<>();
            writerlist.add(content.getWriter());
            direclist.add(content.getDirector());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            TextAdapter castadapter = new TextAdapter(content.getCastBy(), context);
            cast.setLayoutManager(mLayoutManager);
            cast.setItemAnimator(new DefaultItemAnimator());
            cast.setAdapter(castadapter);

            TextAdapter writeradapter = new TextAdapter(writerlist, context);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            write.setLayoutManager(mLayoutManager1);
            write.setItemAnimator(new DefaultItemAnimator());
            write.setAdapter(writeradapter);

            TextAdapter direcadapter = new TextAdapter(direclist, context);
            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            director.setLayoutManager(mLayoutManager2);
            director.setItemAnimator(new DefaultItemAnimator());
            director.setAdapter(direcadapter);

            for (Genre g : content.getGenres()) {
                genlist.add(g.getTitle());
            }
            TextAdapter genres = new TextAdapter(genlist, context);
            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            genre.setLayoutManager(mLayoutManager3);
            genre.setItemAnimator(new DefaultItemAnimator());
            genre.setAdapter(genres);

            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}