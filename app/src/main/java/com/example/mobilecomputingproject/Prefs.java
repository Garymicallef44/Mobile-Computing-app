package com.example.mobilecomputingproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Prefs {
    private static final String PREFS_NAME   = "app_prefs";
    private static final String KEY_PLAYLIST = "playlist_ids";

    private final SharedPreferences prefs;

    public Prefs(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /** Add a track to the playlist (id as string) */
    public void addToPlaylist(int trackId) {
        Set<String> ids = new HashSet<>(
                prefs.getStringSet(KEY_PLAYLIST, Collections.emptySet())
        );
        ids.add(String.valueOf(trackId));
        prefs.edit()
                .putStringSet(KEY_PLAYLIST, ids)
                .apply();
    }

    public void removeFromPlaylist(int trackId) {
        Set<String> ids = new HashSet<>(
                prefs.getStringSet(KEY_PLAYLIST, Collections.emptySet())
        );
        String key = String.valueOf(trackId);
        if (ids.remove(key)) {
            prefs.edit()
                    .putStringSet(KEY_PLAYLIST, ids)
                    .apply();
        }
    }

    /** Get all saved track IDs in the playlist */
    public Set<String> getPlaylistIds() {
        return prefs.getStringSet(KEY_PLAYLIST, Collections.emptySet());
    }

    /** Clear the playlist entirely */
    public void clearPlaylist() {
        prefs.edit()
                .remove(KEY_PLAYLIST)
                .apply();
    }
}
