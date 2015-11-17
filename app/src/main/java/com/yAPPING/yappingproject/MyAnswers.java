package com.yAPPING.yappingproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
 * Created by srikar on 16/11/15.
 */
public class MyAnswers extends Activity {

    ListView myanswerslv;
    ParseUser currentUser = ParseUser.getCurrentUser();
    ArrayList<String> stringMyAnswers;
    ArrayList<String> stringMyAnswerQuestion;
    String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_answers);
        myanswerslv = (ListView) findViewById(R.id.myanswerslistView);

        stringMyAnswers = new ArrayList<String>();
        stringMyAnswerQuestion = new ArrayList<String>();
        currentUsername = currentUser.getUsername();

        ParseQuery<ParseObject> query= new ParseQuery<ParseObject>("AnswerInText");
        query.whereEqualTo("answerer_name", currentUsername);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
           for(int i=0;i<list.size();i++){
               ParseObject ob = list.get(i);
               stringMyAnswers.add(ob.getString("answers"));
               stringMyAnswerQuestion.add(ob.getString("question1"));
           }
                myanswerslv.setAdapter(new MyAnswersAdapter(stringMyAnswers,stringMyAnswerQuestion));
            }
        });




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent igoToHome = new Intent(this,Home.class);
        startActivity(igoToHome);
    }
}

class MyAnswersAdapter extends BaseAdapter implements View.OnClickListener{
    ArrayList<String> stringMyAnswers;
    ArrayList<String> stringMyAnswerQuestion;
    String answerabovedeletebutton;
    String questionabovedeletebutton;

    MyAnswersAdapter(ArrayList<String> l1, ArrayList<String> l2){
        this.stringMyAnswers=l1;
        this.stringMyAnswerQuestion=l2;
    }

    @Override
    public int getCount() {
        return stringMyAnswers.size();
    }

    @Override
    public Object getItem(int position) {
        return stringMyAnswers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.my_answers_row, null);
        TextView answerTV = (TextView) convertView.findViewById(R.id.myanswers_row_answer);
        TextView questionTV = (TextView) convertView.findViewById(R.id.myanswers_row_question);
        answerTV.setText(stringMyAnswers.get(position).toString());
        questionTV.setText(stringMyAnswerQuestion.get(position).toString());
        Button bDeleteAnswer = (Button) convertView.findViewById(R.id.myanswers_row_deletebutton);
        bDeleteAnswer.setOnClickListener(this);

       return convertView;
    }

    @Override
    public void onClick(final View v) {
        if(v.getId()==R.id.myanswers_row_deletebutton){
//Toast.makeText(v.getContext(),"clicked bitch",Toast.LENGTH_SHORT).show();

            RelativeLayout parentRow = (RelativeLayout) v.getParent();
            for(int k=0;k<parentRow.getChildCount();k++){
               View childView = parentRow.getChildAt(k);
                if(childView instanceof TextView){
                    if(childView.getId()==R.id.myanswers_row_answer){
                        answerabovedeletebutton = ((TextView) childView).getText().toString();
//                        Toast.makeText(v.getContext(),answerabovedeletebutton,Toast.LENGTH_SHORT).show();
                    }
                    else if(childView.getId()==R.id.myanswers_row_question){
                        questionabovedeletebutton = ((TextView) childView).getText().toString();
//                        Toast.makeText(v.getContext(),questionabovedeletebutton,Toast.LENGTH_SHORT).show();
                    }
                }

            }

            ParseQuery<ParseObject> q = new ParseQuery<ParseObject>("AnswerInText");
            q.whereEqualTo("answerer_name",ParseUser.getCurrentUser().getUsername());
            q.whereEqualTo("answers",answerabovedeletebutton);
            q.whereEqualTo("question1",questionabovedeletebutton);
            q.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    for(int i=0;i<list.size();i++){
//                        Toast.makeText(v.getContext(),list.size()+"",Toast.LENGTH_SHORT).show();
                        ParseObject ob = list.get(i);
                        ob.deleteInBackground();
                        Toast.makeText(v.getContext(), "Answer deleted", Toast.LENGTH_SHORT).show();
                        Intent irefresh  =new Intent(v.getContext(),MyAnswers.class);
                        v.getContext().startActivity(irefresh);

                    }
                }
            });
        }

    }
}
