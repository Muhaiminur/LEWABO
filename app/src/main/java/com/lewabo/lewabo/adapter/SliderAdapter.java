package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lewabo.lewabo.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<String> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(String sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);
        if (position == 0) {
            Glide.with(viewHolder.itemView).load("https://i.ibb.co/g78J3ww/03-Getting-Start-01.png").into(viewHolder.imageViewBackground);
            viewHolder.t1.setText("unlimited films,\n" + "TV programmes\n" + "& more");
            viewHolder.t2.setText("Watch anywhere, cancel at any time");
        } else if (position == 1) {
            Glide.with(viewHolder.itemView).load("https://i.ibb.co/7grkTCQ/05-Getting-Start-03.png").into(viewHolder.imageViewBackground);
            viewHolder.t1.setText("No annoying\n" + "contracts");
            viewHolder.t2.setText("Join Today, cancel at any time");
        } else if (position == 2) {
            Glide.with(viewHolder.itemView).load("https://i.ibb.co/QMRRfJH/06-Getting-Start-04.png").into(viewHolder.imageViewBackground);
            viewHolder.t1.setText("Watch on\n" + "Any device");
            viewHolder.t2.setText("stream on your phone, tablet, laptop, tv\n" + "and more");
        } else {
            Glide.with(viewHolder.itemView).load(sliderItem.toString()).into(viewHolder.imageViewBackground);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView t1;
        TextView t2;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.slider_image);
            t1 = itemView.findViewById(R.id.splash_t1);
            t2 = itemView.findViewById(R.id.splash_t2);
            this.itemView = itemView;
        }
    }

}