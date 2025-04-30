package com.example.mumbainosh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private ImageView semicircleView;
    private ImageView secondSemicircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views after setContentView
        semicircleView = findViewById(R.id.semicircleView);
        secondSemicircleView = findViewById(R.id.secondsemicircleView);

        // Start the animation as soon as the activity is created
        setupAnimation();
    }

    private void setupAnimation() {
        if (semicircleView != null && secondSemicircleView != null) {
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            int centerY = screenHeight / 4 - (semicircleView.getHeight() / 2);  // Adjusted position
            int finalYSecond = (int) (-screenHeight * 1.2); // Move 2 times the screen height upwards

            ValueAnimator animatorFirst = ValueAnimator.ofFloat(-semicircleView.getHeight(), centerY); // Move from top off-screen to center
            animatorFirst.setDuration(500);  // Faster animation duration
            animatorFirst.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                semicircleView.setTranslationY(animatedValue);
            });

            ValueAnimator animatorSecond = ValueAnimator.ofFloat(secondSemicircleView.getTranslationY(), finalYSecond);
            animatorSecond.setDuration(500);  // Duration for the second semicircle
            animatorSecond.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();
                secondSemicircleView.setTranslationY(animatedValue); // Apply animation for the second semicircle
            });

            // Use AnimatorSet to run both animations simultaneously
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorFirst, animatorSecond);

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    semicircleView.setImageResource(R.drawable.circle_shape); // Replace with your full circle drawable

                    // Set the designer image inside the circle
                    semicircleView.setImageResource(R.drawable.designer);  // Replace with the designer.png image


                    fadeInImage(semicircleView);

                    redirectToFirstScreen();
                }
            });

            animatorSet.start();
        }
    }

    private void fadeInImage(ImageView imageView) {
        AlphaAnimation fadeIn = new AlphaAnimation(0.3f, 1.0f); // Start invisible, end fully visible
        fadeIn.setDuration(700);  // 1-second fade-in duration
        fadeIn.setFillAfter(true); // Keep the final state after animation completes

        imageView.startAnimation(fadeIn);
    }

    private void redirectToFirstScreen() {
        // Delay for smooth transition
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, FirstScreen.class);
            startActivity(intent);
            finish();  // Optionally finish MainActivity so user can't go back to it
        }, 1000);  // 1 second delay after animations complete
    }
}
