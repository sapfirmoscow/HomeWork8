package ru.sberbank.homework8.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.List;

import ru.sberbank.homework8.model.Note;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "db_notes";
    public static final String TABLE_NAME = "note";
    private static final int VERSION_DB = 1;

    private DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        this(context, DB_NAME, null, VERSION_DB);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmptyTables(db);
    }

    private void createEmptyTables(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME;
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTables(db);
        onCreate(db);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public void addNote(Note note) {

    }

    public List<Note> getNotes() {
        return null;
    }

    public Note getNote(int id) {
        return null;
    }

    public void deleteNote(Note note) {

    }

    public void deleteAllNotes() {

    }

    public void updateNote() {

    }
}
