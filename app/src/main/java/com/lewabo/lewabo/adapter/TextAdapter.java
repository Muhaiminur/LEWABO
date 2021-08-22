package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lewabo.lewabo.R;
import com.lewabo.lewabo.databinding.RecyclerTextBinding;
import com.lewabo.lewabo.utility.Utility;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.Todo_View_Holder> {
    Context context;
    List<String> list;
    Utility utility;


    public TextAdapter(List<String> to, Context c) {
        list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        RecyclerTextBinding historyBinding;

        public Todo_View_Holder(RecyclerTextBinding view) {
            super(view.getRoot());
            this.historyBinding = view;
        }

        public void bind(String s) {
            //historyBinding.setHistory(deliveryModel);
            historyBinding.executePendingBindings();
        }
    }

    @Override
    public TextAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerTextBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_text, parent, false);
        return new TextAdapter.Todo_View_Holder(binding);
    }

    @Override
    public void onBindViewHolder(final TextAdapter.Todo_View_Holder holder, int position) {
        final String bodyResponse = list.get(position);
        try {
            holder.bind(bodyResponse);
            holder.historyBinding.textName.setText(bodyResponse.toString());
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}