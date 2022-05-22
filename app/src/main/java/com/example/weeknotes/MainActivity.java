package com.example.weeknotes;

import static android.provider.BaseColumns._ID;
import static com.example.weeknotes.NotesContract.NotesEntry.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weeknotes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NotesDatabase database;
    private ArrayList<Note> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = NotesDatabase.getInstance(this);
        getData();

        NotesAdapter adapter = new NotesAdapter(notes);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteCLick(int position) {
               Toast.makeText(MainActivity.this, "Clicked",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoteLongClick(int position) {
                //Do nothing
            }
        });

        binding.addNoteFAB.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivity(intent);
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
                Note note = notes.get(viewHolder.getAdapterPosition());
                database.notesDao().deleteNote(note);
                getData();
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                if(notes.isEmpty()){
                    binding.noNotesTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(notes.isEmpty()){
            binding.noNotesTextView.setVisibility(View.VISIBLE);
        }
    }

    private void getData(){
        List<Note> notesFromDB = database.notesDao().getAllNotes();
        notes.clear();
        notes.addAll(notesFromDB);
    }
}