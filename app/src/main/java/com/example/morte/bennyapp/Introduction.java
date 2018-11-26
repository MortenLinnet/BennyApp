package com.example.morte.bennyapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Introduction extends AppCompatActivity {
ImageView SwipeView;
int CurrentPlace;
Button ButtonLeft;
Button ButtonMid;
Button ButtonRight;
Button GoplayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        SwipeView = findViewById(R.id.SwipeView);

        ButtonLeft = findViewById(R.id.try1);
        ButtonMid = findViewById(R.id.try2);
        ButtonRight = findViewById(R.id.try3);
         GoplayButton = findViewById(R.id.GoPlayButton);
        GoplayButton.setEnabled(false);


        CurrentPlace = 1;
HighLightButtons();


        SwipeView.setOnTouchListener(new OnSwipeListener(this){

                                         @Override
                                         public void onSwipeRight() {

                                             if (CurrentPlace> 1 ) {
                                                 CurrentPlace--;
                                             }
                                             HighLightButtons();
                                          //   Toast.makeText(Introduction.this, "nr er"+ CurrentPlace, Toast.LENGTH_SHORT).show();

                                             super.onSwipeRight();
                                         }

                                         @Override
                                         public void onSwipeLeft() {

                                             if (CurrentPlace < 3) {
                                                 CurrentPlace++;
                                             }
                                             HighLightButtons();
                                         //    Toast.makeText(Introduction.this, "nr er"+ CurrentPlace, Toast.LENGTH_SHORT).show();


                                             super.onSwipeLeft();
                                         }
          }
        );
    }

    public void HighLightButtons(){

        if (CurrentPlace == 1){
         ButtonLeft.setBackgroundColor(Color.BLACK);
         ButtonMid.setBackgroundColor(Color.BLUE);
         ButtonRight.setBackgroundColor(Color.BLUE);


        }
        if (CurrentPlace == 2){
            ButtonLeft.setBackgroundColor(Color.BLUE);
            ButtonMid.setBackgroundColor(Color.BLACK);
            ButtonRight.setBackgroundColor(Color.BLUE);
            RemoveButton();


        }
        if (CurrentPlace == 3){
            ButtonLeft.setBackgroundColor(Color.BLUE);
            ButtonMid.setBackgroundColor(Color.BLUE);
            ButtonRight.setBackgroundColor(Color.BLACK);
            EnhanceGoPlayButton();

        }



    }


public void EnhanceGoPlayButton(){


    if (!GoplayButton.isEnabled()) {
        GoplayButton.setEnabled(true);

        ObjectAnimator GoplayButtonFadeOut = ObjectAnimator.ofFloat(GoplayButton, "alpha", 0f, .1f);
        GoplayButtonFadeOut.setDuration(100);
        ObjectAnimator GoplaybuttonFadeIn = ObjectAnimator.ofFloat(GoplayButton, "alpha", .1f, 1f);
        GoplaybuttonFadeIn.setDuration(450);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(GoplayButtonFadeOut).before(GoplaybuttonFadeIn);
        animatorSet.start();
    }
    }


public void RemoveButton (){


    if (GoplayButton.isEnabled()) {
        GoplayButton.setEnabled(false);
        ObjectAnimator GoplayButtonFadeOut = ObjectAnimator.ofFloat(GoplayButton, "alpha", 1f, 0f);
        GoplayButtonFadeOut.setDuration(550);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(GoplayButtonFadeOut);

        animatorSet.start();

    }


}

    public void MovingOnUpMovingOndDown(View view) {
        Toast.makeText(this, "Magiccarp used splash, nothing happened", Toast.LENGTH_SHORT).show();
    }
}




