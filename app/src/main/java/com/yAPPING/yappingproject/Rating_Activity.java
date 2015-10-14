package com.yAPPING.yappingproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;

/**
 * Created by srikar on 6/9/15.
 */
public class Rating_Activity extends Activity {

    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratings_screen);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int numberofstarts = ratingBar.getNumStars();
//        user.increment("rating", numberofstarts);
//        user.saveInBackground;

    }
}
