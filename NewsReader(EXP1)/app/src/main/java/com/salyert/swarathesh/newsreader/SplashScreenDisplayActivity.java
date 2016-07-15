package com.salyert.swarathesh.newsreader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

public class SplashScreenDisplayActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;
    private RelativeLayout MainLayout;
    private AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_display);
        MainLayout = (RelativeLayout) findViewById(R.id.layout);
        MainLayout.setBackgroundResource(R.drawable.splash_bg);
        animationDrawable = (AnimationDrawable) MainLayout.getBackground();
        animationDrawable.start();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                startActivity(new Intent(SplashScreenDisplayActivity.this,ScrollingActivity.class));

                SplashScreenDisplayActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
