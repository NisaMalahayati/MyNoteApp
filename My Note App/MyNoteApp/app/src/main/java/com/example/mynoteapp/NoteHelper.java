package com.example.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public NoteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE my_table (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Menghapus tabel lama jika ada perubahan
        db.execSQL("DROP TABLE IF EXISTS my_table");
        onCreate(db);
    }

    // Menampilkan semua data
    public Cursor showData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM my_table", null);
    }

    // Menambahkan data baru
    public void insertData(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);

        long result = db.insert("my_table", null, values);
        if (result == -1) {
            Log.e("NoteHelper", "Data insertion failed");
        } else {
            Log.d("NoteHelper", "Data inserted successfully");
        }

        db.close();
    }

    // Memperbarui data
    public boolean updateNote(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);

        int rowsUpdated = db.update("my_table", values, "id=?", new String[]{String.valueOf(id)});
        db.close();  // Menutup koneksi setelah operasi selesai

        return rowsUpdated > 0;
    }

    // Menghapus data
    public void deleteData(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("my_table", "id = ?", new String[]{String.valueOf(noteId)});
        db.close();
    }
}
