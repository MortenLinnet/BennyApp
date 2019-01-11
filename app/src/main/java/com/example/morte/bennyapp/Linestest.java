package com.example.morte.bennyapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class Linestest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linestest);


        VideoView Ojneviewlol = findViewById(R.id.videoViewline);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ikkeskaleretopline);


        Ojneviewlol.setVideoURI(uri);
        Ojneviewlol.start();


    }
}
