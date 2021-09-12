package com.lewabo.lewabo.data.moviecontent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resolutions {

    @SerializedName("path1080p")
    @Expose
    private String path1080p;
    @SerializedName("path240p")
    @Expose
    private String path240p;
    @SerializedName("path720p")
    @Expose
    private String path720p;
    @SerializedName("path144p")
    @Expose
    private String path144p;
    @SerializedName("path360p")
    @Expose
    private String path360p;
    @SerializedName("path480p")
    @Expose
    private String path480p;

    public String getPath1080p() {
        return path1080p;
    }

    public void setPath1080p(String path1080p) {
        this.path1080p = path1080p;
    }

    public String getPath240p() {
        return path240p;
    }

    public void setPath240p(String path240p) {
        this.path240p = path240p;
    }

    public String getPath720p() {
        return path720p;
    }

    public void setPath720p(String path720p) {
        this.path720p = path720p;
    }

    public String getPath144p() {
        return path144p;
    }

    public void setPath144p(String path144p) {
        this.path144p = path144p;
    }

    public String getPath360p() {
        return path360p;
    }

    public void setPath360p(String path360p) {
        this.path360p = path360p;
    }

    public String getPath480p() {
        return path480p;
    }

    public void setPath480p(String path480p) {
        this.path480p = path480p;
    }

    @Override
    public String toString() {
        return "Resolutions{" +
                "path1080p='" + path1080p + '\'' +
                ", path240p='" + path240p + '\'' +
                ", path720p='" + path720p + '\'' +
                ", path144p='" + path144p + '\'' +
                ", path360p='" + path360p + '\'' +
                ", path480p='" + path480p + '\'' +
                '}';
    }
}