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
    List<String> travelStringDataFromParse;
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

        travelStringDataFromParse = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowTravel");
        query.orderByDescending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            travelStringDataFromParse.add((String) country.get("mustknowtravel"));


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
        travellv.setAdapter(new MyAdapterTravelMustKnow(travelStringDataFromParse));
        travellv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterTravelMustKnow extends BaseAdapter {
    List<String> TMK;
    MyAdapterTravelMustKnow(List<String> list){
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
        convertView = inflater.inflate(R.layout.travel_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.travel_mustknow_row_textview);
        textView.setText(TMK.get(position));
        return convertView;
    }
}