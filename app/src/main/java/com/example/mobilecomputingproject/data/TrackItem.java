package com.example.mobilecomputingproject.data;

// Simple class representing a music track
public class TrackItem{
    private int trackId;
    private String title;
    private String artist;
    private String genre;

    // Full constructor
    public TrackItem(int trackId, String title, String artist, String genre){
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
    }
    // Partial constructor used when creating a new track before insertion, Id is assigned by db
    public TrackItem(String title, String artist, String genre){
        this.title = title;
        this.genre = genre;
        this.artist = artist;
    }

    // Returns respective track details
    public int getId(){
        return trackId;
    }

    public String getTitle(){
        return this.title;
    }

    public String getGenre(){
        return genre;
    }

    public String getArtist(){
        return this.artist;
    }
}