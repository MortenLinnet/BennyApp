package com.example.morte.bennyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;

public class SizeOfEyes extends AppCompatActivity {

    private static SeekBar seek_bar;
    private ImageView Eye_view;
    Integer SeekBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_of_eyes);


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
    }*/

    public void seekbar(){
        seek_bar = findViewById(R.id.seekBar);
        Eye_view = findViewById(R.id.SizeofEyesView);

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                       /* SeekBarValue = progress;

                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  (Eye_view.getLayoutParams().height * (progress/100)), getResources().getDisplayMetrics());
                        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  (Eye_view.getLayoutParams().width * (progress/100)), getResources().getDisplayMetrics());


                        Eye_view.getLayoutParams().height = height;
                        Eye_view.getLayoutParams().width = width;
                        Eye_view.requestLayout();
*/
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                        Eye_view.getLayoutParams().height = height;
                        Eye_view.getLayoutParams().width = width;
                        Eye_view.requestLayout();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {


                    }

                    });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }
}
