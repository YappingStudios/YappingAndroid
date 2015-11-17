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

// Instances of this class are fragments representing a single
// object in our collection.
public class FoodFragment extends Fragment implements AdapterView.OnItemClickListener {
    //public static final String ARG_OBJECT = "object";
    ListView foodlv;
    List<String> foodStringDataFromParseans;
    List<String> foodStringDataFromParseques;
    List<ParseObject> ob;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_food, container, false);

        Bundle args = getArguments();

//        parse data

        foodStringDataFromParseans = new ArrayList<String>();
        foodStringDataFromParseques = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                "MustKnowFood");
        query.orderByAscending("createdAt");
        try {
            ob = query.find();
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        for (ParseObject country : ob) {
            foodStringDataFromParseans.add((String) country.get("mustknowfoodans"));
            foodStringDataFromParseques.add((String) country.get("mustknowfoodques"));


        }


        return rootView;

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
//        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodlv = (ListView) view.findViewById(R.id.FoodListView);
        foodlv.setAdapter(new MyAdapterFoodMustKnow(foodStringDataFromParseques,foodStringDataFromParseans));
        foodlv.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class MyAdapterFoodMustKnow extends BaseAdapter {
    List<String> FMKques;
    List<String> FMKans;
    MyAdapterFoodMustKnow(List<String> list,List<String> list1){
        this.FMKques=list;
        this.FMKans=list1;
    }


    @Override
    public int getCount() {
        return FMKques.size();
    }

    @Override
    public Object getItem(int position) {
        return FMKques.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.food_mustknow_row, null);
        TextView textViewques = (TextView) convertView.findViewById(R.id.food_mustknow_row_textview_ques);
        textViewques.setText(FMKques.get(position));
        TextView textViewans = (TextView) convertView.findViewById(R.id.food_mustknow_row_textview_ans);
        textViewans.setText(FMKans.get(position));
        return convertView;
    }
}