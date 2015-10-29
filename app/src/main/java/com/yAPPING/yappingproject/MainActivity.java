package com.yAPPING.yappingproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.sinch.android.rtc.SinchError;

import java.net.InetAddress;

public class MainActivity extends BaseActivity implements OnClickListener, SinchService.StartFailedListener {

    EditText username, password;
    Button signin, createaccount;
    TextView t1, t2;
    private ProgressDialog progressDialog;
    private Menu menu;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        System.out.print("Login Back Pressed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login..");
        progressDialog.setCancelable(false);

		/*ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
		testObject.saveInBackground();*/

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        signin = (Button) findViewById(R.id.bSignIn);
        createaccount = (Button) findViewById(R.id.createaccount);
        //t1 = (TextView)findViewById(R.id.createanaccount) ;
        //t2 = (TextView)findViewById(R.id.forgotpassword) ;
        createaccount.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

//    public void startProfile(View v) {
//        Intent i1 = new Intent(v.getContext(), ProfileActivity.class);
//        startActivity(i1);
//    }


    @Override
    public void onClick(final View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.bSignIn) {
            if (isInternetAvailable(this)) {

                //Temporary when no mobile data
//			Intent i = new Intent(v.getContext(),Home.class);
//			startActivity(i);
                final String x = username.getText().toString();
                String y = password.getText().toString();
                if (x.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (y.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
//                SinchClient sinchClient = Sinch.getSinchClientBuilder()
//                        .context(v.getContext())
//                        .userId(x)
//                        .applicationKey("74b2e753-89f0-4474-8577-cbd48669db4d")
//                        .applicationSecret("Z1OrHB0XH0Ol7+p3hZ4D+w==app-secret")
//                        .environmentHost("sandbox.sinch.com")
//                        .build();
//                sinchClient.setSupportCalling(true);
//                sinchClient.start();
                    if (!getSinchServiceInterface().isStarted()) {
                        getSinchServiceInterface().startClient(x);
                    }

                    ParseUser.logInInBackground(x, y, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {


                            if (user != null) {
                                progressDialog.dismiss();
                                // Hooray! The user is logged in.
                                //Sinch
//                            if (!getSinchServiceInterface().isStarted()) {
//                                getSinchServiceInterface().startClient(x);
//                            }
                                //Sinch

                                Intent i = new Intent(v.getContext(), Home.class);
                                startActivity(i);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                // Signup failed. Look at the ParseException to see what happened.
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
                    });
                }
            } else {
                Toast.makeText(this, "You are not connected to the internet", Toast.LENGTH_SHORT).show();
            }
        }
            if (v.getId() == R.id.createaccount) {
                Intent i2 = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(i2);
                //finish();

            }
        }


    @Override
    public void onStartFailed(SinchError error) {
        return;
    }

    @Override
    public void onStarted() {
//Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
    }

    public boolean isInternetAvailable(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }

}


