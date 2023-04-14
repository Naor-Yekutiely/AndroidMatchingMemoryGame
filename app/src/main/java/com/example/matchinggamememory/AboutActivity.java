package com.example.matchinggamememory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        View someView = findViewById(R.id.aboutActivity);
        View root = someView.getRootView();
        root.setBackgroundColor(Color.rgb(16,127,196));

        findViewById(R.id.backToMain).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}