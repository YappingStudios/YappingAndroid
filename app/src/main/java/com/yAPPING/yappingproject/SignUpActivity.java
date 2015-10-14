package com.yAPPING.yappingproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

/**
 * Created by srikar on 5/9/15.
 */
public class SignUpActivity extends FragmentActivity {
    ParseUser user;
    ArrayList userPreferences =new ArrayList();
    Dialog_interests dialog_interests = new Dialog_interests(userPreferences);

    EditText username, userpass, useremail;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up..");
        progressDialog.setCancelable(false);
        username = (EditText) findViewById(R.id.nameeditText);
        userpass = (EditText) findViewById(R.id.passEditText);
        useremail = (EditText) findViewById(R.id.emaileditText);}

    public void signUpClicked(final View v) {
        if (isInternetAvailable(this)) {

        if (username.getText().toString().equals("")) {
            Toast.makeText(v.getContext(), "Enter Username", Toast.LENGTH_SHORT).show();
        } else if (userpass.getText().toString().equals("")) {
            Toast.makeText(v.getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (useremail.getText().toString().equals("")) {
            Toast.makeText(v.getContext(), "Enter mail", Toast.LENGTH_SHORT).show();
        } else if (userPreferences.isEmpty()) {
            Toast.makeText(v.getContext(), "Please choose at least one Preference to view those questions", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();


            //Parse part
            user = new ParseUser();
            user.setUsername(username.getText().toString());
            user.setPassword(userpass.getText().toString());
            user.setEmail(useremail.getText().toString());
            user.put("preferences", userPreferences);
            user.put("rating_food", 0);
            user.put("rating_travel", 0);
            user.put("rating_lifestyle", 0);
            user.put("rating_tech", 0);
            user.put("rating_education", 0);

// other fields can be set just like with ParseObject
            // user.put("phone", "650-253-0000");

            user.signUpInBackground(new SignUpCallback() {

                                        public void done(com.parse.ParseException e) {
                                            if (e == null) {
                                                progressDialog.dismiss();
                                                // Hooray! Let them use the app now.
                                                Intent i = new Intent(v.getContext(), MainActivity.class);
                                                startActivity(i);

                                            } else {
                                                // Sign up didn't succeed. Look at the ParseException
                                                // to
                                                // figure out what went wrong
                                                progressDialog.dismiss();
                                                Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                                builder.setMessage("Error");
                                                builder.setTitle("Oops");
                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                });
                                                AlertDialog dialog = builder.create();
                                                dialog.show();

                                            }
                                        }
                                    }

            );
        }

    }
        else{
            Toast.makeText(this,"You are not connected to the internet",Toast.LENGTH_SHORT).show();
        }
    }





    public void setPefSignup(View v){
        dialog_interests.show(getSupportFragmentManager(),"chooseinterests");


    }
    public boolean isInternetAvailable(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


}
