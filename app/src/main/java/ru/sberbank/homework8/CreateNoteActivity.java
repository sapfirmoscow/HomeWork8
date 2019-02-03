package ru.sberbank.homework8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import java.util.Objects;

import ru.sberbank.homework8.db.DBManager;
import ru.sberbank.homework8.model.Note;

public class CreateNoteActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;
    private TextInputEditText mTitle;
    private TextInputEditText mText;

    private ToggleButton mYellowButton;
    private ToggleButton mRedButton;
    private ToggleButton mGreenButton;
    private ToggleButton mBlueButton;

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
                uploadNote();

                finish();
            }
        });

        //если другие цвета выбраны то снимаем с них галку
        mYellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueButton.isChecked() || mRedButton.isChecked() || mGreenButton.isChecked()) {
                    mBlueButton.setChecked(false);
                    mRedButton.setChecked(false);
                    mGreenButton.setChecked(false);
                }

            }
        });


        mRedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueButton.isChecked() || mYellowButton.isChecked() || mGreenButton.isChecked()) {
                    mBlueButton.setChecked(false);
                    mYellowButton.setChecked(false);
                    mGreenButton.setChecked(false);
                }
            }
        });


        mGreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueButton.isChecked() || mYellowButton.isChecked() || mRedButton.isChecked()) {
                    mBlueButton.setChecked(false);
                    mYellowButton.setChecked(false);
                    mRedButton.setChecked(false);
                }
            }
        });

        mBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRedButton.isChecked() || mYellowButton.isChecked() || mGreenButton.isChecked()) {
                    mRedButton.setChecked(false);
                    mYellowButton.setChecked(false);
                    mGreenButton.setChecked(false);
                }
            }
        });

    }

    private void initViews() {
        mFloatingActionButton = findViewById(R.id.save_floatingActionButton);
        mTitle = findViewById(R.id.title_editText);
        mText = findViewById(R.id.text_editText);

        mYellowButton = findViewById(R.id.button_yellow);
        mRedButton = findViewById(R.id.button_red);
        mBlueButton = findViewById(R.id.button_blue);
        mGreenButton = findViewById(R.id.button_green);
    }


    private Note formNote() {
        Note tempNote = new Note();
        tempNote.setText(Objects.requireNonNull(mText.getText()).toString());
        tempNote.setTitle(Objects.requireNonNull(mTitle.getText()).toString());
        int color = getCheckedColor();
        color = ColorUtils.setAlphaComponent(color, 70);
        tempNote.setColor(color);


        return tempNote;
    }

    private int getCheckedColor() {
        if (mYellowButton.isChecked()) return getResources().getColor(R.color.yellow_light);
        else if (mRedButton.isChecked()) return getResources().getColor(R.color.red_light);
        else if (mGreenButton.isChecked()) return getResources().getColor(R.color.green_light);
        else if (mBlueButton.isChecked()) return getResources().getColor(R.color.blue_light);
        else return getResources().getColor(R.color.colorWhite);
    }

    private void uploadNote() {
        final Note tempNote = formNote();

        new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager.getInstance(CreateNoteActivity.this).addNote(tempNote);

            }
        }).start();
        setResult(1);
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
