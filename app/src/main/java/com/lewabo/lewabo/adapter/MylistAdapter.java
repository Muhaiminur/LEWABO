package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.RecyclerMylistBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.List;

public class MylistAdapter extends RecyclerView.Adapter<MylistAdapter.Todo_View_Holder> {
    Context context;
    List<String> list;
    Utility utility;


    public MylistAdapter(List<String> to, Context c) {
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

        public void bind(String s) {
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
        final String bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            Glide.with(context)
                    .load(bodyResponse.toString())
                    .fitCenter()
                    .into(holder.historyBinding.mylistImage);

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