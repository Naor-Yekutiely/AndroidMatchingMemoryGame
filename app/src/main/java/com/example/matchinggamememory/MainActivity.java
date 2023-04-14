package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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

        findViewById(R.id.leaderBoard).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gameActivity = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(gameActivity);
            }
        });

        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent aboutActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutActivity);
            }
        });
    }
}