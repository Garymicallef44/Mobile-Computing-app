package com.example.mobilecomputingproject.ui.playlists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaylistsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlaylistsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Your added tracks will appear here");
    }

    public LiveData<String> getText() {
        return mText;
    }
}