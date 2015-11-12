package com.yAPPING.yappingproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends FragmentActivity {
    ArrayList<Integer> updatedInterests =new ArrayList();
    ParseUser currentUser = ParseUser.getCurrentUser();
    //    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
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
    TextView totalXPVal;
    private int foodrating;
    private int totalrating;
    private int edurating;
    private int travelrating;
    private int lifestylerating;
    private int techrating;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iHomeStart = new Intent(this,Home.class);
        startActivity(iHomeStart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        ParseObject user_install_pair = new ParseObject("user_install_pair");

        name = (TextView) findViewById(R.id.namefromparsetextview);
        emailid = (TextView) findViewById(R.id.emailfromparsetextview);
        edXPVal= (TextView) findViewById(R.id.eduXPfromParse);
        foodXPVal = (TextView) findViewById(R.id.foodXPfromParse);
        travelXPVal = (TextView) findViewById(R.id.travelXPfromParse);
        lifestyleXPVal = (TextView) findViewById(R.id.lifestyleXPfromParse);
        techXPVal = (TextView) findViewById(R.id.techXPfromParse);
        totalXPVal = (TextView) findViewById(R.id.xpfromparse);
        if (currentUser != null) {
            // do stuff with the user
            String name1 =  currentUser.getUsername();
            name.setText(name1);
            String email1 = currentUser.getEmail();
            emailid.setText(email1);

            List<Integer> categoriesList =currentUser.getList("preferences");
            String categoryString = new String();
            if(categoriesList.contains(0)){
                categoryString = categoryString+"food;";
            }
            if(categoriesList.contains(1)){
                categoryString = categoryString+"travel;";
            }
            if(categoriesList.contains(2)){
                categoryString = categoryString+"lifestyle;";
            }
            if(categoriesList.contains(3)){
                categoryString = categoryString+"education;";
            }
            if(categoriesList.contains(4)){
                categoryString = categoryString+"tech;";
            }

            TextView interestsfromParseTV = (TextView) findViewById(R.id.interestsfromparsetextview);

            interestsfromParseTV.setText(categoryString);

//            emailid.setText(email1);
//            String edurating = currentUser.get("rating_education").toString();
            String currentUserName = currentUser.getUsername();

            ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("XPClass");
            q.whereEqualTo("username",currentUserName);
            q.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject ob = list.get(i);
                        int totalXP = ob.getInt("totalXP");
                        totalXPVal.setText(totalXP + "");

                    }
                }
            });

//            totalrating = currentUser.getInt("rating_total");
//            totalXPVal.setText(totalrating+"");

            edurating = currentUser.getInt("rating_education");
//            edXPVal.setText(edurating+"");
            edXPVal.setText("0,currently disabled");

            foodrating = currentUser.getInt("rating_food");
//            foodXPVal.setText(foodrating + "");
            foodXPVal.setText("0,currently disabled");
            travelrating = currentUser.getInt("rating_travel");
//            travelXPVal.setText(travelrating+"");
            travelXPVal.setText("0,currently disabled");
            lifestylerating = currentUser.getInt("rating_lifestyle");
//            lifestyleXPVal.setText(lifestylerating+"");
            lifestyleXPVal.setText("0,currently disabled");
            techrating = currentUser.getInt("rating_tech");
            techXPVal.setText("0,currently disabled");
//            techXPVal.setText(techrating+"");
            //nameTextView.setText(name);
            //emailTextView.setText(email1);

        } else {
            // show the signup or profile screen
        }

    }

    public void afterClickingEditInterests(final View v){
//            System.out.print("This is food rating"+foodrating);
//        Toast.makeText(v.getContext(),"This is food rating"+foodrating,Toast.LENGTH_SHORT).show();
        if(currentUser != null){
            dialog_interests.show(getSupportFragmentManager(),"chooseinterests");
            while(dialog_interests.isVisible()){
                System.out.print("Do Nothing");
            }
            Toast.makeText(this,"Preferences will be saved when confirm pressed,click back to refresh",Toast.LENGTH_LONG).show();
            List<Integer> categoriesList =currentUser.getList("preferences");
            String categoryString = new String();
            if(categoriesList.contains(0)){
                categoryString = categoryString+"food;";
            }
            if(categoriesList.contains(1)){
                categoryString = categoryString+"travel;";
            }
            if(categoriesList.contains(2)){
                categoryString = categoryString+"lifestyle;";
            }
            if(categoriesList.contains(3)){
                categoryString = categoryString+"education;";
            }
            if(categoriesList.contains(4)){
                categoryString = categoryString+"tech;";
            }

            TextView interestsfromParseTV = (TextView) findViewById(R.id.interestsfromparsetextview);

            interestsfromParseTV.setText(categoryString);









//    String userID = currentUser.getObjectId().toString();
            // Create a pointer to an object of class Point with id dlkj83d
//    ParseObject point = ParseObject.createWithoutData("User", userID);

// Set a new value on quantity
//    point.put("preferences", updatedInterests);
//
//// Save
//    point.saveInBackground(new SaveCallback() {
//        public void done(ParseException e) {
//            if (e == null) {
//                // Saved successfully.
//            } else {
//                // The save failed.
//            }
//        }
//    });
//    ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
//
//// Retrieve the object by id
//    query.getInBackground(userID, new GetCallback<ParseObject>() {
//        public void done(ParseObject userInstance, ParseException e) {
//            if (e == null) {
//                // Now let's update it with some new data. In this case, only cheatMode and score
//                // will get sent to the Parse Cloud. playerName hasn't changed.
////while(dialog_interests.flag==0){}
//
//                userInstance.put("preferences", updatedInterests);
//                userInstance.saveInBackground();
//                Toast.makeText(v.getContext(), "Preferences saved", Toast.LENGTH_SHORT).show();
//
//
//            }
//        }
//    });


        }
        else{
            Toast.makeText(v.getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
        }


    }

}
