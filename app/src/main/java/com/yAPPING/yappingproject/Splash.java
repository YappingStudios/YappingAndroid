package com.yAPPING.yappingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by srikar on 20/9/15.
 */
public class Splash extends Activity{
    ImageView splashImage;
    Animation zoomin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        splashImage= (ImageView) findViewById(R.id.splashimageView);
        zoomin= AnimationUtils.loadAnimation(this,R.anim.zoomin);
        splashImage.setAnimation(zoomin);
        splashImage.startAnimation(zoomin);

       /* splashImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent a= new Intent(Splash.this,MainActivity.class);
                startActivity(a);
                finish();
                return false;
            }
        });   */
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finishscreen();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 2000);

    }
    private void finishscreen() {
        this.finish();
    }
}