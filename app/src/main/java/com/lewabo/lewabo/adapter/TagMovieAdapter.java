package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.databinding.RecyclerTagMovieBinding;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.PlayerDetails;

import java.util.List;

public class TagMovieAdapter extends RecyclerView.Adapter<TagMovieAdapter.Todo_View_Holder> {
    Context context;
    List<Content> list;
    Utility utility;
    Gson gson = new Gson();


    public TagMovieAdapter(List<Content> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerTagMovieBinding historyBinding;

        public Todo_View_Holder(RecyclerTagMovieBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(Content s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public TagMovieAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerTagMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_tag_movie, parent, false);
        return new TagMovieAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final TagMovieAdapter.Todo_View_Holder holder, int position) {
        final Content bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            utility.logger("check" + bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.getImage())
                    .fitCenter()
                    .error(context.getResources().getDrawable(R.drawable.ic_loading))
                    .into(holder.historyBinding.tagMovieImage);

            holder.historyBinding.tagMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        NavController navController = Navigation.findNavController(holder.historyBinding.getRoot());
                        /*if (navController != null) {
                            navController.navigate(R.id.player_frag);
                        }*/
                        tagdialog(bodyResponse, navController);
                        //((AppCompatActivity) context).startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", bodyResponse.getResolutions().getPath240p()));
                    } catch (Exception e) {
                        Log.d("Error Line Number", Log.getStackTraceString(e));
                    }
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


    public void tagdialog(Content con, NavController controller) {
        try {
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.setContentView(R.layout.dialog_tagdialog);
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
            MaterialButton info = dialog.findViewById(R.id.dmylist_info);
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
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (controller != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("content_details", gson.toJson(con));
                        controller.navigate(R.id.player_frag, bundle);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

}