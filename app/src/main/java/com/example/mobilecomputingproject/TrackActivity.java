package com.example.mobilecomputingproject;

import com.example.mobilecomputingproject.data.TrackHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;

public class TrackActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    public static final String EXTRA_ID     = "id";
    public static final String EXTRA_TITLE  = "title";
    public static final String EXTRA_ARTIST = "artist";
    public static final String EXTRA_GENRE  = "genre";
    private SQLiteDatabase db;

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

        if (id != -1){
            String resName = "track" + id;
            int resId = getResources().getIdentifier(resName, "raw", getPackageName());

            if (resId != 0){
                mediaPlayer = MediaPlayer.create(this, resId);
                mediaPlayer.start();
            } else {
                Toast.makeText(this, "Audio file not found:" + resName, Toast.LENGTH_SHORT).show();
            }
        }

        // 3) Populate UI
        tvId.setText("ID: " + id);  // even if id == -1, show fallback
        if (title != null) tvTitle .setText(title);
        if (artist != null) tvArtist.setText(artist);
        if (genre != null) tvGenre .setText(genre);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();;
        // Release resources
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}
