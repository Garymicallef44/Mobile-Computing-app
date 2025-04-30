package com.example.mobilecomputingproject.ui.playlists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
;import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.Prefs;
import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.TrackActivity;
import com.example.mobilecomputingproject.data.TrackHelper;
import com.example.mobilecomputingproject.data.TrackItem;
import com.example.mobilecomputingproject.ui.TrackListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlaylistsFragment extends Fragment {
    // Textview displays message when playlist is empty,
    // Prefs stores playlist contents, Helper fetches track details with SQLite and adapter binds track items to recycler view rows
    private TextView tvEmpty;
    private Prefs prefs;
    private TrackHelper db;
    private TrackListAdapter adapter;

    // Inflate fragment layout
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlists, container, false);
    }
    // Once view is created, intialise UI components, load playlist data and set up listeners.
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvEmpty = view.findViewById(R.id.tvEmpty);
        prefs = new Prefs(requireContext());
        db = new TrackHelper(requireContext());

        RecyclerView rv = view.findViewById(R.id.rvPlaylists);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TrackListAdapter(true);
        rv.setAdapter(adapter);

        // load added tracks
        List<TrackItem> saved = new ArrayList<>();
        for (String idStr : prefs.getPlaylistIds()) {
            TrackItem t = db.getTrackById(Integer.parseInt(idStr));
            if (t != null) saved.add(t);
        }
        adapter.submitList(saved);

        // Show empty message if needed
        boolean isEmpty = saved.isEmpty();
        rv.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);


        // Card click: open details
        adapter.setOnItemClickListener(item -> {
            Intent i = new Intent(requireContext(), TrackActivity.class);
            i.putExtra(TrackActivity.EXTRA_ID, item.getId());
            i.putExtra(TrackActivity.EXTRA_TITLE, item.getTitle());
            i.putExtra(TrackActivity.EXTRA_ARTIST, item.getArtist());
            i.putExtra(TrackActivity.EXTRA_GENRE, item.getGenre());
            startActivity(i);
        });

        // Handle remove button taps to remove tracks from the playlist
        adapter.setOnRemoveClickListener(item -> {
            prefs.removeFromPlaylist(item.getId());
            // rebuild list
            List<TrackItem> updated = new ArrayList<>();
            for (String s : prefs.getPlaylistIds()) {
                TrackItem t = db.getTrackById(Integer.parseInt(s));
                if (t != null) updated.add(t);
            }
            adapter.submitList(updated);
            // toggle empty view
            boolean nowEmpty = updated.isEmpty();
            rv.setVisibility(nowEmpty ? View.GONE : View.VISIBLE);
            tvEmpty.setVisibility(nowEmpty ? View.VISIBLE : View.GONE);
        });

        // Load only the tracks whose IDs were saved
        Set<String> idStrings = prefs.getPlaylistIds();
        List<TrackItem> savedTracks = new ArrayList<>();
        for (String s : idStrings) {
            int id = Integer.parseInt(s);
            TrackItem t = db.getTrackById(id);
            if (t != null) savedTracks.add(t);
        }
        adapter.submitList(savedTracks);
    }
}
