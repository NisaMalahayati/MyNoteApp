package com.example.mynoteapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NoteHelper noteHelper;
    FloatingActionButton floatingId;
    RecyclerView recyclerView;
    ArrayList<NoteModel> arrayList = new ArrayList<>();
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingId = findViewById(R.id.floatingId);
        recyclerView = findViewById(R.id.recyclerView);
        noteHelper = new NoteHelper(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadNotes();

        // FloatingActionButton untuk menambah note baru
        floatingId.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity2.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        Cursor cursor = noteHelper.showData();
        arrayList.clear();

        while (cursor.moveToNext()) {
            arrayList.add(new NoteModel(cursor.getString(1), cursor.getString(2), cursor.getInt(0)));
        }

        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(this, arrayList);
            recyclerView.setAdapter(noteAdapter);
        } else {
            noteAdapter.updateNotes(arrayList);
        }
    }

    public void deleteNote(int id) {
        noteHelper.deleteData(id); // Pastikan deleteNote digunakan, bukan deleteData
        loadNotes();
        Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
    }

    public void editNote(NoteModel note) {
        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent.putExtra("noteId", note.getId());  // Pass the note's ID
        intent.putExtra("noteTitle", note.getTitle());  // Pass the note's Title
        intent.putExtra("noteDescription", note.getDescription());  // Pass the note's Description
        startActivity(intent);
    }

}
