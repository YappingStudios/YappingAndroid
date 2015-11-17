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
    List<String> lifestyleStringDataFromParseques;
    List<String> lifestyleStringDataFromParseans;
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

        lifestyleStringDataFromParseques = new ArrayList<String>();
        lifestyleStringDataFromParseans = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowLifestyle");
        query.orderByAscending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            lifestyleStringDataFromParseques.add((String) country.get("mustknowlifestyleques"));
            lifestyleStringDataFromParseans.add((String) country.get("mustknowlifestyleans"));


        }

        lifestylelv = (ListView) rootView.findViewById(R.id.LifestylelistView);
        lifestylelv.setAdapter(new MyAdapterLifestyleMustKnow(lifestyleStringDataFromParseques,lifestyleStringDataFromParseans));
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
    List<String> LMKques;
    List<String> LMKans;
    MyAdapterLifestyleMustKnow(List<String> list,List<String> list1){
        this.LMKques=list;
        this.LMKans=list1;

    }


    @Override
    public int getCount() {
        return LMKques.size();
    }

    @Override
    public Object getItem(int position) {
        return LMKques.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.lifestyle_mustknow_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.lifestyle_mustknow_row_textview_ques);
        TextView textViewans = (TextView) convertView.findViewById(R.id.lifestyle_mustknow_row_textview_ans);
        textView.setText(LMKques.get(position));
        textViewans.setText(LMKans.get(position));
        return convertView;
    }
}