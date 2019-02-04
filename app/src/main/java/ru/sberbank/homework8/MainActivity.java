package ru.sberbank.homework8;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ru.sberbank.homework8.db.DBManager;
import ru.sberbank.homework8.model.Note;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;


    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
        initRecyclerView();
        downloadNotes();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mMyAdapter = new MyAdapter(MainActivity.this, this::openNote, this::deleteNote);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void deleteNote(Note note) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                DBManager.getInstance(MainActivity.this).deleteNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                downloadNotes();
            }
        }.execute();
    }


    private void openNote(Note note) {
        Intent intent = CreateNoteActivity.newIntent(MainActivity.this);
        Bundle bundle = new Bundle();

        bundle.putString("text", note.getText());
        bundle.putString("title", note.getTitle());
        bundle.putInt("color", note.getColor());
        bundle.putInt("id", note.getId());
        bundle.putString("date", note.getCreationDate());

        intent.putExtra("note", bundle);
        startActivityForResult(intent, 2);
    }

    private void initListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this,"Button pressed",Toast.LENGTH_SHORT).show();
                Intent intent = CreateNoteActivity.newIntent(MainActivity.this);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.floatingActionButton), "", Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        switch (resultCode) {
            case 1:
                downloadNotes();
                snackbar.setText("Note added");
                snackbar.show();
                break;
            case 2:
                downloadNotes();
                snackbar.setText("Note updated");
                snackbar.show();
                break;
            case 3:
                snackbar.setText("Text size updated");
                snackbar.show();
                mMyAdapter.invalidateItems();
                break;
        }
    }


    private void initView() {
        mFloatingActionButton = findViewById(R.id.floatingActionButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu, menu);
        return true;
    }


    @SuppressLint("StaticFieldLeak")
    private void downloadNotes() {
        new AsyncTask<Void, Void, List<Note>>() {


            @Override
            protected List<Note> doInBackground(Void... voids) {
                return DBManager.getInstance(MainActivity.this).getNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                mMyAdapter.setNotes(notes);
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting_menu) {
            Intent intent = SettingActivity.newIntent(MainActivity.this);
            startActivityForResult(intent, 3);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
