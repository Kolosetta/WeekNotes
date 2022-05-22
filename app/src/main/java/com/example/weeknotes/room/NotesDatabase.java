package com.example.weeknotes.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weeknotes.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
abstract public class NotesDatabase extends RoomDatabase {
    private static NotesDatabase database;
    private static final String DB_NAME = "notesDatabase.db";
    private static final Object LOCK = new Object();

    public static NotesDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).build();
            }
            return database;
        }
    }

    public abstract NotesDao notesDao();
}
