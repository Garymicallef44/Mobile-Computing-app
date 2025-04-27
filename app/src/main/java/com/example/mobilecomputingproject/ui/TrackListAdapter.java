package com.example.mobilecomputingproject.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.data.TrackItem;

import java.util.ArrayList;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {
    private final List<TrackItem> items = new ArrayList<>();

    // ViewHolder holds references to the views of a ro
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView artist;
        TextView genre;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            artist = itemView.findViewById(R.id.textArtist);
            genre = itemView.findViewById(R.id.textGenre);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_track, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrackItem track = items.get(position);
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getArtist());
        holder.genre.setText(track.getGenre());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /** Replace the current list with a new one and refresh */
    public void submitList(List<TrackItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }
}

