package com.yAPPING.yappingproject;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends FragmentActivity {
ArrayList updatedInterests =new ArrayList();
    ParseUser currentUser = ParseUser.getCurrentUser();
    Dialog_interests dialog_interests = new Dialog_interests(updatedInterests);
    //TextView nameTextView = (TextView) findViewById(R.id.Name);
    //TextView emailTextView = (TextView) findViewById(R.id.EmailId);
    TextView name;
    TextView emailid;

    TextView edXPVal;
    TextView foodXPVal;
    TextView travelXPVal;
    TextView lifestyleXPVal;
    TextView techXPVal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);
        ParseUser currentUser = ParseUser.getCurrentUser();
        name = (TextView) findViewById(R.id.namefromparsetextview);
        emailid = (TextView) findViewById(R.id.emailfromparsetextview);
        edXPVal= (TextView) findViewById(R.id.eduXPfromParse);
        foodXPVal = (TextView) findViewById(R.id.foodXPfromParse);
        travelXPVal = (TextView) findViewById(R.id.travelXPfromParse);
        lifestyleXPVal = (TextView) findViewById(R.id.lifestyleXPfromParse);
        techXPVal = (TextView) findViewById(R.id.techXPfromParse);

        if (currentUser != null) {
            // do stuff with the user
            String name1 =  currentUser.getUsername();
            name.setText(name1);
            String email1 = currentUser.getEmail();
            emailid.setText(email1);

            List<Integer> preferenceNumbers=currentUser.getList("preferences");
           List<String> preferenceStrings = new ArrayList<String>();
            if(preferenceNumbers!=null){
                for(int counter=0;counter<preferenceNumbers.size();counter++){
                    switch (preferenceNumbers.get(counter)){
                        case 0:
                            preferenceStrings.add("food");
                            break;
                        case 1:
                            preferenceStrings.add("travel");
                            break;
                        case 2:
                            preferenceStrings.add("lifestyle");
                            break;
                        case 3:
                            preferenceStrings.add("education");
                            break;
                        case 4:
                            preferenceStrings.add("tech");
                            break;
                    }
                }
            }
            else{
                Toast.makeText(this,"No preferecnes",Toast.LENGTH_SHORT).show();
            }

            TextView interestsfromParseTV = (TextView) findViewById(R.id.interestsfromparsetextview);
            String big = new String() ;
            for(int count=0;count<preferenceStrings.size();count++){
            big = big+","+preferenceStrings.get(count);
            }
            big=big.substring(1);
            interestsfromParseTV.setText(big);

//            emailid.setText(email1);
//            String edurating = currentUser.get("rating_education").toString();
            int edurating = currentUser.getInt("rating_education");
//            edXPVal.setText(edurating);
            int foodrating = currentUser.getInt("rating_food");
//            edXPVal.setText(foodrating);
            int travelrating = currentUser.getInt("rating_travel");
//            edXPVal.setText(travelrating);
            int lifestylerating = currentUser.getInt("rating_lifestyle");
//            edXPVal.setText(lifestylerating);
            int techrating = currentUser.getInt("rating_tech");
//            edXPVal.setText(techrating);
            //nameTextView.setText(name);
            //emailTextView.setText(email1);

        } else {
            // show the signup or profile screen
        }

    }

    public void afterClickingEditInterests(View v){
        dialog_interests.show(getSupportFragmentManager(),"chooseinterests");
if(currentUser != null){
    String userID = (String) currentUser.get("objectId");
    // Create a pointer to an object of class Point with id dlkj83d
    ParseObject point = ParseObject.createWithoutData("User", userID);

// Set a new value on quantity
    point.put("preferences", updatedInterests);

// Save
    point.saveInBackground(new SaveCallback() {
        public void done(ParseException e) {
            if (e == null) {
                // Saved successfully.
            } else {
                // The save failed.
            }
        }
    });
}
else{
    Toast.makeText(v.getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
}


    }

}
