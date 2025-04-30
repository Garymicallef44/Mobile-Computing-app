package com.example.mobilecomputingproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.Playlist;
import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.data.TrackItem;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CHILD  = 1;

    private final List<Object> displayList;  // mix of Playlist (header) and TrackItem (child)

    public PlaylistsAdapter(List<Playlist> data) {
        displayList = new ArrayList<>();
        for (Playlist pl : data) {
            displayList.add(pl.name); // header
            for (TrackItem t : pl.tracks) {
                displayList.add(t);    // child
            }
        }
    }

    @Override
    public int getItemViewType(int pos) {
        return (displayList.get(pos) instanceof String)
                ? TYPE_HEADER : TYPE_CHILD;
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            View v = inf.inflate(R.layout.item_playlist_header, parent, false);
            return new HeaderVH(v);
        } else {
            View v = inf.inflate(R.layout.item_playlist_track, parent, false);
            return new ChildVH(v);
        }
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder vh, int pos) {

        Object item = displayList.get(pos);
        if (vh instanceof HeaderVH) {
            ((HeaderVH) vh).bind((String) item);
        } else {
            ((ChildVH) vh).bind((TrackItem) item);
        }
    }

    // ViewHolders
    static class HeaderVH extends RecyclerView.ViewHolder {
        TextView name;
        HeaderVH(View v) {
            super(v);
            name = v.findViewById(R.id.tvPlaylistName);
        }
        void bind(String playlistName) {
            name.setText(playlistName);
        }
    }

    static class ChildVH extends RecyclerView.ViewHolder {
        TextView title, artist;
        ChildVH(View v) {
            super(v);
            title  = v.findViewById(R.id.tvTrackTitle);
            artist = v.findViewById(R.id.tvTrackArtist);
        }
        void bind(TrackItem t) {
            title.setText(t.getTitle());
            artist.setText(t.getArtist());
        }
    }
}
