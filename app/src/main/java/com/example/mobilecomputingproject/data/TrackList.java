package com.example.mobilecomputingproject.data;

import java.util.ArrayList;
import java.util.List;

public class TrackList{

    public List<TrackItem> tracks = new ArrayList<>();

    public String list_title;


    public TrackList(String title){
        this.list_title = title;
    }
    public void add(TrackItem newTrack){
        tracks.add(newTrack);
    }

    public TrackItem get(int index){
        return tracks.get(index);
    }

    public List<TrackItem> getTracks(){
        return tracks;
    }
    public String getTitle(){
        return this.list_title;
    }
}
