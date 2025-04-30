package com.example.mobilecomputingproject.data;

import java.util.ArrayList;
import java.util.List;

// Class that groups multiple track items under a common title.
public class TrackList{

    public List<TrackItem> tracks = new ArrayList<>();

    public String list_title;
    // Sets title of track list
    public TrackList(String title){
        this.list_title = title;
    }
    public void add(TrackItem newTrack){
        tracks.add(newTrack);
    }

    // Returns full track list
    public List<TrackItem> getTracks(){
        return tracks;
    }
}
