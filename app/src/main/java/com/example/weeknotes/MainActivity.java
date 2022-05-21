package com.example.weeknotes;

import static android.provider.BaseColumns._ID;
import static com.example.weeknotes.NotesContract.NotesEntry.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weeknotes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import com.example.weeknotes.Note.*;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        //database.delete(NotesContract.NotesEntry.TABLE_NAME, null, null);
        ArrayList<Note> noteFromDb = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME, null, null, null,null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String dayOfWeek = cursor.getString(3);
            String priority = cursor.getString(4);
            Note note = new Note(id, title, description, DayOfWeek.getDay(dayOfWeek), Priority.getPriority(priority));
            noteFromDb.add(note);
        }
        cursor.close();

        NotesAdapter adapter = new NotesAdapter(noteFromDb);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteCLick(int position) {
               Toast.makeText(MainActivity.this, "Clicked",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoteLongClick(int position) {
                int id = noteFromDb.get(position).getId();
                String where = _ID + " = ?";
                String[] whereArgs = new String[]{String.valueOf(id)};
                database.delete(TABLE_NAME, where, whereArgs);
                noteFromDb.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int id = noteFromDb.get(viewHolder.getAdapterPosition()).getId();
                String where = _ID + " = ?";
                String[] whereArgs = new String[]{String.valueOf(id)};
                database.delete(TABLE_NAME, where, whereArgs);
                noteFromDb.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }
    
    public void onCLickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}