package com.yAPPING.yappingproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikar on 10/9/15.
 */
public class MyQuestions extends Activity {
    ListView myquestionslv;
    ParseUser currentUser = ParseUser.getCurrentUser();
    ArrayList<String> stringMyQuestions;
    String currentUsername;
    ProgressDialog mProgressDialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_questions);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
//    progressDialog.show();

        stringMyQuestions = new ArrayList<String>();
        myquestionslv = (ListView) findViewById(R.id.myquestionslistView);


        currentUsername = currentUser.getUsername();
mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Deleting");
mProgressDialog.setMessage("Please wait");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.whereEqualTo("username", currentUsername);
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> questionsList, ParseException e) {
                if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                    for (int i = 0; i < questionsList.size(); i++) {
                        ParseObject p1 = questionsList.get(i);
                        String questionassociated = p1.getString("question");
                        stringMyQuestions.add(i, new String(questionassociated));
//                        System.out.println(stringMyQuestions);
//                        Toast.makeText(MyQuestions.this, "this is loop" + i+questionassociated, Toast.LENGTH_SHORT).show();
                    }
                    myquestionslv.setAdapter(new MyQuestionsAdapter(stringMyQuestions));

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

//        Toast.makeText(this,"hi",Toast.LENGTH_SHORT).show();
//    stringMyQuestions.add("forceadd");

    progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent igoToHome = new Intent(this,Home.class);
        startActivity(igoToHome);
    }

    class MyQuestionsAdapter extends BaseAdapter implements View.OnClickListener {
        List<String> stringMyQuestions;
        private String questionCorresponding;
//    ParseUser currentuser = ParseUser.getCurrentUser();
//    currentusername = fin

        MyQuestionsAdapter(ArrayList<String> stringMyQuestions) {
            this.stringMyQuestions = stringMyQuestions;
        }


        @Override
        public int getCount() {
            return stringMyQuestions.size();
        }

        @Override
        public Object getItem(int position) {
            return stringMyQuestions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.print("This is the size" + stringMyQuestions.size());
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.my_questions_row, null);
            TextView textView = (TextView) convertView.findViewById(R.id.my_questions_row_textview);
            textView.setText(stringMyQuestions.get(position));
            Button bViewAnswersMyQuestions = (Button) convertView.findViewById(R.id.bview_answers_myqestions_row);
            bViewAnswersMyQuestions.setOnClickListener(this);
            Button bDelQuestionMyQuestions = (Button) convertView.findViewById(R.id.bdel_answers_row_textview);
            bDelQuestionMyQuestions.setOnClickListener(this);
            return convertView;
        }

        @Override
        public void onClick(final View v) {
            if (v.getId() == R.id.bview_answers_myqestions_row) {
                Intent iViewAnswers = new Intent(v.getContext(), ViewAnswers.class);

                RelativeLayout parentRow = (RelativeLayout) v.getParent();
//    System.out.println(parentRow);

                for (int i = 0; i < parentRow.getChildCount(); i++) {
                    View childView = parentRow.getChildAt(i);
                    if (childView instanceof TextView) {
                        if (childView.getId() == R.id.my_questions_row_textview) {
                            TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                            questionCorresponding = textview.getText().toString();
//                answerInText.put("question1", questionCorresponding);
                        }
                    }

                }

                iViewAnswers.putExtra("questionclicked", questionCorresponding);
//    Toast.makeText(view.getContext(),"hia",Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(iViewAnswers);

            }
            if (v.getId() == R.id.bdel_answers_row_textview) {
                RelativeLayout parentRow = (RelativeLayout) v.getParent();
//    System.out.println(parentRow);

                for (int i = 0; i < parentRow.getChildCount(); i++) {
                    View childView = parentRow.getChildAt(i);
                    if (childView instanceof TextView) {
                        if (childView.getId() == R.id.my_questions_row_textview) {
                            TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                            questionCorresponding = textview.getText().toString();
//                answerInText.put("question1", questionCorresponding);
                        }
                    }

                }
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
                query.whereEqualTo("username", currentUsername);
//                Toast.makeText(v.getContext(),currentUsername,Toast.LENGTH_SHORT).show();
//                Toast.makeText(v.getContext(),questionCorresponding,Toast.LENGTH_SHORT).show();
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> scoreList, ParseException e) {
//                        if (e == null) {
//                            Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                        } else {
//                            Log.d("score", "Error: " + e.getMessage());
//                        }
//                        Toast.makeText(v.getContext(),scoreList.get(0).getString("question"),Toast.LENGTH_SHORT).show();
                        for (ParseObject counter : scoreList) {
//                            Toast.makeText(v.getContext(),counter.getString("question"),Toast.LENGTH_SHORT).show();
                            if (counter.getString("question").equalsIgnoreCase(questionCorresponding)) {
//                                mProgressDialog.show();
                                counter.deleteInBackground();
                                Toast.makeText(v.getContext(), "Question deleted", Toast.LENGTH_SHORT).show();
                                Intent irefresh  =new Intent(v.getContext(),MyQuestions.class);
startActivity(irefresh);


                            } else {
                                continue;
                            }
                        }
                    }
                });

            }
        }
    }

}