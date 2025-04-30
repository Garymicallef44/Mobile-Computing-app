package com.example.mobilecomputingproject.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.TrackActivity;
import com.example.mobilecomputingproject.data.TrackHelper;
import com.example.mobilecomputingproject.data.TrackItem;

import java.util.ArrayList;
import java.util.List;

// Adapter for displaying a list of tracks, it operates in two modes according to the state of isplaylistmode
// True shows a remove button on each card and false shows an add button on each card
public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {
    private final List<TrackItem> items = new ArrayList<>();
    private final boolean isPlaylistMode;
    // Listeners for clicking on cards or add/remove buttons
    private OnItemClickListener listener;
    private OnAddClickListener addListener;
    private OnRemoveClickListener removeListener;

    // Constructor that accepts a flag to determine the mode
    public TrackListAdapter(boolean isPlaylistMode) {
        this.isPlaylistMode = isPlaylistMode;
    }

    // Click event listener interfaces
    public interface OnAddClickListener {
        void onAddClick(TrackItem item);
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(TrackItem item);
    }

    public interface OnItemClickListener {
        void onItemClick(TrackItem item);
    }

    // Register a listener for tapping on cards or add/remove buttons
    public void setOnAddClickListener(OnAddClickListener l) {
        this.addListener = l;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnRemoveClickListener(OnRemoveClickListener rl) {
        removeListener = rl;
    }

    // Inflate row layout and wrap it in viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_track, parent, false);
        return new ViewHolder(row);
    }

    // Bind data and event handlers to the viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TrackItem track = items.get(position);
        // Populate text fields
        holder.title.setText(track.getTitle());
        holder.artist.setText(track.getArtist());
        holder.genre.setText(track.getGenre());

        // Attach click listener to the card (or the entire itemView)
        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TrackActivity.class);

            TrackHelper trackHelper = new TrackHelper(v.getContext());

            intent.putExtra("id", track.getId());
            intent.putExtra("title", track.getTitle());
            intent.putExtra("artist", track.getArtist());
            intent.putExtra("genre", track.getGenre());
            v.getContext().startActivity(intent);

        });

        // Configures the action button for each corresponding mode
        if (isPlaylistMode) {
            holder.btnAction.setText("Remove");
            holder.btnAction.setOnClickListener(v -> {
                if (removeListener != null)
                    removeListener.onRemoveClick(track);
            });
        } else {
            holder.btnAction.setText("Add");
            holder.btnAction.setOnClickListener(v -> {
                if (addListener != null)
                    addListener.onAddClick(track);
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Replace current list with a new one and refresh recycler view
    public void submitList(List<TrackItem> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    // Viewholder caches references to the item's views for efficiency
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView title;
        TextView artist;
        TextView genre;
        Button btnAction;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_track);
            title = itemView.findViewById(R.id.textTitle);
            artist = itemView.findViewById(R.id.textArtist);
            genre = itemView.findViewById(R.id.textGenre);
            btnAction = itemView.findViewById(R.id.btnAdd);
        }
    }
}
