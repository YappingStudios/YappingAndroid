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

public class TravelFragment extends Fragment implements AdapterView.OnItemClickListener {
    //public static final String ARG_OBJECT = "object";
    ListView travellv;
    List<String> travelStringDataFromParseques;
    List<String> travelStringDataFromParseans;
    List<ParseObject> ob;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_travel, container, false);

        Bundle args = getArguments();

//        parse data

        travelStringDataFromParseques = new ArrayList<String>();
        travelStringDataFromParseans = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowTravel");
        query.orderByAscending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            travelStringDataFromParseques.add((String) country.get("mustknowtravelques"));
            travelStringDataFromParseans.add((String) country.get("mustknowtravelans"));


        }


        return rootView;

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        travellv = (ListView) view.findViewById(R.id.TravelListView);
        travellv.setAdapter(new MyAdapterTravelMustKnow(travelStringDataFromParseques,travelStringDataFromParseans));
        travellv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterTravelMustKnow extends BaseAdapter {
    List<String> TMKques;
    List<String> TMKans;
    MyAdapterTravelMustKnow(List<String> list,List<String> list1){
        this.TMKques=list;
        this.TMKans=list1;

    }


    @Override
    public int getCount() {
        return TMKques.size();
    }

    @Override
    public Object getItem(int position) {
        return TMKques.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.travel_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.travel_mustknow_row_textview_ques);
        TextView textViewans = (TextView) convertView.findViewById(R.id.travel_mustknow_row_textview_ans);
        textView.setText(TMKques.get(position));
        textViewans.setText(TMKans.get(position));
        return convertView;
    }
}