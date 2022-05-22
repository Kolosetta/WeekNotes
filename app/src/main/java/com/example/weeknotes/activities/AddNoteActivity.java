package com.example.weeknotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.weeknotes.MainViewModel;
import com.example.weeknotes.Note;
import com.example.weeknotes.R;
import com.example.weeknotes.databinding.ActivityAddNoteBinding;
import com.example.weeknotes.Note.*;


public class AddNoteActivity extends AppCompatActivity {

    ActivityAddNoteBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.button.setOnClickListener(view -> {
            String title = binding.editTextTitle.getText().toString().trim();
            String description = binding.editTextDescription.getText().toString().trim();
            DayOfWeek dayOfWeek = DayOfWeek.getDay(binding.spinnerDayOfWeek.getSelectedItem().toString());
            Priority priority = Priority.getPriority(((RadioButton)findViewById(binding.radioGroupPriority.getCheckedRadioButtonId())).getText().toString());

            //Кладем значения в бд
            if(!title.isEmpty() && !description.isEmpty()) {
                Note note = new Note(title,description,dayOfWeek,priority);
                viewModel.insertNote(note);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
            }
        });
    }
}