package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;
import android.os.Handler;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private int numOfButtons;
    private MemoryButton[] memoryButtons;
    private int[] buttonsLocations;
    private int[] distinctButtons;
    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;
    private boolean isBusy = false;
    private long startTime = 0;
    private int triesCounter = 0;
    private LeaderBoardsHandler leaderBoardsHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        View someView = findViewById(R.id.gameActivity);
        View root = someView.getRootView();
        root.setBackgroundColor(Color.rgb(16,127,196));

        leaderBoardsHandler = new LeaderBoardsHandler(this);

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        this.numOfButtons = gridLayout.getColumnCount() * gridLayout.getRowCount();

        memoryButtons = new MemoryButton[numOfButtons];
        distinctButtons = new int[numOfButtons / 2];

        distinctButtons[0] = R.drawable.b1;
        distinctButtons[1] = R.drawable.b2;
        distinctButtons[2] = R.drawable.b3;
        distinctButtons[3] = R.drawable.b4;
        distinctButtons[4] = R.drawable.b5;
        distinctButtons[5] = R.drawable.b6;
        distinctButtons[6] = R.drawable.b7;
        distinctButtons[7] = R.drawable.b8;

        buttonsLocations = new int[numOfButtons];

        this.shuffleButtons();

        for (int i=0; i < gridLayout.getRowCount(); i++) {
            for (int j =0; j < gridLayout.getColumnCount(); j++) {
                int idx = i * gridLayout.getColumnCount() + j;
                MemoryButton tmpBtn = new MemoryButton(this, i, j, distinctButtons[buttonsLocations[idx]]);
                tmpBtn.setId(View.generateViewId());
                tmpBtn.setOnClickListener(this);
                memoryButtons[idx] = tmpBtn;
                gridLayout.addView(tmpBtn);
                gridLayout.setPadding(20,20,20,20);
                gridLayout.setBackgroundColor(Color.BLACK);
            }
        }

        findViewById(R.id.backToMain).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(GameActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

        this.startTime = System.currentTimeMillis();
    }

    public void shuffleButtons() {
        Random rand = new Random();
        for (int i = 0; i < this.numOfButtons; i++) {
            buttonsLocations[i] = i % this.numOfButtons / 2;
        }

        for (int i = 0; i < this.numOfButtons; i++) {
            int curr = buttonsLocations[i];
            int replace = rand.nextInt(numOfButtons);

            buttonsLocations[i] = buttonsLocations[replace];
            buttonsLocations[replace] = curr;
        }
    }

    @Override
    public void onClick(View view) {
        if (isBusy) return;

        MemoryButton button = (MemoryButton) view;
        if (button.isMatched) return;

        if(selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }

        if (selectedButton1.getId() == button.getId()) return;

        // Match
        if (selectedButton1.getImageId() == button.getImageId()) {
            this.triesCounter++;
            button.flip();

            button.setMatched(true);
            selectedButton1.setMatched(true);

            selectedButton1.setEnabled(false);
            button.setEnabled(false);

            selectedButton1 = null;

            boolean isGameFinished = true;
            for(int i=0; i < memoryButtons.length; i++) {
                if (!memoryButtons[i].isMatched) {
                    isGameFinished = false;
                    break;
                }
            }

            if (isGameFinished) {
                long endTime = System.currentTimeMillis();
                double duration = (endTime - this.startTime) / 1000.0;
                this.leaderBoardsHandler.saveToLeaderBoardsIfNeeded(this.triesCounter);

                Intent intent = new Intent(GameActivity.this, ResultsActivity.class);
                intent.putExtra("duration", String.valueOf(duration));
                intent.putExtra("triesCounter", String.valueOf(this.triesCounter));
                startActivity(intent);
            }
            return;
        } else  {
            this.triesCounter++;
            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();

                    selectedButton2 = null;
                    selectedButton1 = null;

                    isBusy = false;
                }
            }, 500);
        }
    }
}