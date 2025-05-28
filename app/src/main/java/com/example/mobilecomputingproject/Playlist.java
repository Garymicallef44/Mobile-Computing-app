package com.example.mobilecomputingproject;

import com.example.mobilecomputingproject.data.TrackItem;

import java.util.List;

// Simple class representing a playlist
public class Playlist {
    public final String name;
    public final List<TrackItem> tracks;
    private String imageUri;
    public Playlist(String name, List<TrackItem> tracks) {
        this.name   = name;
        this.tracks = tracks;
    }
    // Image attributes
    public String getImageUri() { return imageUri; }
    public void setImageUri(String uri) { imageUri = uri; }
}