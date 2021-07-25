package com.lewabo.lewabo.data;

import java.util.ArrayList;
import java.util.List;

public class TagModel {
    String tagname;
    List<String> tagmovie = new ArrayList<>();

    public TagModel(String tagname, List<String> tagmovie) {
        this.tagname = tagname;
        this.tagmovie = tagmovie;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public List<String> getTagmovie() {
        return tagmovie;
    }

    public void setTagmovie(List<String> tagmovie) {
        this.tagmovie = tagmovie;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                "tagname='" + tagname + '\'' +
                ", tagmovie=" + tagmovie +
                '}';
    }
}
