package com.example.mobilecomputingproject.ui;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.Playlist;
import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.data.TrackItem;

import java.util.ArrayList;
import java.util.List;

// Recycler view adapter that supports the header and child view types, one displays the name as a section header and the other displays a track item belonging to the header.
public class PlaylistsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Distinguishes between types
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CHILD  = 1;
    private final List<Object> displayList;  // mix of Playlist (header) and TrackItem (child)

    // Constructor builds the flat display list from hierarchical data
    public PlaylistsAdapter(List<Playlist> data) {
        displayList = new ArrayList<>();
        for (Playlist pl : data) {
            displayList.add(pl.name); // header
            for (TrackItem t : pl.tracks) {
                displayList.add(t);    // child
            }
        }
    }

    // Determine which view type to use based on data stored in displaylist
    @Override
    public int getItemViewType(int pos) {
        return (displayList.get(pos) instanceof String)
                ? TYPE_HEADER : TYPE_CHILD;
    }

    // Gets total number of items
    @Override
    public int getItemCount() {
        return displayList.size();
    }

    // Inflate appropriate layout based on view type and wrap it in matching viewholder
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
    // Bind data to viewholder
    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder vh, int pos) {

        Object item = displayList.get(pos);
        if (vh instanceof HeaderVH) {
            ((HeaderVH) vh).bind((Playlist) item);
        } else {
            ((ChildVH) vh).bind((TrackItem) item);
        }
    }

    // ViewHolder for header items, name and cover
    static class HeaderVH extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        HeaderVH(View v) {
            super(v);
            name = v.findViewById(R.id.tvPlaylistName);
            cover = v.findViewById(R.id.imgCover);
        }
        // Bind playlist data to header view
        void bind(Playlist pl) {
            name.setText(pl.name);
            // Get URI string
            String uriString = pl.getImageUri();
            if (uriString != null) {
                cover.setImageURI(Uri.parse(uriString));
            } else {
                // If no image was set, use default cover image
                cover.setImageResource(R.drawable.ic_default_cover);
            }
        }

    }

    // ViewHolder for child items (tracks)
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
