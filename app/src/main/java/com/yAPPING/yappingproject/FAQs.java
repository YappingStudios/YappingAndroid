package com.yAPPING.yappingproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
public class FAQs extends Activity {


    ListView faqlv;
    ArrayList<String> stringquesFAQ;
    ArrayList<String> stringansFAQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        faqlv = (ListView) findViewById(R.id.faqlistView);
        stringansFAQ = new ArrayList<String>();
        stringquesFAQ = new ArrayList<String>();
//        stringquesFAQ = getResources().getStringArray(R.array.FAQQues);
//        stringansFAQ = getResources().getStringArray(R.array.FAQAns);

        ParseQuery<ParseObject> qans = new ParseQuery<ParseObject>("FAQData");
        qans.orderByAscending("createdAt");
        qans.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
           for(int i=0;i<list.size();i++) {
//               Toast.makeText(getApplicationContext(),list.size()+"",Toast.LENGTH_SHORT).show();
               ParseObject ob = list.get(i);
               stringansFAQ.add(ob.getString("Answers").toString());
               stringquesFAQ.add(ob.getString("Questions").toString());
           }

        faqlv.setAdapter(new FAQAdapter(stringquesFAQ,stringansFAQ));
            }
        });



    }
}

class FAQAdapter extends BaseAdapter{
    ArrayList<String> FAQQues,FAQAns;
    FAQAdapter(ArrayList<String> listQ,ArrayList<String> listA){
        this.FAQQues=listQ;
        this.FAQAns=listA;
    }
    @Override
    public int getCount() {
        return FAQQues.size();
    }

    @Override
    public Object getItem(int position) {
        return FAQQues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.faq_row, null);
        TextView textViewQ = (TextView) convertView.findViewById(R.id.faq_row_ques_textview);
        textViewQ.setText(FAQQues.get(position));
        TextView textViewA = (TextView) convertView.findViewById(R.id.faq_row_ans_textview);
        textViewA.setText(FAQAns.get(position));
        return convertView;
    }
}