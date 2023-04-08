package com.example.matchinggamememory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatDrawableManager;

public class MemoryButton extends androidx.appcompat.widget.AppCompatButton {

    protected int row;
    protected int col;
    protected int frontImageId;
    protected boolean isFlipped = false;
    protected boolean isMatched = false;
    protected Drawable frontImage;
    protected Drawable backImage;

    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int row, int col, int frontImageId) {
        super(context);
        this.row = row;
        this.col = col;
        this.frontImageId = frontImageId;

        this.frontImage = AppCompatDrawableManager.get().getDrawable(context, frontImageId);
        this.backImage = AppCompatDrawableManager.get().getDrawable(context, R.drawable.back);

        setBackground(backImage);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(this.row), GridLayout.spec(this.col));

        // 50dp for width, height
        layoutParams.width = (int)getResources().getDisplayMetrics().density * 115;
        layoutParams.height = (int)getResources().getDisplayMetrics().density * 115;

        setLayoutParams(layoutParams);
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getImageId() {
        return frontImageId;
    }

    public void flip() {
        if (isMatched) return;

        if (isFlipped) {
            setBackground(backImage);
            isFlipped = false;
        } else {
            setBackground(frontImage);
            isFlipped = true;
        }
    }
}
