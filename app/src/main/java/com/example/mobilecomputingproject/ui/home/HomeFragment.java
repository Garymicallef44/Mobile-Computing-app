package com.example.mobilecomputingproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecomputingproject.Prefs;
import com.example.mobilecomputingproject.R;
import com.example.mobilecomputingproject.TrackActivity;
import com.example.mobilecomputingproject.TrackLibViewModel;
import com.example.mobilecomputingproject.ui.TrackListAdapter;

public class HomeFragment extends Fragment {
    private TrackListAdapter adapter;
    private TrackLibViewModel vm;
    private Prefs prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = new Prefs(requireContext());

        // RecyclerView setup
        RecyclerView rv = view.findViewById(R.id.rvSuggestions);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TrackListAdapter(false); // library mode false
        rv.setAdapter(adapter);

        // Obtain ViewModel
        vm = new ViewModelProvider(requireActivity())
                .get(TrackLibViewModel.class);

        // Observe random suggestions
        vm.getSuggestions().observe(getViewLifecycleOwner(), tracks -> {
            adapter.submitList(tracks);
        });

        // Trigger loading
        vm.loadSuggestions(3);

        adapter.setOnItemClickListener(item -> {
            Intent i = new Intent(requireContext(), TrackActivity.class);
            i.putExtra(TrackActivity.EXTRA_ID, item.getId());
            i.putExtra(TrackActivity.EXTRA_TITLE, item.getTitle());
            i.putExtra(TrackActivity.EXTRA_ARTIST, item.getArtist());
            i.putExtra(TrackActivity.EXTRA_GENRE, item.getGenre());
            startActivity(i);
        });

        adapter.setOnAddClickListener(item -> {
            prefs.addToPlaylist(item.getId());
            Toast.makeText(requireContext(),
                    "'" + item.getTitle() + "' added to playlist",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
