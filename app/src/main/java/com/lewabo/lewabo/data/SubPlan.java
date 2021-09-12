package com.lewabo.lewabo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubPlan {
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("resolution")
    @Expose
    private String resolution;
    @SerializedName("quality")
    @Expose
    private String quality;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "SubPlan{" +
                "duration='" + duration + '\'' +
                ", price='" + price + '\'' +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", day='" + day + '\'' +
                ", resolution='" + resolution + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }
}