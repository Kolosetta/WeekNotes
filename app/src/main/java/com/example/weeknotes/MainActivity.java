package com.example.weeknotes;

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
import com.example.weeknotes.Note.*;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static final ArrayList<Note> notes = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(notes.isEmpty()) {
            notes.add(new Note("Title", "Чет сделать1", DayOfWeek.FRIDAY, Priority.HIGH));
            notes.add(new Note("Title2", "Чет сделать2", DayOfWeek.FRIDAY, Priority.LOW));
            notes.add(new Note("Title3", "Чет сделать3", DayOfWeek.FRIDAY, Priority.MEDIUM));
            notes.add(new Note("Title4", "Чет сделать4", DayOfWeek.FRIDAY, Priority.LOW));
            notes.add(new Note("Title5", "Чет сделать4", DayOfWeek.SATURDAY, Priority.MEDIUM));
        }

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
                notes.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
               // int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notes.remove(viewHolder.getAdapterPosition());
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