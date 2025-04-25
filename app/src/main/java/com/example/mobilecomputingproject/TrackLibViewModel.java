package com.example.mobilecomputingproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilecomputingproject.data.TrackList;

import java.util.List;

public class TrackLibViewModel extends ViewModel {

    private final MutableLiveData<List<TrackList>> playList = new MutableLiveData<>();


    public void setPlayListist(List<TrackList> list){
        playList.setValue(list);
    }

    public LiveData<List<TrackList>> getPlaylists() {
        return playList;
    }



}