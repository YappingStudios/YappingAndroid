package com.yAPPING.yappingproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by srikar on 10/9/15.
 */
public class FAQs extends Activity {


    ListView faqlv;
    String[] stringquesFAQ;
    String[] stringansFAQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        stringquesFAQ = getResources().getStringArray(R.array.FAQQues);
        stringansFAQ = getResources().getStringArray(R.array.FAQAns);
        faqlv = (ListView) findViewById(R.id.faqlistView);
        faqlv.setAdapter(new FAQAdapter(stringquesFAQ,stringansFAQ));


    }
}

class FAQAdapter extends BaseAdapter{
String[] FAQQues,FAQAns;
    FAQAdapter(String[] listQ,String[] listA){
        this.FAQQues=listQ;
        this.FAQAns=listA;
    }
    @Override
    public int getCount() {
        return FAQQues.length;
    }

    @Override
    public Object getItem(int position) {
        return FAQQues[position];
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
        textViewQ.setText(FAQQues[position]);
        TextView textViewA = (TextView) convertView.findViewById(R.id.faq_row_ans_textview);
        textViewA.setText(FAQAns[position]);
        return convertView;
    }
}