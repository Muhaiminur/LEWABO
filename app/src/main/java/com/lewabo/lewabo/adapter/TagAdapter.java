package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.TagModel;
import com.lewabo.lewabo.data.moviecontent.Content;
import com.lewabo.lewabo.data.moviecontent.Tagdata;
import com.lewabo.lewabo.databinding.RecyclerTagBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.Todo_View_Holder> {
    Context context;
    List<Tagdata> list;
    Utility utility;


    public TagAdapter(List<Tagdata> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerTagBinding historyBinding;

        public Todo_View_Holder(RecyclerTagBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(Tagdata s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public TagAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerTagBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_tag, parent, false);
        return new TagAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final TagAdapter.Todo_View_Holder holder, int position) {
        final Tagdata bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            holder.historyBinding.tagName.setText(bodyResponse.getTitle());
            List<Content> movielist = new ArrayList<>();
            movielist.addAll(bodyResponse.getContents());
            TagMovieAdapter taglistMovieAdapter = new TagMovieAdapter(movielist, context);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.historyBinding.tagRecycler.setLayoutManager(mLayoutManager);
            holder.historyBinding.tagRecycler.setItemAnimator(new DefaultItemAnimator());
            holder.historyBinding.tagRecycler.setAdapter(taglistMovieAdapter);
            taglistMovieAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}