package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.data.moviecontent.Genre;
import com.lewabo.lewabo.databinding.RecyclerScifiBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.List;

public class ScifiAdapter extends RecyclerView.Adapter<ScifiAdapter.Todo_View_Holder> {
    Context context;
    List<Genre> list;
    Utility utility;


    public ScifiAdapter(List<Genre> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerScifiBinding historyBinding;

        public Todo_View_Holder(RecyclerScifiBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(Genre s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public ScifiAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerScifiBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_scifi, parent, false);
        return new ScifiAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final ScifiAdapter.Todo_View_Holder holder, int position) {
        final Genre bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            holder.historyBinding.scifiTittle.setText(bodyResponse.getTitle().toString());
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}