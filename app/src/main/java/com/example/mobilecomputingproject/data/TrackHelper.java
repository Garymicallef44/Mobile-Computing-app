package com.example.mobilecomputingproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

// This class manages db creation and version management, extending SQLiteOpenHelper which handles opening and upgrading the db.
public class TrackHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tracks-lib.db";
    public static final int DATABASE_VERSION = 1;
    private final String _title = TrackDet.TrackDetails.COLUMN_NAME_TITLE;
    private final String _artist = TrackDet.TrackDetails.COLUMN_NAME_ARTIST;
    private final String _genre = TrackDet.TrackDetails.COLUMN_NAME_GENRE;

    public TrackHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called on database creation
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTables());
    }
    // Called when database needs to be upgraded, drops old table and recreates it
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTables());
        onCreate(db);
    }
    // Same as onupgrade but is called when the db version is lower than requested
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    // Builds SQL string to create the tracks table
    private String createTables() {
        return "CREATE TABLE " + TrackDet.TrackDetails.TABLE_NAME + " (" +
                TrackDet.TrackDetails._ID + " INTEGER PRIMARY KEY, " +
                _title + " varchar, " +
                _artist + " varchar, " +
                _genre + " varchar)";
    }
    // Drops the tracks table if it exists
    private String dropTables() {
        return "DROP TABLE IF EXISTS " + TrackDet.TrackDetails.TABLE_NAME;
    }

    // Adds track details to the database if empty (db is always empty when the app runs so it updates if any new tracks are added)
    public void initiateTables() {
        if (getTracks().isEmpty()) {
            this.insertTrack(new TrackItem("Upbeat Music", "Infraction", "Synth"));
            this.insertTrack(new TrackItem("Mortals", "NCS", "FTrap"));
            this.insertTrack(new TrackItem("My Heart", "NCS","Drumstep"));
            this.insertTrack(new TrackItem("Riebeck Cover", "Gary", "Classical"));
            this.insertTrack(new TrackItem("Sky High", "NCS", "ProgHouse"));
            this.insertTrack(new TrackItem("Blank", "NCS", "DubstepM"));
        }
    }

    // Inserts a new track item into the database
    public long insertTrack(TrackItem track) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(_title, track.getTitle());
        values.put(_artist, track.getArtist());
        values.put(_genre, track.getGenre());

        long id = db.insert(TrackDet.TrackDetails.TABLE_NAME, null, values);

        return id;
    }

    // Retrieves all tracks by title alphabetically
    public ArrayList<TrackItem> getTracks() {
        ArrayList<TrackItem> tracks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String[] projection = {
                BaseColumns._ID,
                _title,
                _artist,
                _genre
        };

        String sortOrder = _title + " ASC";
        // Wraps raw sql and returns a cursor
        Cursor cursor = db.query(TrackDet.TrackDetails.TABLE_NAME, projection, null, null, null, null, sortOrder);
        // Iterate through results and build track item list
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(_title));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(_artist));
            String genre = cursor.getString(cursor.getColumnIndexOrThrow(_genre));
            TrackItem track = new TrackItem((int) id, title, artist, genre);
            tracks.add(track);
        }
        cursor.close(); // Closes cursor to free resources

        return tracks;
    }
    // Retrieves a track by its Id in the database
    public TrackItem getTrackById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                _title,
                _artist,
                _genre
        };

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {Long.toString(id)};
        String sortOrder = _title + " ASC";

        Cursor cursor = db.query(TrackDet.TrackDetails.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        TrackItem track = null;
        while (cursor.moveToNext()) {
            long trackId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(_title));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(_artist));
            String genre = cursor.getString(cursor.getColumnIndexOrThrow(_genre));
            track = new TrackItem((int) trackId, title, artist, genre);
        }
        cursor.close();
        return track;
    }
    // Retrieves a random set of tracks
    public TrackList getTracksRandom(int no, String pTitle) {
        TrackList trackList = new TrackList(pTitle);
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TrackDet.TrackDetails.TABLE_NAME + " ORDER BY RANDOM() LIMIT ? ";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(no)});

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(_title));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(_artist));
            String genre = cursor.getString(cursor.getColumnIndexOrThrow(_genre));
            trackList.add(new TrackItem((int) id, title, artist, genre));
        }
        cursor.close();
        return trackList;
    }


}
