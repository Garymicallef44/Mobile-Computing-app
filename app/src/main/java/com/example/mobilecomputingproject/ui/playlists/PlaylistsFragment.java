package com.example.mobilecomputingproject.ui.playlists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.TrackLibViewModel;
import com.example.mobilecomputingproject.ui.TrackListAdapter;

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
            // Handle click: show toast or navigate
            Toast.makeText(requireContext(), "Clicked: " + item.getTitle(), Toast.LENGTH_LONG).show();
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
