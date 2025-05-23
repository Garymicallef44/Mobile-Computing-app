package com.example.mobilecomputingproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilecomputingproject.data.TrackHelper;
import com.example.mobilecomputingproject.data.TrackItem;
import com.example.mobilecomputingproject.data.TrackList;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Viewmodel provides track data to the UI.
public class TrackLibViewModel extends AndroidViewModel {
    // Helper for SQL operations, live data holding the list of all tracks, executor runs db operations off main thread,
    // And live data holding a list of randomly selected track suggestions
    private final TrackHelper trackHelper;
    private final MutableLiveData<List<TrackItem>> tracks = new MutableLiveData<>();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<List<TrackItem>> suggestions = new MutableLiveData<>();

    public TrackLibViewModel(@NonNull Application app) {
        super(app);
        // Initialize the final helper field (blank finals must be set here)
        trackHelper = new TrackHelper(app);
        trackHelper.initiateTables();
    }

    public LiveData<List<TrackItem>> getTracks() {
        return tracks;
    }

    // Get suggestions randomly
    public LiveData<List<TrackItem>> getSuggestions() {
        return suggestions;
    }

    // Load a number of tracks in a bg thread and posts the resulting list into the suggestions livedata
    public void loadSuggestions(int count) {
        executor.execute(() -> {
            // call the instance helper
            TrackList list = trackHelper.getTracksRandom(count, "Suggestions");
            // post only the List<TrackItem>
            suggestions.postValue(list.getTracks());
        });
    }
    // Loads all tracks in a bg thread then posts the resulting list into tracks livedata
    public void loadAllTracks() {
        executor.execute(() -> {
            List<TrackItem> list = trackHelper.getTracks();
            // postValue is safe from background threads
            tracks.postValue(list);
        });
    }
}
