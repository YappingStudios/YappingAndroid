package com.yAPPING.yappingproject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
/**
 * Created by srikar on 4/9/15.
 */
public class Dialog_interests extends DialogFragment {

    ArrayList<Integer> mSelectedItems;
    private ParseObject profilepreferences;
    int flag=0;
    Context view;

    public Dialog_interests(ArrayList<Integer> list){
        mSelectedItems = list;

    }
    ParseUser currentUser = ParseUser.getCurrentUser();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //mSelectedItems = new ArrayList();  // Where we track the selected items


        if (currentUser != null) {
            // do stuff with the user
            profilepreferences = new ParseObject("Preferences");




            String name = (String) currentUser.get("username");
            //String email1 = (String) currentUser.get("email");

            profilepreferences.put("username", name);

            profilepreferences.saveInBackground();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.chooseInterests)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.interests, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
//            profilepreferences.put("Preferences", mSelectedItems);
//                        profilepreferences.saveInBackground();
//                        flag=1;


                        String userID = currentUser.getObjectId().toString();
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                        query.getInBackground(userID, new GetCallback<ParseObject>() {
                            public void done(ParseObject userInstance, ParseException e) {
                                if (e == null) {
                                    // Now let's update it with some new data. In this case, only cheatMode and score
                                    // will get sent to the Parse Cloud. playerName hasn't changed.
//while(dialog_interests.flag==0){}

                                    userInstance.put("preferences", mSelectedItems);
                                    userInstance.saveInBackground();
//                                    Toast.makeText(getActivity(), "Preferences saved", Toast.LENGTH_SHORT).show();

//                                    TextView interestsfromParseTV = (TextView) findViewById(R.id.interestsfromparsetextview);
//                                    String big = new String() ;
//                                    for(int count=0;count<mSelectedItems.size();count++){
//                                        big = big+","+mSelectedItems.get(count);
//                                    }
//                                    interestsfromParseTV.setText(big);

                                }
                            }
                        });


//                        Toast.makeText(getActivity(), "Preferences saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}
