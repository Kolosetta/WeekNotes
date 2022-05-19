package com.example.weeknotes;

import static com.example.weeknotes.MainActivity.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;

import com.example.weeknotes.databinding.ActivityAddNoteBinding;
import com.example.weeknotes.Note.*;


public class AddNoteActivity extends AppCompatActivity {

    ActivityAddNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            String title = binding.editTextTitle.getText().toString().trim();
            String description = binding.editTextDescription.getText().toString().trim();
            DayOfWeek dayOfWeek = DayOfWeek.getDay(binding.spinnerDayOfWeek.getSelectedItem().toString());
            Priority priority = Priority.getPriority(((RadioButton)findViewById(binding.radioGroupPriority.getCheckedRadioButtonId())).getText().toString());
            notes.add(new Note(title, description, dayOfWeek, priority));
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}