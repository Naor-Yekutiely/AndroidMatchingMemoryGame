package com.example.matchinggamememory;

import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoardsHandler {
    // Change this file name to start a new fresh player game.
    private final String leaderBoardsData_FileName = "gameDB7.txt";
    private final int leaderBoardsMaxRows = 10;
    Context context;
    public LeaderBoardsHandler(Context context) {
        this.context = context;
    }
    public boolean isHaveSavedData() {
        return this.loadAllGamesAsString() == null;
    }
    public void saveToLeaderBoardsIfNeeded(int currTriesCounter) {
        String allGames = this.loadAllGamesAsString();
        if (allGames == null) {
            this.appendSingleGameToLeaderBoard(currTriesCounter);
            Toast.makeText(context.getApplicationContext(), "congrats on finishing your first game! you entered the leaderboard!", Toast.LENGTH_LONG).show();
            return;
        }

        String[] allGamesArr = allGames.split("\\|");

        ArrayList<Integer> triesArrList = new ArrayList<Integer>();
        for (int i=0; i < allGamesArr.length; i++) {
            int currTries = Integer.valueOf(allGamesArr[i]);
            triesArrList.add(currTries);
        }

        if (triesArrList.contains(currTriesCounter)) {
            Toast.makeText(context.getApplicationContext(), "You came close.." + currTriesCounter + " tries is already in the leaderboard. Keep it going!", Toast.LENGTH_LONG).show();
            return;
        }

        if (allGamesArr.length < this.leaderBoardsMaxRows) {
            this.appendSingleGameToLeaderBoard(currTriesCounter);
            Toast.makeText(context.getApplicationContext(), "congrats! you entered the leaderboard!", Toast.LENGTH_LONG).show();
            return;
        }

        int maxIndex = triesArrList.indexOf(Collections.max(triesArrList));
        if (currTriesCounter < triesArrList.get(maxIndex)) {
            triesArrList.set(maxIndex, currTriesCounter);
            this.overwriteMultipleGameToLeaderBoard(triesArrList);
            Toast.makeText(context.getApplicationContext(), "congrats! you entered the leaderboard!", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<Integer> getAllGamesAsArrayList() {
        String allGames = this.loadAllGamesAsString();
        if (allGames == null) {
            return null;
        }

        String[] allGamesArr = allGames.split("\\|");

        ArrayList<Integer> triesArrList = new ArrayList<Integer>();
        for (int i=0; i < allGamesArr.length; i++) {
            int currTries = Integer.valueOf(allGamesArr[i]);
            triesArrList.add(currTries);
        }

        Collections.sort(triesArrList);
        return triesArrList;
    }

    private void appendSingleGameToLeaderBoard(int triesCounter) {
        // Writing data to internal storage
        String data = triesCounter + "|";
        try {
            FileOutputStream fos = this.context.openFileOutput(this.leaderBoardsData_FileName, Context.MODE_APPEND);
            fos.write(data.getBytes());
            fos.write(System.getProperty("line.separator").getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void overwriteMultipleGameToLeaderBoard(ArrayList<Integer> triesArrList) {
        // Writing data to internal storage
        try {
            FileOutputStream fos = this.context.openFileOutput(this.leaderBoardsData_FileName, Context.MODE_PRIVATE);
            for (int i=0;i< triesArrList.size(); i++) {
                String data = triesArrList.get(i) + "|";
                fos.write(data.getBytes());
                fos.write(System.getProperty("line.separator").getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadAllGamesAsString() {
        if (!isFileExists(this.leaderBoardsData_FileName)) return null;
        // Reading data from internal storage
        try {
            isFileExists(this.leaderBoardsData_FileName);
            FileInputStream in = context.openFileInput(this.leaderBoardsData_FileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStreamReader.close();
            in.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    private boolean isFileExists(String filename) {
        File file = this.context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}
