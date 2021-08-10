package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.RecyclerComingsoonBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.List;

public class ComingsoonAdapter extends RecyclerView.Adapter<ComingsoonAdapter.Todo_View_Holder> {
    Context context;
    List<String> list;
    Utility utility;


    public ComingsoonAdapter(List<String> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerComingsoonBinding historyBinding;

        public Todo_View_Holder(RecyclerComingsoonBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(String s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public ComingsoonAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerComingsoonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_comingsoon, parent, false);
        return new ComingsoonAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final ComingsoonAdapter.Todo_View_Holder holder, int position) {
        final String bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.toString())
                    .fitCenter()
                    .into(holder.historyBinding.comingImage);
            Glide.with(context)
                    .load(bodyResponse.toString())
                    .fitCenter()
                    .into(holder.historyBinding.comingImage2);

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