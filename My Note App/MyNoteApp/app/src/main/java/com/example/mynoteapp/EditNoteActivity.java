package com.example.mynoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private NoteHelper noteHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        noteHelper = new NoteHelper(this);

        // Get the note data passed from MainActivity
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);  // Get the note ID
        String title = intent.getStringExtra("noteTitle");  // Get the note Title
        String description = intent.getStringExtra("noteDescription");  // Get the note Description

        // Set the existing data into the EditText fields
        editTitle.setText(title);
        editDescription.setText(description);

        // Save changes when Save button is clicked
        findViewById(R.id.saveButton).setOnClickListener(v -> saveChanges());
    }

    private void saveChanges() {
        String updatedTitle = editTitle.getText().toString();
        String updatedDescription = editDescription.getText().toString();

        // Update the note in the database
        if (noteHelper.updateNote(noteId, updatedTitle, updatedDescription)) {
            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity and return to MainActivity
        } else {
            Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show();
        }
    }
}
