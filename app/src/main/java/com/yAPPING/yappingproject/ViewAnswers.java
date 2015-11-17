package com.yAPPING.yappingproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikar on 14/9/15.
 */
public class ViewAnswers extends Activity{

    ListView valv;
    //    List<String> answers11;
    ArrayList<String> answers11;
    private ArrayList<String> askerer_name;


//    valv.setAdapter(new )


//    ParseQuery<ParseObject> query = ParseQuery.getQuery("AnswerInText");
//    query.whereEqualTo("question",questionclicked);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_answers);
        answers11=new ArrayList<String>();
        askerer_name = new ArrayList<String>();
        valv = (ListView) findViewById(R.id.viewanswerslistView);
        Intent intent = getIntent();

//        answers11.add("hai");
        final String questionclicked = intent.getExtras().getString("questionclicked");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AnswerInText");
        query.whereEqualTo("question1", questionclicked);
        query.orderByDescending("answer_likes");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> questionswithsametext, ParseException e) {
                if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                    ParseObject oneOfTheQuestionsWithSameText = questionswithsametext.get(0);
//                    answers=  oneOfTheQuestionsWithSameText.getString("answers");
                    for (int i = 0; i < questionswithsametext.size(); i++) {
                        ParseObject oneOfTheQuestionsWithSameText = questionswithsametext.get(i);
                        String oneOfTheQuestionsText = oneOfTheQuestionsWithSameText.getString("answers");
                        String oneOfTheUsersNames = oneOfTheQuestionsWithSameText.getString("answerer_name");
                        answers11.add(oneOfTheQuestionsText);
                        askerer_name.add(oneOfTheUsersNames);
                    }
                    valv.setAdapter(new ViewAnswersAdapter(answers11,askerer_name,questionclicked));
//                    answers11.add("hi");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });






    }
}


class ViewAnswersAdapter extends BaseAdapter {
    List<String> answers, askerer_name;
    ParseUser currentUserobj = ParseUser.getCurrentUser();
    String currentUser = currentUserobj.getUsername();
    String questionclicked;
    ViewAnswersAdapter(List<String> list, List<String> list1,String questionclicked) {
        this.answers = list;
        this.askerer_name = list1;
        this.questionclicked=questionclicked;
    }
//    final String  MY_PREFS_NAME = "MyPrefsFile"+questionclicked;


    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Object getItem(int position) {
        return answers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.view_answers_row, null);
//        final SharedPreferences preferences = convertView.getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final TextView textView = (TextView) convertView.findViewById(R.id.view_answers_row_textview);
        TextView textView1 = (TextView) convertView.findViewById(R.id.view_answers_row_askerer_name);
        final TextView answer_likes_textview  = (TextView) convertView.findViewById(R.id.answer_likes_textview);
        final ToggleButton bPromote = (ToggleButton) convertView.findViewById(R.id.bPromote);


//        bPromote.setChecked(toggleStateCheck(convertView,position,questionclicked));
//        ParseQuery<ParseObject> qu = new ParseQuery<ParseObject>("Key_value");
//        qu.whereEqualTo("key",questionclicked+position);
//        qu.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> listu, ParseException e) {
//                if(listu==null){
//                    bPromote.setChecked(false);
//                }
//                else{
//                    for(int i=0;i<listu.size();i++){
//                        ParseObject ob = listu.get(i);
//                        bPromote.setChecked((Boolean)ob.get("value"));
//                    }
//                }
//            }
//        });
//        bPromote.setTag(1,position);
//        if(bPromote.getTag() != null) {
//            if(bPromote.getTag().toString().equals(questionclicked)) {
//                SharedPreferences prefs = convertView.getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
//                Boolean tgpref = prefs.getBoolean("tgpref" + position + questionclicked, false);
//                bPromote.setChecked(tgpref);
//            }
//        }

        textView.setText(answers.get(position));
        textView1.setText(askerer_name.get(position));
//        answer_likes_textview.setText(answerlikes);

        ParseQuery<ParseObject> qqq = new ParseQuery<ParseObject>("AnswerInText");

        qqq.whereEqualTo("answers", answers.get(position));
        qqq.whereEqualTo("question1", questionclicked);
//        qqq.whereEqualTo("answerer_name",XPName);
        qqq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> listt, ParseException e) {
                if (listt == null) {
                    System.out.print("Do nothing");
                } else {
                    for (int i = 0; i < listt.size(); i++) {
                        ParseObject ob = listt.get(i);
                        int answerlikes = ob.getInt("answer_likes");
                        answer_likes_textview.setText(answerlikes + "");
                    }

                }

            }
        });
        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("AnswerInText"+currentUser);

        q.whereEqualTo("answers", answers.get(position));
        q.whereEqualTo("question1", questionclicked);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list == null) {
                    System.out.print("Do nothing");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject ob = list.get(i);
                        bPromote.setChecked(ob.getBoolean("togglestate"));

                    }

                }

            }
        });

        bPromote.setOnClickListener(new View.OnClickListener() {
//            int clickcounter=0;
            @Override
            public void onClick(final View v) {
//                clickcounter++;
//                Toast.makeText(v.getContext(),questionclicked+position,Toast.LENGTH_SHORT).show();
                RelativeLayout parent = (RelativeLayout) v.getParent();
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View childview = parent.getChildAt(i);
                    if (childview instanceof TextView) {
                        if (childview.getId() == R.id.view_answers_row_askerer_name) {
                            final String XPName = ((TextView) childview).getText().toString();

                            ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("XPClass");
                            q.whereEqualTo("username", XPName);
                            q.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> list, ParseException e) {
                                    for (int j = 0; j < list.size(); j++) {
                                        ParseObject ob = list.get(j);
                                        int totalXP = ob.getInt("totalXP");
                                        if (bPromote.getText().equals("Demote!")) {
//                                              Toast.makeText(v.getContext(),questionclicked+position,Toast.LENGTH_SHORT).show();
//                                            ParseQuery<ParseObject> qq = new ParseQuery<ParseObject>("Key_value");
//                                            qq.whereEqualTo("key", questionclicked + position);
//                                            qq.findInBackground(new FindCallback<ParseObject>() {
//                                                @Override
//                                                public void done(List<ParseObject> list, ParseException e) {
//                                                    if (list.size() == 0) {
//                                                        ParseObject key_value_likes = new ParseObject("Key_value");
//                                                        key_value_likes.put("key", questionclicked + position);
//                                                        key_value_likes.put("value", true);
//                                                        key_value_likes.saveInBackground();
//
//                                                    } else {
//                                                        for (int i = 0; i < list.size(); i++) {
//                                                            ParseObject ob = list.get(i);
//                                                            ob.put("value", true);
//                                                            ob.saveInBackground();
//                                                        }
//
//                                                    }
//                                                }
//                                            });


//                                            SharedPreferences.Editor editor = preferences.edit();
//                                            editor.putBoolean("tgpref" + position, true); // value to store
//                                            editor.commit();
//                                            Toast.makeText(v.getContext(), totalXP + "", Toast.LENGTH_SHORT).show();
                                            totalXP++;
//                                            Toast.makeText(v.getContext(), totalXP + "", Toast.LENGTH_SHORT).show();
                                            ob.put("totalXP", totalXP);
                                            ob.saveInBackground();
                                            bPromote.setTag(questionclicked);
//                                            bPromote.setTag(null);
                                            //Increase answer likes
                                            ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("AnswerInText");
                                            RelativeLayout parent1 = (RelativeLayout) v.getParent();
                                            for (int i = 0; i < parent1.getChildCount(); i++) {
                                                View childview1 = parent1.getChildAt(i);
                                                if (childview1 instanceof TextView) {
                                                    if (childview1.getId() == R.id.view_answers_row_textview) {
                                                        final String answer = ((TextView) childview1).getText().toString();
                                                        q.whereEqualTo("answers", answer);
                                                        q.whereEqualTo("question1",questionclicked);
                                                        q.whereEqualTo("answerer_name",XPName);
                                                        q.findInBackground(new FindCallback<ParseObject>() {
                                                            @Override
                                                            public void done(List<ParseObject> listincc, ParseException e) {
                                                                for (int i = 0; i < listincc.size(); i++) {
                                                                    ParseObject ob = listincc.get(i);
                                                                    int answerlikes = ob.getInt("answer_likes");
                                                                    answerlikes++;
                                                                    ob.put("answer_likes", answerlikes);
//                                                                    ob.put("togglestate",true);


//                                                                    New class starts here
                                                                    ParseQuery<ParseObject> quser = new ParseQuery<ParseObject>("AnswerInText"+currentUser);
                                                                    quser.whereEqualTo("answers", answer);
                                                                    quser.whereEqualTo("question1", questionclicked);
                                                                    quser.whereEqualTo("answerer_name", XPName);
                                                                    quser.findInBackground(new FindCallback<ParseObject>() {
                                                                        @Override
                                                                        public void done(List<ParseObject> listnew, ParseException e) {
                                                                            if (listnew.isEmpty()) {
                                                                                ParseObject puserobj = new ParseObject("AnswerInText"+currentUser);
//                                                                                Toast.makeText(v.getContext(),"finished creating a new class for:"+currentUser,Toast.LENGTH_SHORT).show();
                                                                                puserobj.put("answers", answer);
                                                                                puserobj.put("question1", questionclicked);
                                                                                puserobj.put("answerer_name", XPName);
                                                                                puserobj.put("togglestate", true);
                                                                                puserobj.saveInBackground();

                                                                            } else {
                                                                                for (int iuser = 0; iuser < listnew.size(); iuser++) {
                                                                                    ParseObject obuser = listnew.get(iuser);
                                                                                    obuser.put("togglestate", true);
                                                                                }
                                                                            }
                                                                        }
                                                                    });


                                                                  //New class ends here

                                                                    answer_likes_textview.setText(answerlikes + "");
                                                                    ob.saveInBackground();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                        } else {
                                            Toast.makeText(v.getContext(),questionclicked+position,Toast.LENGTH_SHORT).show();
//                                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Key_value");
//                                            query.whereEqualTo("key", questionclicked + position);
//                                            query.findInBackground(new FindCallback<ParseObject>() {
//                                                @Override
//                                                public void done(List<ParseObject> list, ParseException e) {
//                                                    for (int i = 0; i < list.size(); i++) {
//                                                        ParseObject ob = list.get(i);
//                                                        ob.put("value", false);
//                                                        ob.saveInBackground();
//                                                    }
//                                                }
//                                            });
//                                            SharedPreferences.Editor editor = preferences.edit();
//                                            editor.putBoolean("tgpref" + position, false); // value to store
//                                            editor.commit();
//                                            Toast.makeText(v.getContext(), totalXP + "", Toast.LENGTH_SHORT).show();
                                            totalXP--;
//                                            Toast.makeText(v.getContext(), totalXP + "", Toast.LENGTH_SHORT).show();
                                            ob.put("totalXP", totalXP);
                                            ob.saveInBackground();
//                                            bPromote.setTag(questionclicked);
                                            bPromote.setTag(null);
                                            //Decrease answer likes
                                            ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("AnswerInText");
                                            RelativeLayout parent1 = (RelativeLayout) v.getParent();
                                            for (int i = 0; i < parent1.getChildCount(); i++) {
                                                View childview1 = parent1.getChildAt(i);
                                                if (childview1 instanceof TextView) {
                                                    if (childview1.getId() == R.id.view_answers_row_textview) {
                                                        final String answer = ((TextView) childview1).getText().toString();
                                                        q.whereEqualTo("answers", answer);
                                                        q.whereEqualTo("question1",questionclicked);
                                                        q.whereEqualTo("answerer_name",XPName);
                                                        q.findInBackground(new FindCallback<ParseObject>() {
                                                            @Override
                                                            public void done(List<ParseObject> listinc, ParseException e) {
                                                                for (int i = 0; i < listinc.size(); i++) {
                                                                    ParseObject ob = listinc.get(i);
                                                                    int answerlikes = ob.getInt("answer_likes");
                                                                    answerlikes--;
                                                                    ob.put("answer_likes", answerlikes);
//                                                                    ob.put("togglestate",false);

                                                                    //                                                                    New class starts here
                                                                    ParseQuery<ParseObject> quser = new ParseQuery<ParseObject>("AnswerInText"+currentUser);
                                                                    quser.whereEqualTo("answers", answer);
                                                                    quser.whereEqualTo("question1", questionclicked);
                                                                    quser.whereEqualTo("answerer_name", XPName);
                                                                    quser.findInBackground(new FindCallback<ParseObject>() {
                                                                        @Override
                                                                        public void done(List<ParseObject> list, ParseException e) {
                                                                            if (list == null) {
                                                                                ParseObject puserobj = new ParseObject("AnswerInText" + currentUser);
                                                                                puserobj.put("answers", answer);
                                                                                puserobj.put("question1", questionclicked);
                                                                                puserobj.put("answerer_name", XPName);
                                                                                puserobj.put("togglestate", false);
                                                                                puserobj.saveInBackground();

                                                                            } else {
                                                                                for (int iuser = 0; iuser < list.size(); iuser++) {
                                                                                    ParseObject obuser = list.get(iuser);
                                                                                    obuser.put("togglestate", false);
                                                                                }
                                                                            }
                                                                        }
                                                                    });


                                                                    //New class ends here




                                                                    answer_likes_textview.setText(answerlikes + "");
                                                                    ob.saveInBackground();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });





        return convertView;

    }

//    public boolean toggleStateCheck(View view, int position, String questionclicked ){
////        SharedPreferences prefs = view.getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
////        Boolean tgpref = prefs.getBoolean("tgpref"+position, false);
////        if(tgpref==true){
////return true;
////        }
////        else{
////return false;
////        }
//        Boolean state;
//        ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("Key_value");
//        q.whereEqualTo("key", questionclicked + position);
//        q.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                for(int i=0;i<list.size();i++){
//                    ParseObject pob = list.get(i);
//                     state =pob.getBoolean("value");
//
//                }
//
//
//            }
//        });
//                    return state;
//
//    }
}

//    @Override
//    public void onClick(final View v) {
//        if (v.getId() == R.id.bPromote) {
////            v.setClickable(false);
//
//            String currentUserName = currentUser.getUsername().toString();
//            String XP_increase_name = new String();
//
//            //Increase XP
//            //Check for correctness
////
//            RelativeLayout parentRow = (RelativeLayout) v.getParent();
//            for (int i = 0; i < parentRow.getChildCount(); i++) {
//                View childView = parentRow.getChildAt(i);
//                if (childView instanceof TextView) {
//                    if (childView.getId() == R.id.view_answers_row_askerer_name) {
//                        TextView textview = (TextView) childView;
////                            System.out.println(textview.getText().toString().trim());
//                        XP_increase_name = textview.getText().toString();
////Toast.makeText(v.getContext(),XP_increase_name,Toast.LENGTH_SHORT).show();
////                        System.out.print("LOOOOOOOOOOOOOOOOOOOK" + XP_increase_name);
////                answerInText.put("question1", questionCorresponding);
//                    }
//                }
//
//            }
//
//            ParseQuery<ParseObject> query = new ParseQuery("_User");
//            query.whereEqualTo("username",XP_increase_name);
//            query.findInBackground(new FindCallback<ParseObject>() {
//                public void done(List<ParseObject> scoreList, ParseException e) {
//                ParseObject ob= scoreList.get(0);
//                    String abc = ob.get("rating_total").toString();
//                    Toast.makeText(v.getContext(),abc,Toast.LENGTH_SHORT).show();
//
//                ob.increment("rating_total");
//                ob.saveInBackground();
//
//                    String abcn = ob.get("rating_total").toString();
//                    Toast.makeText(v.getContext(),abcn,Toast.LENGTH_SHORT).show();
//                }
//
//
//          });
////            ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
//
//// Retrieve the object by id
//
////            query.getInBackground(userID, new GetCallback<ParseObject>() {
////                public void done(ParseObject userInstance, ParseException e) {
////                    if (e == null) {
////                        // Now let's update it with some new data. In this case, only cheatMode and score
////                        // will get sent to the Parse Cloud. playerName hasn't changed.
////
////                    }
////
////
////
////        }
//        }
//    }

