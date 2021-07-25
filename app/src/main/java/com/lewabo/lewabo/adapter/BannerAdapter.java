package com.lewabo.lewabo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lewabo.lewabo.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.BannerAdapterVH> {

    private Context context;
    private List<String> mSliderItems = new ArrayList<>();

    public BannerAdapter(Context context) {
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
    public BannerAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_banner, null);
        return new BannerAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(BannerAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.toString())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

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

    class BannerAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public BannerAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.banner_image);
            this.itemView = itemView;
        }
    }
}