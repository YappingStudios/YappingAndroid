package com.yAPPING.yappingproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikar on 10/9/15.
 */
public class SeeAllQuestions extends Activity {
    ArrayList<String> stringDataFromParse;
    List<ParseObject> ob;
    ListView seeAllQuest;
    private String userAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_questions);

        seeAllQuest = (ListView) findViewById(R.id.seeallquestlistView);
        stringDataFromParse = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "Questions");
        query.orderByDescending("createdAt");

        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            stringDataFromParse.add((String) country.get("question"));
        }
        seeAllQuest.setAdapter(new AllQuestAdapter(stringDataFromParse));

    }
    public void answerInText(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your answer");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userAnswer = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}


class AllQuestAdapter extends BaseAdapter implements View.OnClickListener {
    List<String> allQuest;
    ParseObject answerInText = new ParseObject("AnswerInText");
    public String questionCorresponding;

    AllQuestAdapter(List<String> list) {
        this.allQuest = list;

    }


    @Override
    public int getCount() {
        return allQuest.size();
    }

    @Override
    public Object getItem(int position) {
       return allQuest.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.home_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.questions);
        Button answerButton = (Button) convertView.findViewById(R.id.banswerintext);
        Button viewAnswers1 = (Button) convertView.findViewById(R.id.bViewAnswers);
        answerButton.setOnClickListener(this);
        viewAnswers1.setOnClickListener(this);

        textView.setText(allQuest.get(position));
        return convertView;
        
        
        
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banswerintext:
                answerForQuestion(v);
                break;
            case R.id.bViewAnswers:
                ViewAnswersMethod(v);
                break;
            case R.id.checkBox1:
                callStart(v);
                break;
            //
        }

    }

    private void callStart(final View v) {
        String questCorr = new String();
//    String askerNameForPassing =new String();
        RelativeLayout parentRow = (RelativeLayout) v.getParent();
        for (int i = 0; i < parentRow.getChildCount(); i++) {
            View childView = parentRow.getChildAt(i);
            if (childView instanceof TextView) {
                if (childView.getId() == R.id.questions) {
                    TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                    questCorr = textview.getText().toString();
//Toast.makeText(v.getContext(),askerNameForPassing,Toast.LENGTH_SHORT).show();
//                answerInText.put("question1", questionCorresponding);
                }
            }

        }
        ParseQuery<ParseObject> query = new ParseQuery("Questions");
        query.whereEqualTo("question", questCorr);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                String askerNameForPassing = scoreList.get(0).getString("username");
                Intent icallStart = new Intent(v.getContext(), CallStartActivity.class);
                icallStart.putExtra("askernameextra", askerNameForPassing);
                v.getContext().startActivity(icallStart);
            }


        });
    }

    private void ViewAnswersMethod(View view) {


        Intent iViewAnswers = new Intent(view.getContext(), ViewAnswers.class);

        RelativeLayout parentRow = (RelativeLayout) view.getParent();
//    System.out.println(parentRow);

        for (int i = 0; i < parentRow.getChildCount(); i++) {
            View childView = parentRow.getChildAt(i);
            if (childView instanceof TextView) {
                if (childView.getId() == R.id.questions) {
                    TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                    questionCorresponding=textview.getText().toString();
//                answerInText.put("question1", questionCorresponding);
                }
            }

        }

        iViewAnswers.putExtra("questionclicked", questionCorresponding);
//    Toast.makeText(view.getContext(),"hia",Toast.LENGTH_SHORT).show();
        view.getContext().startActivity(iViewAnswers);

    }

    private void answerForQuestion(final View view) {
        //        int position = (Integer)v.getTag();
//        Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
//        Toast.makeText(view.getContext(),"Test",Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Your answer");

// Set up the input
        final EditText input = new EditText(view.getContext());

//      WindowManager.LayoutParams lp = (WindowManager.LayoutParams) input.getLayoutParams();
//        input.setLayoutParams(lp);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userAnswer = input.getText().toString();
                Toast.makeText(view.getContext(), userAnswer, Toast.LENGTH_SHORT).show();
//                ParseObject answerInText = new ParseObject("AnswerInText");
                answerInText.put("answers", userAnswer);
                // answerInText.put("who is answering", currentUser);

//        answerInText.put("whose question",);

                RelativeLayout parentRow = (RelativeLayout) view.getParent();
                System.out.println(parentRow);

                for (int i = 0; i < parentRow.getChildCount(); i++) {
                    View childView = parentRow.getChildAt(i);
                    if (childView instanceof TextView) {
                        if (childView.getId() == R.id.questions) {
                            TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                            questionCorresponding=textview.getText().toString();
                            answerInText.put("question1", questionCorresponding);
                        }
                    }

                }
                answerInText.saveInBackground();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
//        answerInText.saveInBackground();

        //store answer in Parse

//        answerInText.saveInBackground();

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
//        query.whereEqualTo("question",questionAtPosition);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> questionWithSameText, ParseException e) {
//                if (e == null) {
//                    Log.d("questioncompare", "Retrieved " + questionWithSameText.size() + " scores");
//                    Use this somehow to retrieve question ID
//                } else {
//                    Log.d("questioncompare", "Error: " + e.getMessage());
//                }
//            }
//        });
    }

}