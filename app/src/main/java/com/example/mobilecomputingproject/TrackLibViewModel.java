package com.example.mobilecomputingproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilecomputingproject.data.TrackHelper;
import com.example.mobilecomputingproject.data.TrackItem;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TrackLibViewModel extends AndroidViewModel {
    private final TrackHelper trackHelper;
    private final MutableLiveData<List<TrackItem>> tracks = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public TrackLibViewModel(@NonNull Application app) {
        super(app);
        // Initialize the final helper field (blank finals must be set here) :contentReference[oaicite:3]{index=3}
        trackHelper = new TrackHelper(app);
        trackHelper.initiateTables();
    }

    public LiveData<List<TrackItem>> getTracks() {
        return tracks;
    }

    public void loadAllTracks() {
        executor.execute(() -> {
            List<TrackItem> list = trackHelper.getTracks();
            // postValue is safe from background threads :contentReference[oaicite:4]{index=4}
            tracks.postValue(list);
        });
    }
}
