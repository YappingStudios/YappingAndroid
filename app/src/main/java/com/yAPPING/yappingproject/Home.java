
package com.yAPPING.yappingproject;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.support.v4.app.FragmentActivity;


public class Home extends FragmentActivity implements OnItemClickListener, OnClickListener {
    List<String> stringDataFromParse;
    boolean isOpen=false;
    EditText question;
    Button ask;
    Button setPreferencesbeforeasking;
    ListView lvcontent;
    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle drawerlistener;
    private ListView drawerlist;
    private String userAnswer = "";
    ParseObject questionAsked = new ParseObject("Questions");
    ParseObject questionAsked2 = new ParseObject("Questions2");
    //    ParseObject answerInText = new ParseObject("AnswerInText");
    ParseUser currentUser = ParseUser.getCurrentUser();
    Dialog_interests dialog_interests;
    List<ParseObject> ob;
    ArrayList questioncategorieschosen = new ArrayList();
    String[] ndstring = null;
    private String questionObjectId;
    private String questionAtPosition;
    Button bAIN;
     List<Integer> userPreferences;
     List<Integer> questioncategories;
    private List<String> askerNamesFromParse;
    private ProgressDialog progressDialog;
    public MyAdapter ad;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quit Yapping")
                .setMessage("Do you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Stop the activity
                        //System.exit(0);
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

        System.out.println("Back Pressed");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void animateText(View v){
        if(!isOpen){
            ObjectAnimator animation = ObjectAnimator.ofInt(question,"lines",1,6);
            animation.setDuration(100).start();
            isOpen=true;

            ask.setVisibility(View.VISIBLE);
            setPreferencesbeforeasking.setVisibility(View.VISIBLE);

        }
        else{
            ObjectAnimator animation = ObjectAnimator.ofInt(question,"lines",6,1);
            animation.setDuration(100).start();
            isOpen=false;

            ask.setVisibility(View.GONE);
            setPreferencesbeforeasking.setVisibility(View.GONE);
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addprofile:
                Intent iprofile = new Intent(this, ProfileActivity.class);
                startActivity(iprofile);

                break;
            case R.id.logout:
                Intent ilogin = new Intent(this, MainActivity.class);
                startActivity(ilogin);
                currentUser.logOut();
                break;
            case R.id.share:
                shareFunctionality();
                break;

        }
        return true;
    }

    private void shareFunctionality() {
        Resources resources = getResources();

        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
// Native email client doesn't currently support HTML, but it doesn't
// hurt to try in case they fix it
        emailIntent
                .putExtra(Intent.EXTRA_TEXT, Html.fromHtml(resources
                        .getString(R.string.share_email_native)));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                resources.getString(R.string.share_email_subject));
        emailIntent.setType("message/rfc822");

        PackageManager pm = getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        Intent openInChooser = Intent.createChooser(emailIntent,
                resources.getString(R.string.share_chooser_text));

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if (packageName.contains("twitter")
                    || packageName.contains("facebook")
                    || packageName.contains("mms")
                    || packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName,
                        ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if (packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT,
                            resources.getString(R.string.share_twitter));
                } else if (packageName.contains("facebook")) {
                    // Warning: Facebook IGNORES our text. They say
                    // "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the user voice."
                    // One workaround is to use the Facebook SDK to post, but
                    // that doesn't allow the user to choose how they want to
                    // share. We can also make a custom landing page, and the
                    // link
                    // will show the <meta content ="..."> text from that page
                    // with our link in Facebook.
                    intent.putExtra(Intent.EXTRA_TEXT,
                            resources.getString(R.string.share_facebook));
                } else if (packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT,
                            resources.getString(R.string.share_sms));
                } else if (packageName.contains("android.gm")) { // If Gmail
                    // shows up
                    // twice,
                    // try
                    // removing
                    // this
                    // else-if
                    // clause
                    // and the
                    // reference
                    // to
                    // "android.gm"
                    // above
                    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(resources
                            .getString(R.string.share_email_gmail)));
                    intent.putExtra(Intent.EXTRA_SUBJECT,
                            resources.getString(R.string.share_email_subject));
                    intent.setType("message/rfc822");
                }

                intentList.add(new LabeledIntent(intent, packageName, ri
                        .loadLabel(pm), ri.icon));
            }
        }

// convert intentList to array
        LabeledIntent[] extraIntents = intentList
                .toArray(new LabeledIntent[intentList.size()]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        startActivity(openInChooser);
    }

    /*public void answerInText(View v){
                Toast.makeText(this,"Hi",Toast.LENGTH_SHORT).show();
            }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        dialog_interests = new Dialog_interests(questioncategorieschosen);
        ndstring = getResources().getStringArray(R.array.nd);
        questionAsked2.saveInBackground();
        userPreferences = currentUser.getList("preferences");
        questioncategories=questionAsked.getList("categories");

        stringDataFromParse = new ArrayList<String>();
        askerNamesFromParse = new ArrayList<String>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Questions");

        for(int i=0;i<userPreferences.size();i++){
            query.whereEqualTo("categories",userPreferences.get(i));
            query.orderByDescending("createdAt");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            for (ParseObject country : ob) {
                stringDataFromParse.add((String) country.get("question"));
//                askerNamesFromParse.add((String)country.get("username"));


            }
        }
         stringDataFromParse =removeDuplicates(stringDataFromParse);
//        askerNamesFromParse = removeDuplicates(askerNamesFromParse);



//List<Integer> userPreferencesFromDatabase = (List<Integer>) currentUser.get("preferences");
//        Integer userPreferencesFromDatabaseSize = userPreferencesFromDatabase.size();
//        Toast.makeText(this,userPreferencesFromDatabaseSize,Toast.LENGTH_SHORT).show();



        question = (EditText) findViewById(R.id.etQuestion);
        ask = (Button) findViewById(R.id.bAsk);
        bAIN = (Button) findViewById(R.id.bViewAnswers);
        lvcontent = (ListView) findViewById(R.id.listView1);
        setPreferencesbeforeasking = (Button) findViewById(R.id.bsetpreferencesbeforeask);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerlist = (ListView) findViewById(R.id.drawerlist);
        drawerlistener = new ActionBarDrawerToggle(this, drawerlayout,
                R.drawable.final1, R.string.hello_world, R.string.hello_world) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(drawerView);
//                Toast.makeText(Home.this, "Drawer closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                super.onDrawerOpened(drawerView);
//                Toast.makeText(Home.this, "Drawer opened", Toast.LENGTH_LONG).show();
            }
        };
        drawerlayout.setDrawerListener(drawerlistener);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this, R.layout.drawer_view, ndstring);
        drawerlist.setAdapter(adapter);
        drawerlist.setOnItemClickListener(new DrawerItemListener());


        ActionBar ab = getActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.show();

        question.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {


                return false;
            }
        });
        //FOR DRAWER ICON IN ACTION BAR
        drawerlayout.post(new Runnable() {
            @Override
            public void run() {
                drawerlistener.syncState();
            }
        });


        ask.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    if (questioncategorieschosen.isEmpty()) {
                        Toast.makeText(v.getContext(), "Please choose at least one category", Toast.LENGTH_SHORT).show();
                    } else {
                        // do stuff with the user
                        String name = (String) currentUser.get("username");
                        //String email1 = (String) currentUser.get("email");
                        questionAsked.put("question", question.getText().toString());
                        questionAsked.put("username", name);
                        questionAsked.put("categories", questioncategorieschosen);
                        questionAsked.saveInBackground();
                        questionObjectId = questionAsked.getObjectId();
                        // TODO Auto-generated method stub
                        Toast.makeText(v.getContext(), "Your question has been posted.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                Home.class));
                    }

                } else {
                    // show the signup or profile screen
                    Toast.makeText(v.getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ad = new MyAdapter(stringDataFromParse,askerNamesFromParse);
        lvcontent.setAdapter(ad);
        lvcontent.setOnItemClickListener(new ContentItemListener());

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banswerintext:
                break;
        }
    }


    private class DrawerItemListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            progressDialog.show();
            System.out.println("ON ITEM CLICK");
            String clickedItem = ndstring[position].trim();
//            Toast.makeText(Home.this, clickedItem + " clicked", Toast.LENGTH_LONG).show();
            if (clickedItem.equalsIgnoreCase("Must Know")) {
                Intent iMustknow = new Intent(Home.this, MustKnow.class);
                startActivity(iMustknow);
                progressDialog.dismiss();
            } else if (clickedItem.equals("See All questions")) {
                Intent iSeeAllQuestions = new Intent(Home.this, SeeAllQuestions.class);
                startActivity(iSeeAllQuestions);
                progressDialog.dismiss();
            } else if (clickedItem.equals("My Questions")) {
                Intent iMyQuestions = new Intent(Home.this, MyQuestions.class);
                startActivity(iMyQuestions);
                progressDialog.dismiss();
            } else if (clickedItem.equals("FAQs")) {
                Intent iFAQs = new Intent(Home.this, FAQs.class);
                startActivity(iFAQs);
                progressDialog.dismiss();
            }
        }
    }

    public void onCheckBoxClick(View v){
        CheckBox cb=(CheckBox)v;
        cb.setEnabled(false);
        Toast.makeText(Home.this, "Thanks! Wait for the call.", Toast.LENGTH_LONG).show();
        cb.setTextColor(getResources().getColor(R.color.Gray));
    }
    private class ContentItemListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("ON ITEM CLICK");
            RelativeLayout relativeLayout = (RelativeLayout) parent.getChildAt(position);
            int childcount = relativeLayout.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View childview = relativeLayout.getChildAt(i);
                if (childview instanceof TextView) {
                    if (childview.getId() == R.id.questions) {
                        String question = ((TextView) childview).getText().toString().trim();
                        Toast.makeText(view.getContext(), question, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(view.getContext(),"IS it WOring",Toast.LENGTH_SHORT).show();
                    }
//                    if(childview.getId() == R.id.bViewAnswers){
//                        Intent iViewAnswers = new Intent(view.getContext(), ViewAnswers.class);
//                    iViewAnswers.putExtra("questionclicked",hygyb);
//                        startActivity(iViewAnswers);
//
//                    }

                }
            }
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position,
//                            long id) {
//        Toast.makeText(Home.this, "CLICKED", Toast.LENGTH_LONG).show();
//        /*switch (clickedItem){
//            case "Must Know":
//				Intent iMustknow = new Intent(this,MustKnow.class);
//				startActivity(iMustknow);
//				break;
//			case "See All questions":
//				Intent iSeeAllQuestions = new Intent(this,SeeAllQuestions.class);
//				startActivity(iSeeAllQuestions);
//
//				break;
//			case "My Questions":
//				Intent iMyQuestions = new Intent(this,MyQuestions.class);
//				startActivity(iMyQuestions);
//				break;
//			case "FAQs":
//				Intent iFAQs = new Intent(this,FAQs.class);
//				startActivity(iFAQs);
//				break;
//		}*/
//
//
//    }

//    public void clickedViewAnswers(View v) {
//        Intent iViewAnswers = new Intent(this, ViewAnswers.class);
//        iViewAnswers.putExtra("questionclicked", questionAtPosition);
//        startActivity(iViewAnswers);
//    }

    public void displayCategory(View v) {
        dialog_interests.show(getSupportFragmentManager(), "chooseinterests");


    }
    List<String> removeDuplicates(List<String> list) {

        // Store unique items in result.
        List<String> result = new ArrayList<String>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<String>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }

}

class MyAdapter extends BaseAdapter implements OnClickListener {

    ParseObject answerInText = new ParseObject("AnswerInText");
    ParseUser currentUser = ParseUser.getCurrentUser();
    List<String> stringDataFromParse;
    List<String> list;
    List<String> askerNames;
    List<String> answers;
    private String questionAtPosition;
    public String questionCorresponding;
//    private String askerNameForPassing;

    MyAdapter(List<String> list, List<String> askerNames) {
        this.list = list;


        answers=list;
        for(int i=0;i<list.size();i++){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("AnswerInText");
            query.whereEqualTo("question1", list.get(i));
            final int tempi=i;
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> questionswithsametext, ParseException e) {
                    if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                    ParseObject oneOfTheQuestionsWithSameText = questionswithsametext.get(0);
//                    answers=  oneOfTheQuestionsWithSameText.getString("answers");
                        int templ=answers.size();
                        for (int k = 0; k < questionswithsametext.size(); k++) {
                            ParseObject oneOfTheQuestionsWithSameText = questionswithsametext.get(k);
                            String oneOfTheQuestionsText = oneOfTheQuestionsWithSameText.getString("answers");
                            answers.set(tempi, oneOfTheQuestionsText);
                            //answers.add(oneOfTheQuestionsText);
                        }


//                    answers11.add("hi");
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });

        }


//        this.askerNames=askerNames;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //return questionsFromParse.length

        return list.size();
//		return questions.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
        //return questions[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        convertView = inflater.inflate(R.layout.home_row, null);
        TextView textView = (TextView) convertView.findViewById(R.id.questions);
        Button answerButton = (Button) convertView.findViewById(R.id.banswerintext);
        TextView askerNameTV = (TextView) convertView.findViewById(R.id.askername);
        EditText topAnswer = (EditText) convertView.findViewById(R.id.topAnswer);

        Button viewAnswers1 = (Button) convertView.findViewById(R.id.bViewAnswers);
        answerButton.setOnClickListener(this);
        viewAnswers1.setOnClickListener(this);
        Button bcallStart = (Button) convertView.findViewById(R.id.checkBox1);
        bcallStart.setOnClickListener(this);
        textView.setText(list.get(position));
        topAnswer.setText(answers.get(position));
//        askerNameTV.setText(askerNames.get(position));
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banswerintext:
                answerForQuestion(v);
                break;
            case R.id.bViewAnswers:
                ViewAnswersMethod(v);
                break;
            case R.id.checkBox1:
                callStart(v);
                break;
//
        }
    }

    private void ViewAnswersMethod(View view) {


        Intent iViewAnswers = new Intent(view.getContext(), ViewAnswers.class);

        RelativeLayout parentRow = (RelativeLayout) view.getParent();
//    System.out.println(parentRow);

        for (int i = 0; i < parentRow.getChildCount(); i++) {
            View childView = parentRow.getChildAt(i);
            if (childView instanceof TextView) {
                if (childView.getId() == R.id.questions) {
                    TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                    questionCorresponding = textview.getText().toString();
//                answerInText.put("question1", questionCorresponding);
                }
            }

        }

        iViewAnswers.putExtra("questionclicked", questionCorresponding);
//    Toast.makeText(view.getContext(),"hia",Toast.LENGTH_SHORT).show();
        view.getContext().startActivity(iViewAnswers);

    }

    private void answerForQuestion(final View view) {
        //        int position = (Integer)v.getTag();
//        Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
//        Toast.makeText(view.getContext(),"Test",Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Your answer");

// Set up the input
        final EditText input = new EditText(view.getContext());

//      WindowManager.LayoutParams lp = (WindowManager.LayoutParams) input.getLayoutParams();
//        input.setLayoutParams(lp);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHorizontallyScrolling(false);
        input.setVerticalScrollBarEnabled(true);

        input.setLines(5);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userAnswer = input.getText().toString();
//                Toast.makeText(view.getContext(), userAnswer, Toast.LENGTH_SHORT).show();
//                ParseObject answerInText = new ParseObject("AnswerInText");
                answerInText.put("answers", userAnswer);
                // answerInText.put("who is answering", currentUser);

//        answerInText.put("whose question",);

                RelativeLayout parentRow = (RelativeLayout) view.getParent();
                System.out.println(parentRow);

                for (int i = 0; i < parentRow.getChildCount(); i++) {
                    View childView = parentRow.getChildAt(i);
                    if (childView instanceof TextView) {
                        if (childView.getId() == R.id.questions) {
                            TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                            questionCorresponding = textview.getText().toString();
                            answerInText.put("question1", questionCorresponding);
                        }
                    }

                }
                answerInText.saveInBackground();
            }

        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
//        answerInText.saveInBackground();

        //store answer in Parse

//        answerInText.saveInBackground();

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
//        query.whereEqualTo("question",questionAtPosition);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> questionWithSameText, ParseException e) {
//                if (e == null) {
//                    Log.d("questioncompare", "Retrieved " + questionWithSameText.size() + " scores");
//                    Use this somehow to retrieve question ID
//                } else {
//                    Log.d("questioncompare", "Error: " + e.getMessage());
//                }
//            }
//        });
    }

    public void callStart(final View v) {
        String questCorr = new String();
//    String askerNameForPassing =new String();
        RelativeLayout parentRow = (RelativeLayout) v.getParent();
        for (int i = 0; i < parentRow.getChildCount(); i++) {
            View childView = parentRow.getChildAt(i);
            if (childView instanceof TextView) {
                if (childView.getId() == R.id.questions) {
                    TextView textview = (TextView) childView;
//                            System.out.println(textview.getText().toString().trim());
                    questCorr = textview.getText().toString();
//Toast.makeText(v.getContext(),askerNameForPassing,Toast.LENGTH_SHORT).show();
//                answerInText.put("question1", questionCorresponding);
                }
            }

        }
        ParseQuery<ParseObject> query = new ParseQuery("Questions");
        query.whereEqualTo("question", questCorr);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                String askerNameForPassing = scoreList.get(0).getString("username");
                Intent icallStart = new Intent(v.getContext(), CallStartActivity.class);
                icallStart.putExtra("askernameextra", askerNameForPassing);
                v.getContext().startActivity(icallStart);
            }


        });
    }
}

class MyAdapter1 extends BaseAdapter {
    private final String[] ndstring;
    Context context;


    public MyAdapter1(Context c, String[] items) {
        // TODO Auto-generated constructor stub
        this.context = c;
        ndstring = items;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ndstring.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ndstring[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_row1, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.updateObject((String) getItem(position));

        return convertView;

    }

    private class ViewHolder {

        TextView itemText;

        private ViewHolder(View view) {
            itemText = (TextView) view.findViewById(R.id.questionsdrawer);
        }


        public void updateObject(String item) {
            itemText.setText(item);
        }
    }


}
