package com.yAPPING.yappingproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class EducationFragment extends Fragment implements AdapterView.OnItemClickListener {
    //public static final String ARG_OBJECT = "object";
    ListView edulv;
    List<String> eduStringDataFromParseques;
    List<String> eduStringDataFromParseans;
    List<ParseObject> ob;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_education, container, false);

        Bundle args = getArguments();

//        parse data

        eduStringDataFromParseques = new ArrayList<String>();
        eduStringDataFromParseans = new ArrayList<String>();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowEdu");
        query.orderByAscending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            eduStringDataFromParseques.add((String) country.get("mustknoweduques"));
            eduStringDataFromParseans.add((String) country.get("mustknoweduans"));

        }


        return rootView;

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edulv = (ListView) view.findViewById(R.id.EdulistView);
        edulv.setAdapter(new MyAdapterEduMustKnow(eduStringDataFromParseques,eduStringDataFromParseans));
        edulv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterEduMustKnow extends BaseAdapter{
    List<String> EMKques;
    List<String> EMKans;
    MyAdapterEduMustKnow(List<String> list,List<String> list1){
        this.EMKques=list;
        this.EMKans=list1;

    }


    @Override
    public int getCount() {
        return EMKques.size();
    }

    @Override
    public Object getItem(int position) {
        return EMKques.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.edu_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.edu_mustknow_row_textview_ques);
        TextView textViewans = (TextView) convertView.findViewById(R.id.edu_mustknow_row_textview_ans);
        textView.setText(EMKques.get(position));
        textViewans.setText(EMKans.get(position));
        return convertView;
    }
}