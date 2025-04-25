package com.example.mobilecomputingproject.data;

public class TrackItem{
    private int trackId;
    private String title;
    private String artist;
    private String genre;


    public TrackItem(int trackId, String title, String artist, String genre){
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
    }

    public TrackItem(String title, String artist, String genre){
        this.title = title;
        this.genre = genre;
        this.artist = artist;
    }



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