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
    List<String> eduStringDataFromParse;
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

        eduStringDataFromParse = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowEdu");
        query.orderByDescending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            eduStringDataFromParse.add((String) country.get("mustknowedu"));


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
        edulv.setAdapter(new MyAdapterEduMustKnow(eduStringDataFromParse));
        edulv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterEduMustKnow extends BaseAdapter{
    List<String> EMK;
    MyAdapterEduMustKnow(List<String> list){
        this.EMK=list;
    }


    @Override
    public int getCount() {
        return EMK.size();
    }

    @Override
    public Object getItem(int position) {
        return EMK.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.edu_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.edu_mustknow_row_textview);
        textView.setText(EMK.get(position));
        return convertView;
    }
}