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

public class LifestyleFragment extends Fragment implements AdapterView.OnItemClickListener {
    //public static final String ARG_OBJECT = "object";
    ListView lifestylelv;
    List<String> lifestyleStringDataFromParse;
    List<ParseObject> ob;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_lifestyle, container, false);

        Bundle args = getArguments();

//        parse data

        lifestyleStringDataFromParse = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowLifestyle");
        query.orderByDescending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            lifestyleStringDataFromParse.add((String) country.get("mustknowlifestyle"));


        }

        lifestylelv = (ListView) rootView.findViewById(R.id.LifestylelistView);
        lifestylelv.setAdapter(new MyAdapterLifestyleMustKnow(lifestyleStringDataFromParse));
        lifestylelv.setOnItemClickListener(this);
        return rootView;

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterLifestyleMustKnow extends BaseAdapter {
    List<String> LMK;
    MyAdapterLifestyleMustKnow(List<String> list){
        this.LMK=list;
    }


    @Override
    public int getCount() {
        return LMK.size();
    }

    @Override
    public Object getItem(int position) {
        return LMK.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.lifestyle_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.lifestyle_mustknow_row_textview);
        textView.setText(LMK.get(position));
        return convertView;
    }
}