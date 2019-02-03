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
        mMyAdapter = new MyAdapter();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);
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
        // super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                downloadNotes();
                Snackbar.make(findViewById(R.id.floatingActionButton), "Note added", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                downloadNotes();
        }
    }




    private void initView() {
        mFloatingActionButton = findViewById(R.id.floatingActionButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting_menu,menu);
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
        if(item.getItemId() == R.id.setting_menu){
           // Toast.makeText(MainActivity.this,"Setting has clicked",Toast.LENGTH_SHORT).show();
            Intent intent = SettingActivity.newIntent(MainActivity.this);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
