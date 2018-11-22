package com.example.morte.bennyapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpActivity extends Activity {

    Button ClosePopUp;
    String WhatBenny;
    TextView TextOmkringHvemBennyEr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        TextOmkringHvemBennyEr = findViewById(R.id.PopUpTextView);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ClosePopUp = findViewById(R.id.PopUpButton);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String WhatBennyIdentifier = bundle.getString("StringBenny");
            WhatBenny = WhatBennyIdentifier;


        } catch (NullPointerException e) {
            Toast.makeText(this, "Nullpointer catched", Toast.LENGTH_SHORT).show();
        }

TextOmkringHvemBennyEr.setText(WhatBenny);

    }

    public void ClosePopUp(View view) {
        finish();

    }
}
