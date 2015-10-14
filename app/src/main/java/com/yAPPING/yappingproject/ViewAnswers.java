package com.yAPPING.yappingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikar on 14/9/15.
 */
public class ViewAnswers extends Activity{

    ListView valv;
//    List<String> answers11;
ArrayList<String> answers11;
//    valv.setAdapter(new )


//    ParseQuery<ParseObject> query = ParseQuery.getQuery("AnswerInText");
//    query.whereEqualTo("question",questionclicked);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_answers);
answers11=new ArrayList<String>();
        valv = (ListView) findViewById(R.id.viewanswerslistView);
        Intent intent = getIntent();

//        answers11.add("hai");
        String questionclicked = intent.getExtras().getString("questionclicked");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AnswerInText");
        query.whereEqualTo("question1", questionclicked);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> questionswithsametext, ParseException e) {
                if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                    ParseObject oneOfTheQuestionsWithSameText = questionswithsametext.get(0);
//                    answers=  oneOfTheQuestionsWithSameText.getString("answers");
                    for (int i = 0; i < questionswithsametext.size(); i++) {
                        ParseObject oneOfTheQuestionsWithSameText = questionswithsametext.get(i);
                        String oneOfTheQuestionsText = oneOfTheQuestionsWithSameText.getString("answers");
                        answers11.add(oneOfTheQuestionsText);
                    }
                    valv.setAdapter(new ViewAnswersAdapter(answers11));
//                    answers11.add("hi");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });






    }
}


class ViewAnswersAdapter extends BaseAdapter{
List<String> answers;
    ViewAnswersAdapter(List<String> list){
    this.answers=list;
}
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.view_answers_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.view_answers_row_textview);
        textView.setText(answers.get(position));
        return convertView;

    }
}
