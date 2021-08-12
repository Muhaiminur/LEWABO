package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.databinding.RecyclerMylistBinding;
import com.lewabo.lewabo.http.ApiService;
import com.lewabo.lewabo.http.Controller;
import com.lewabo.lewabo.utility.API_RESPONSE;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.PlayerDetails;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MylistAdapter extends RecyclerView.Adapter<MylistAdapter.Todo_View_Holder> {
    Context context;
    List<Content> list;
    Utility utility;
    ApiService apiInterface = Controller.getBaseClient().create(ApiService.class);
    Gson gson = new Gson();


    public MylistAdapter(List<Content> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerMylistBinding historyBinding;

        public Todo_View_Holder(RecyclerMylistBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(Content s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public MylistAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerMylistBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_mylist, parent, false);
        return new MylistAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final MylistAdapter.Todo_View_Holder holder, int position) {
        final Content bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.getImage())
                    .fitCenter()
                    .into(holder.historyBinding.mylistImage);

            holder.historyBinding.mylistImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mylistdialog(bodyResponse);
                    //((AppCompatActivity) context).startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", bodyResponse.getResolutions().getPath240p()));
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void mylistdialog(Content con) {
        try {
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.setContentView(R.layout.dialog_mylist);
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            dialog.show();
            ImageView image = dialog.findViewById(R.id.dmylist_image);
            MaterialButton close = dialog.findViewById(R.id.dmylist_close);
            TextView tittle = dialog.findViewById(R.id.dmylist_tittle);
            TextView time = dialog.findViewById(R.id.dmylist_time);
            TextView subtittle = dialog.findViewById(R.id.dmylist_description);
            MaterialButton play = dialog.findViewById(R.id.dmylist_play);
            MaterialButton delete = dialog.findViewById(R.id.dmylist_delete);
            Glide.with(context)
                    .load(con.getImage())
                    .fitCenter()
                    .into(image);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            tittle.setText(con.getTitle());
            time.setText(con.getCatgeoryTitle() + " " + con.getDuration().toString() + "min");
            subtittle.setText(con.getBrief());
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    ((AppCompatActivity) context).startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", con.getResolutions().getPath240p()));
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    delete_mylist(con.getId().toString());
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void delete_mylist(String m) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("id", m);
            utility.showProgress(false, context.getResources().getString(R.string.wait_string));
            Call<API_RESPONSE> call = apiInterface.delete_to_mylist(utility.getAuthToken(), utility.getuserid(), hashMap);
            call.enqueue(new Callback<API_RESPONSE>() {
                @Override
                public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                    utility.hideProgress();
                    try {
                        utility.logger(response.toString());
                        if (response.isSuccessful() && response.code() == 200 && response != null) {
                            utility.logger("delete my list " + response.body().toString());
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