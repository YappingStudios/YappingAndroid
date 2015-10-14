package com.yAPPING.yappingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

        splashImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent a= new Intent(Splash.this,MainActivity.class);
                startActivity(a);
                return false;
            }
        });

    }
}
