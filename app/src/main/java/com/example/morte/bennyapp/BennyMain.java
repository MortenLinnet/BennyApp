package com.example.morte.bennyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BennyMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benny_main);

        Toast.makeText(this, "olo", Toast.LENGTH_SHORT).show();
    }
}
