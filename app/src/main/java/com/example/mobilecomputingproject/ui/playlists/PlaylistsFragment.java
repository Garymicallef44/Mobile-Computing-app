package com.example.mobilecomputingproject.ui.playlists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.TrackLibViewModel;
import com.example.mobilecomputingproject.ui.TrackListAdapter;
import com.example.mobilecomputingproject.TrackActivity;


public class PlaylistsFragment extends Fragment {

    private TrackListAdapter adapter;
    private TrackLibViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1) RecyclerView setup
        RecyclerView rv = view.findViewById(R.id.rvPlaylists);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        // 2) Adapter and click listener
        adapter = new TrackListAdapter();
        adapter.setOnItemClickListener(item -> {
            // create and launch the intent here
            Intent intent = new Intent(requireContext(), TrackActivity.class);
            intent.putExtra(TrackActivity.EXTRA_ID,     item.getId());
            intent.putExtra(TrackActivity.EXTRA_TITLE,  item.getTitle());
            intent.putExtra(TrackActivity.EXTRA_ARTIST, item.getArtist());
            intent.putExtra(TrackActivity.EXTRA_GENRE,  item.getGenre());
            startActivity(intent);
        });
        rv.setAdapter(adapter);

        // 3) Obtain ViewModel
        vm = new ViewModelProvider(requireActivity()).get(TrackLibViewModel.class);

        // 4) Observe LiveData → Adapter
        vm.getTracks().observe(getViewLifecycleOwner(), trackItems -> {
            adapter.submitList(trackItems);
        });

        // 5) Trigger data load
        vm.loadAllTracks();
    }
}
