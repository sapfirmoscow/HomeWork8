package ru.sberbank.homework8;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
    }

    private void initListeners() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this,"Button pressed",Toast.LENGTH_SHORT).show();
                Intent intent = CreateNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
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
