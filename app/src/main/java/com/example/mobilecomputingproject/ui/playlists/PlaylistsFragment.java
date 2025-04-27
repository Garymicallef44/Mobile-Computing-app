package com.example.mobilecomputingproject.ui.playlists;

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

public class PlaylistsFragment extends Fragment {

    private TrackListAdapter adapter;

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
        TrackListAdapter adapter = new TrackListAdapter();
        rv.setAdapter(adapter);

        // 2) Obtain ViewModel
        TrackLibViewModel vm = new ViewModelProvider(requireActivity())
                .get(TrackLibViewModel.class);

        // 3) Observe LiveData → Adapter
        vm.getTracks().observe(getViewLifecycleOwner(), adapter::submitList);

        // 4) Trigger data load
        vm.loadAllTracks();
    }

}