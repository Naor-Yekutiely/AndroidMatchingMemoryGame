package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    private LeaderBoardsHandler leaderBoardsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        leaderBoardsHandler = new LeaderBoardsHandler(this);
        View someView = findViewById(R.id.LeaderBoardGridLayout);
        View root = someView.getRootView();
        root.setBackgroundColor(Color.rgb(16,127,196));
        GridLayout gridLayout = (GridLayout)findViewById(R.id.LeaderBoardGridLayout);
        TextView leaderBoardTextView = (TextView)findViewById(R.id.leaderBoardTextView);
        TextView triesTextView = (TextView)findViewById(R.id.TriesTextView);
        this.populateLeaderBoardGames(gridLayout, leaderBoardTextView, triesTextView);
    }
    private void populateLeaderBoardGames(GridLayout gridLayout, TextView leaderBoardTextView, TextView triesTextView) {
        ArrayList<Integer> triesArrList = leaderBoardsHandler.getAllGamesAsArrayList();
        if (triesArrList == null) {
            leaderBoardTextView.append("No games played yet!");
            return;
        }

        leaderBoardTextView.append("Leaderboard");
        triesTextView.append("Number Of Tries:");
        for (int i=0; i < triesArrList.size(); i++) {
            LeaderBoardRow leaderBoardRow = new LeaderBoardRow(this, i, triesArrList.get(i).toString(), false);
            leaderBoardRow.setId(View.generateViewId());
            gridLayout.addView(leaderBoardRow);
        }

        findViewById(R.id.backToMain).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(LeaderBoardActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

    }
}