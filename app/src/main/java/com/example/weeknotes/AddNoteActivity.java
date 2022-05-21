package com.example.weeknotes;


import static com.example.weeknotes.NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK;
import static com.example.weeknotes.NotesContract.NotesEntry.COLUMN_DESCRIPTION;
import static com.example.weeknotes.NotesContract.NotesEntry.COLUMN_PRIORITY;
import static com.example.weeknotes.NotesContract.NotesEntry.COLUMN_TITLE;
import static com.example.weeknotes.NotesContract.NotesEntry.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.weeknotes.databinding.ActivityAddNoteBinding;
import com.example.weeknotes.Note.*;


public class AddNoteActivity extends AppCompatActivity {

    ActivityAddNoteBinding binding;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();

        binding.button.setOnClickListener(view -> {
            String title = binding.editTextTitle.getText().toString().trim();
            String description = binding.editTextDescription.getText().toString().trim();
            DayOfWeek dayOfWeek = DayOfWeek.getDay(binding.spinnerDayOfWeek.getSelectedItem().toString());
            Priority priority = Priority.getPriority(((RadioButton)findViewById(binding.radioGroupPriority.getCheckedRadioButtonId())).getText().toString());

            //Кладем значения в бд
            if(!title.isEmpty() && !description.isEmpty()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_TITLE, title);
                contentValues.put(COLUMN_DESCRIPTION, description);
                contentValues.put(COLUMN_DAY_OF_WEEK, dayOfWeek.getTitle());
                contentValues.put(COLUMN_PRIORITY, priority.getTitle());
                database.insert(TABLE_NAME, null, contentValues);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
            }

        });
    }
}