package com.example.mobilecomputingproject.data;
import android.provider.BaseColumns;

public class TrackDet {
    // Private constructor
    private TrackDet(){}

    // This class defines table contents, naming the table and columns respectively
    public static class TrackDetails implements BaseColumns {
        public static final String TABLE_NAME = "tracks";
        public static final String COLUMN_NAME_TITLE = "_title";
        public static final String COLUMN_NAME_ARTIST = "_artist";
        public static final String COLUMN_NAME_GENRE = "_genre";
    }
}
