package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import java.util.Random;
//import java.util.logging.Handler;
import android.os.Handler;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private int numOfButtons;
    private MemoryButton[] memoryButtons;
    private int[] buttonsLocations;
    private int[] distinctButtons;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
            }
        }
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
                // TODO: Save the number of tries and time for the current game in the phone internal storage.
                // Use this data in a new activity for the leaderboards and display the top X best results.
                // Add another about and contact activity
                Toast toast = Toast.makeText(this, "Game Finish!", Toast.LENGTH_LONG);
                toast.show();
                Intent mainActivity = new Intent(GameActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
            return;
        } else  {
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