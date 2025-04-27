package com.example.mobilecomputingproject;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import com.example.mobilecomputingproject.R;

public class TrackActivity extends AppCompatActivity {
    public static final String EXTRA_ID     = "id";
    public static final String EXTRA_TITLE  = "title";
    public static final String EXTRA_ARTIST = "artist";
    public static final String EXTRA_GENRE  = "genre";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);  // inflate the layout

        // 1) Find views
        TextView tvId = findViewById(R.id.trackId);
        TextView tvTitle = findViewById(R.id.trackTitle);
        TextView tvArtist = findViewById(R.id.trackArtist);
        TextView tvGenre = findViewById(R.id.trackGenre);

        // 2) Get Intent extras
        int id = getIntent().getIntExtra(EXTRA_ID, -1);  // default -1 if missing
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String artist = getIntent().getStringExtra(EXTRA_ARTIST);
        String genre = getIntent().getStringExtra(EXTRA_GENRE);

        // 3) Populate UI
        tvId.setText("ID: " + id);  // even if id == -1, show fallback
        if (title != null) tvTitle .setText(title);
        if (artist != null) tvArtist.setText(artist);
        if (genre != null) tvGenre .setText(genre);
    }
}
