package com.yAPPING.yappingproject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
/**
 * Created by srikar on 4/9/15.
 */
public class Dialog_interests extends DialogFragment {


    ArrayList mSelectedItems;
    Dialog_interests(ArrayList list){
        mSelectedItems = list;
    }
    ParseUser currentUser = ParseUser.getCurrentUser();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //mSelectedItems = new ArrayList();  // Where we track the selected items


        if (currentUser != null) {
            // do stuff with the user
            ParseObject profilepreferences = new ParseObject("Preferences");

            profilepreferences.put("Preferences", mSelectedItems);


            String name = (String) currentUser.get("username");
            //String email1 = (String) currentUser.get("email");

            profilepreferences.put("username", name);

            profilepreferences.saveInBackground();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

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
