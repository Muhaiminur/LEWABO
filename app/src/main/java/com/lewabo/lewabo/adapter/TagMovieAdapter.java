package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.RecyclerTagMovieBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.List;

public class TagMovieAdapter extends RecyclerView.Adapter<TagMovieAdapter.Todo_View_Holder> {
    Context context;
    List<String> list;
    Utility utility;


    public TagMovieAdapter(List<String> to, Context c) {
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

        public void bind(String s) {
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
        final String bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.toString())
                    .fitCenter()
                    .into(holder.historyBinding.tagMovieImage);

            /*holder.historyBinding.historyCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    utility.openDialpad(bodyResponse.getPhone());
                }
            });*/
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}