package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.databinding.RecyclerSearchBinding;
import com.lewabo.lewabo.utility.Utility;
import com.lewabo.lewabo.view.activity.PlayerDetails;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Todo_View_Holder> {
    Context context;
    List<Content> list;
    Utility utility;


    public SearchAdapter(List<Content> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerSearchBinding historyBinding;

        public Todo_View_Holder(RecyclerSearchBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(Content s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public SearchAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_search, parent, false);
        return new SearchAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final SearchAdapter.Todo_View_Holder holder, int position) {
        final Content bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.getImage().toString())
                    .fitCenter()
                    .into(holder.historyBinding.searchImage);

            holder.historyBinding.searchPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((AppCompatActivity) context).startActivity(new Intent(context, PlayerDetails.class).putExtra("video_id", bodyResponse.getResolutions().getPath240p()));
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

}