package com.lewabo.lewabo.data.moviecontent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Content {

    @SerializedName("brief")
    @Expose
    private String brief;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("catgeoryTitle")
    @Expose
    private String catgeoryTitle;
    @SerializedName("castBy")
    @Expose
    private List<String> castBy = null;
    @SerializedName("resolutions")
    @Expose
    private Resolutions resolutions;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("bannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("catgeoryId")
    @Expose
    private Integer catgeoryId;
    @SerializedName("producer")
    @Expose
    private String producer;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("maturityRating")
    @Expose
    private String maturityRating;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("status")
    @Expose
    private String status;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
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

    public Resolutions getResolutions() {
        return resolutions;
    }

    public void setResolutions(Resolutions resolutions) {
        this.resolutions = resolutions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Integer getCatgeoryId() {
        return catgeoryId;
    }

    public void setCatgeoryId(Integer catgeoryId) {
        this.catgeoryId = catgeoryId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Content{" +
                "brief='" + brief + '\'' +
                ", image='" + image + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", director='" + director + '\'' +
                ", catgeoryTitle='" + catgeoryTitle + '\'' +
                ", castBy=" + castBy +
                ", resolutions=" + resolutions +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", bannerImage='" + bannerImage + '\'' +
                ", genres=" + genres +
                ", catgeoryId=" + catgeoryId +
                ", producer='" + producer + '\'' +
                ", id=" + id +
                ", writer='" + writer + '\'' +
                ", maturityRating='" + maturityRating + '\'' +
                ", likes=" + likes +
                ", status='" + status + '\'' +
                '}';
    }
}