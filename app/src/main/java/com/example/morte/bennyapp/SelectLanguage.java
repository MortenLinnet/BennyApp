package com.example.morte.bennyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SelectLanguage extends AppCompatActivity {

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
    }

    public void HighLightMarkedCharecter() {
        Button button1 = findViewById(R.id.buttonselection1);
        Button button2 = findViewById(R.id.buttonselection2);
        Button button3 = findViewById(R.id.buttonselection3);
        switch (id) {
            case 1:
                button1.setBackgroundColor(this.getResources().getColor(R.color.ButtonSelecctionColor));
                button2.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button3.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                break;

            case 2:
                button1.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button2.setBackgroundColor(this.getResources().getColor(R.color.ButtonSelecctionColor));
                button3.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                break;

            case 3:
                button1.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button2.setBackgroundColor(this.getResources().getColor(R.color.ButtonNOTSelecctionColor));
                button3.setBackgroundColor(this.getResources().getColor(R.color.ButtonSelecctionColor));
                break;
        }


    }
}
