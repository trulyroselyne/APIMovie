package com.example.apimovie.models.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowPlayingResult {
    @SerializedName("results")
    @Expose
    private List<NowPlaying> nowPlayingList;

    public List<NowPlaying> getNowPlayingList() {
        return nowPlayingList;
    }
}
