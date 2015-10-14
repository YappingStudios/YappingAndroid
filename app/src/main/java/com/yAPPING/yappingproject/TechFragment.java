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

public class TechFragment extends Fragment implements AdapterView.OnItemClickListener {
    //public static final String ARG_OBJECT = "object";
    ListView techlv;
    List<String> techStringDataFromParse;
    List<ParseObject> ob;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_tech, container, false);

        Bundle args = getArguments();

//        parse data

        techStringDataFromParse = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowTech");
        query.orderByDescending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            techStringDataFromParse.add((String) country.get("mustknowtech"));


        }

        techlv = (ListView) rootView.findViewById(R.id.techListView);
        techlv.setAdapter(new MyAdapterLifestyleMustKnow(techStringDataFromParse));
        techlv.setOnItemClickListener(this);
        return rootView;

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterTechMustKnow extends BaseAdapter {
    List<String> TMK;
    MyAdapterTechMustKnow(List<String> list){
        this.TMK=list;
    }


    @Override
    public int getCount() {
        return TMK.size();
    }

    @Override
    public Object getItem(int position) {
        return TMK.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.tech_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.tech_mustknow_row_textview);
        textView.setText(TMK.get(position));
        return convertView;
    }
}