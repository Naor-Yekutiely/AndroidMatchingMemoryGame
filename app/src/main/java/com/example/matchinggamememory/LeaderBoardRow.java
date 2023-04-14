package com.example.matchinggamememory;

import android.content.Context;
import android.widget.GridLayout;
import androidx.annotation.NonNull;
public class LeaderBoardRow extends androidx.appcompat.widget.AppCompatTextView {

    private int row;
    private final int col = 0;

    public LeaderBoardRow(@NonNull Context context, int row, String rowText, boolean isText) {
        super(context);
        this.row = row;

        setText(rowText);
        setTextSize(30);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(this.row), GridLayout.spec(this.col));

        setLayoutParams(layoutParams);
    }
}
