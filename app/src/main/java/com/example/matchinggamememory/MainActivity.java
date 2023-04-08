package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startGame;
    private Button results;
    private Button about;
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.startGame).setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                  Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                  startActivity(gameActivity);
              }
        });
    }
}