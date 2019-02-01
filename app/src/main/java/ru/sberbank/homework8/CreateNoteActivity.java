package ru.sberbank.homework8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Objects;

import ru.sberbank.homework8.model.Note;

public class CreateNoteActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;
    private TextInputEditText mTitle;
    private TextInputEditText mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initViews();
        initListeners();


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Saved", Snackbar.LENGTH_SHORT);
                formNote();
            }
        });
    }

    private void initViews() {
        mFloatingActionButton = findViewById(R.id.save_floatingActionButton);
        mTitle = findViewById(R.id.title_editText);
        mText = findViewById(R.id.text_editText);
    }

    private Note formNote() {
        Note tempNote = new Note();
        tempNote.setText(Objects.requireNonNull(mText.getText()).toString());
        tempNote.setTitle(Objects.requireNonNull(mTitle.getText()).toString());

        return tempNote;
    }

    private void uploadNote() {
        Note tempNote = formNote();

    }

    public static final Intent newIntent(Context context){
        return new Intent(context,CreateNoteActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
