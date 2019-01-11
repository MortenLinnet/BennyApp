package com.example.morte.bennyapp;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SizeOfEyes extends AppCompatActivity {

    private static SeekBar seek_bar;
    private static TextView textView;
    private ImageView Eye_view;
    private static Integer SeekBarValue;
    private int lol;
    public static final String BennyPreferences = "BennyPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_of_eyes);

        SharedPreferences prefs = getSharedPreferences(SelectLanguage.BennyPreferences, MODE_PRIVATE);
        Integer eyeValue = prefs.getInt("SeekBarValue", 0);
        SeekBarValue = eyeValue;

        seekbar();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
    }*/

    public void seekbar(){
        seek_bar = findViewById(R.id.seekBar);
        Eye_view = findViewById(R.id.SizeofEyesView);
        textView = findViewById(R.id.Sizeofeyestextview);
        final int WidthOfWindow = Eye_view.getLayoutParams().width;
        final int HeightOfWindow = Eye_view.getLayoutParams().height;

        textView.setText("Covered : " + seek_bar.getProgress() + " / "+ seek_bar.getMax());

        seek_bar.setOnSeekBarChangeListener(
        new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekBarValue = progress;
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  (Eye_view.getLayoutParams().height * (SeekBarValue/100)), getResources().getDisplayMetrics());
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  (Eye_view.getLayoutParams().width * (SeekBarValue/100)), getResources().getDisplayMetrics());


                Eye_view.getLayoutParams().height = (HeightOfWindow * (SeekBarValue * (-30)));
                Eye_view.getLayoutParams().width =  (WidthOfWindow * (SeekBarValue * (-30)));
                Eye_view.requestLayout();
                textView.setText("Covered : " + SeekBarValue + " / "+ seek_bar.getMax());
                saveSharedPreference();
                Toast.makeText(SizeOfEyes.this, "værdi er" + lol, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Covered : " + SeekBarValue + " / "+ seek_bar.getMax());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    public void saveSharedPreference () {
        SharedPreferences.Editor editor = getSharedPreferences(SelectLanguage.BennyPreferences, MODE_PRIVATE).edit();
        editor.putInt("SeekBarValue", SeekBarValue);
        editor.apply();
    }
}
