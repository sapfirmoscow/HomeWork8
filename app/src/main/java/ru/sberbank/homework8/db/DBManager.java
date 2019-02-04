package ru.sberbank.homework8.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.homework8.model.Note;

public class DBManager {
    private static DBManager sDbManager;
    private DBHelper mDbHelper;

    public DBManager(Context context) {
        mDbHelper = new DBHelper(context);
    }

    public static DBManager getInstance(Context context) {
        if (sDbManager != null) return sDbManager;
        sDbManager = new DBManager(context);
        return sDbManager;
    }

    public void addNote(Note note) {
        SQLiteDatabase db = null;

        try {
            db = mDbHelper.getWritableDatabase();
            ContentValues contentValues = getNoteContentValues(note);
            //Вставляем нашу записку в базу данных
            db.insert(DBHelper.TABLE_NAME, null, contentValues);
            Log.d("TEst", "id is " + contentValues.get(DBHelper.COLUMN_ID));
        } catch (SQLiteException e) {
            Log.v("SQLiteException:addNote", e.getMessage());
        } finally {
            if (db != null) db.close();
        }
    }


    public List<Note> getNotes() {
        SQLiteDatabase db = null;
        List<Note> notes = null;
        try {
            db = mDbHelper.getReadableDatabase();
            db.beginTransaction();
            Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
            notes = pareseCursorToNotes(cursor);
            cursor.close();
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            Log.v("SQLiteExcp:getNotes", e.getMessage());
        } finally {
            if (db != null) {
                if (db.inTransaction()) db.endTransaction();
                db.close();
            }
        }
        return notes;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = null;

        try {
            db = mDbHelper.getWritableDatabase();
            ContentValues noteContentValues = getNoteContentValues(note);
            int upd = db.update(DBHelper.TABLE_NAME, noteContentValues, DBHelper.COLUMN_ID + "=" + note.getId(), null);
            Log.d("DEB", upd + " ");
        } catch (SQLiteException e) {
            Log.v("SQLiteException:updNote", e.getMessage());
        } finally {
            if (db != null) {
                if (db.inTransaction()) db.endTransaction();
                db.close();
            }

        }
    }


    public void deleteNote(Note note) {
        SQLiteDatabase db = null;
        try {
            db = mDbHelper.getWritableDatabase();
            db.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + "=" + note.getId(), null);

        } catch (SQLiteException e) {
            Log.v("SQLiteException:delNote", e.getMessage());
        } finally {
            if (db != null) {
                if (db.inTransaction()) db.endTransaction();
                db.close();
            }
        }
    }

    private List<Note> pareseCursorToNotes(Cursor cursor) {
        List<Note> temp = new ArrayList<>();
        while (cursor.moveToNext()) {
            temp.add(parseCursorToNote(cursor));
        }
        return temp;
    }

    private Note parseCursorToNote(Cursor cursor) {
        return new Note(
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TEXT)),
                cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CREATED)),
                cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_COLOR)),
                cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID))
        );
    }


    private ContentValues getNoteContentValues(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_TITLE, note.getTitle());
        contentValues.put(DBHelper.COLUMN_TEXT, note.getText());
        contentValues.put(DBHelper.COLUMN_CREATED, note.getCreationDate());
        contentValues.put(DBHelper.COLUMN_ID, note.getId());
        contentValues.put(DBHelper.COLUMN_COLOR, note.getColor());
        return contentValues;
    }


}
