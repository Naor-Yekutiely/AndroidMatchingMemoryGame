package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        View someView = findViewById(R.id.resultsActivity);
        View root = someView.getRootView();
        root.setBackgroundColor(Color.rgb(16,127,196));

        // Retrieve the data from the intent using the keys
        Intent intent = getIntent();
        String durationString = intent.getStringExtra("duration");
        double durationDouble = Double.parseDouble(durationString); // convert string to double
        double roundedDuration = Math.round(durationDouble * 100.0) / 100.0; // round to 2 decimal places
        durationString = String.format("%.2f", roundedDuration); // convert rounded value back to string

        String triesCounter = intent.getStringExtra("triesCounter");

        TextView durationTextView = (TextView)findViewById(R.id.durationTextView);
        TextView triesCountTextView = (TextView)findViewById(R.id.triesCountTextView);

        durationTextView.append("Game Duration - " + durationString);
        triesCountTextView.append("Number Of Tries - " + triesCounter);

        findViewById(R.id.Restart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent gameActivity = new Intent(ResultsActivity.this, GameActivity.class);
                startActivity(gameActivity);
            }
        });

        findViewById(R.id.leaderBoards).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent leaderBoardActivity = new Intent(ResultsActivity.this, LeaderBoardActivity.class);
                startActivity(leaderBoardActivity);
            }
        });

        findViewById(R.id.backToMain).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}