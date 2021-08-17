package com.lewabo.lewabo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandingModel {

    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("catgeoryId")
    @Expose
    private Integer catgeoryId;
    @SerializedName("catgeoryTitle")
    @Expose
    private String catgeoryTitle;
    @SerializedName("castBy")
    @Expose
    private List<String> castBy = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("maturityRating")
    @Expose
    private String maturityRating;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getCatgeoryId() {
        return catgeoryId;
    }

    public void setCatgeoryId(Integer catgeoryId) {
        this.catgeoryId = catgeoryId;
    }

    public String getCatgeoryTitle() {
        return catgeoryTitle;
    }

    public void setCatgeoryTitle(String catgeoryTitle) {
        this.catgeoryTitle = catgeoryTitle;
    }

    public List<String> getCastBy() {
        return castBy;
    }

    public void setCastBy(List<String> castBy) {
        this.castBy = castBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    @Override
    public String toString() {
        return "LandingModel{" +
                "duration=" + duration +
                ", image='" + image + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", catgeoryId=" + catgeoryId +
                ", catgeoryTitle='" + catgeoryTitle + '\'' +
                ", castBy=" + castBy +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", maturityRating='" + maturityRating + '\'' +
                '}';
    }
}