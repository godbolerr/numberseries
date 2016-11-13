package com.series.numberseries;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.series.numberseries.util.Answer;
import com.series.numberseries.util.AppxUtil;
import com.series.numberseries.util.InteractionTO;
import com.series.numberseries.util.SeriesSessionTO;
import com.series.numberseries.util.SeriesTO;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SeriesMessages";
    public int questionCount = 5;


    TextView seriesValueTextView = null;
    TextView statusIndicatorTextView = null;
    TextView seriesInstructionTextView = null;

    View.OnClickListener answerListner = null;
    Spinner spinnerLevel = null;
    MenuItem item = null;
    MenuItem itemLocale = null;

    Button option1Btn = null;
    Button option2Btn = null;
    Button option3Btn = null;
    Button option4Btn = null;

    SharedPreferences sharedPrefs = null;

    protected boolean doubleBackToExitPressedOnce;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        loadLocale();

//        String sQuestionCount = sharedPrefs.getString("questionCount", null);
//
//        if ( sQuestionCount != null ){
//            questionCount = Integer.parseInt(sQuestionCount);
//        }
//
//

        initiateSession();


         answerListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Button button = (Button) v;
                    Answer answer = new Answer(button.getText().toString());
                    SeriesSessionTO sessionTO = AppxUtil.thisSession;
                    sessionTO.setInput(button.getText().toString());
                    InteractionTO ito =  sessionTO.getInteractionTos().get(AppxUtil.currentInteractionCount);
                    ito.setInput(button.getText().toString());
                   // System.out.println(ito.getSto().getSeries() +": with answer " + button.getText().toString());
                     AppxUtil.currentInteractionCount++;
                    if ( AppxUtil.currentInteractionCount < sessionTO.getInteractionTos().size() ) {
                        ito =  sessionTO.getInteractionTos().get(AppxUtil.currentInteractionCount);
                        renderQuestion(ito,this);
                    }else {
                      Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);

                    }
            }
        };


        option1Btn.setOnClickListener(answerListner);
        option2Btn.setOnClickListener(answerListner);
        option3Btn.setOnClickListener(answerListner);
        option4Btn.setOnClickListener(answerListner);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.levelSpinner);

         spinnerLevel = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this,
                R.array.quiz_levels, android.R.layout.simple_spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLevel.setAdapter(sAdapter);
        String sQLevel =  sharedPrefs.getString("questionLevel",null);

        if ( sQLevel != null ) {
            if (sQLevel.equals("one")){
                spinnerLevel.setSelection(0);
            }
            if (sQLevel.equals("two")){
                spinnerLevel.setSelection(1);
            }
            if (sQLevel.equals("three")){
                spinnerLevel.setSelection(2);
            }
        } else {
            spinnerLevel.setSelection(0);
        }

        // Langauage specific code.

        itemLocale = menu.findItem(R.id.langSpinner);

        Spinner spinnerLocale = (Spinner) MenuItemCompat.getActionView(itemLocale);

        ArrayAdapter<CharSequence> lAdapter = ArrayAdapter.createFromResource(this,
                R.array.lang_list_item_array, android.R.layout.simple_spinner_item);
        lAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocale.setAdapter(lAdapter);

        String sCurrentLangauge =  sharedPrefs.getString("Language",null);

//        System.out.println("Current language is " + sCurrentLangauge);

        if (sCurrentLangauge != null ) {

            if ( "en".equals(sCurrentLangauge)){
                spinnerLocale.setSelection(0);
            }
            if ( "mr".equals(sCurrentLangauge)){
                spinnerLocale.setSelection(1);
            }
            if ( "hi".equals(sCurrentLangauge)){
                spinnerLocale.setSelection(2);
            }

        }


        spinnerLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Object item = parent.getItemAtPosition(position);

                String[] langCodeArray = getResources().getStringArray(R.array.lang_list_item_array_value);

                String thisLang = langCodeArray[position];

               // System.out.println("Item selected : " + thisLang );

                String sCurrentLangauge =  sharedPrefs.getString("Language","en");

                if ( thisLang != null && thisLang.equals(sCurrentLangauge)) {
                     //Toast.makeText(parent.getContext(), "Current Language : " + thisLang , Toast.LENGTH_SHORT).show();
                } else {
                    if ( thisLang != null ) {
                        //System.out.println("Setting language to " + thisLang);
                        changeLang(thisLang);
//                        Intent i = new Intent(parent.getContext(), MainActivity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
                            initiateSession();
                     }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });



        spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String[] levelCodeArray = getResources().getStringArray(R.array.quiz_levels_value);

                String currentLevel = levelCodeArray[position];

                String sQLevel =  sharedPrefs.getString("questionLevel",null);

               // System.out.println("Current level is " + currentLevel + " and sQLevel is " + sQLevel + " postiion is " + position + ":" + levelCodeArray);

                if ( currentLevel != null ) {


                    if ( currentLevel.equals(sQLevel)) {
                        //Toast.makeText(parent.getContext(), "Current Level : " + currentLevel , Toast.LENGTH_SHORT).show();
                    } else {

                        saveLevel(currentLevel);
//                        Toast.makeText(parent.getContext(), "Level  : " + currentLevel , Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(parent.getContext(), MainActivity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
                        initiateSession();

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });



        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent i = new Intent(this, SettingsActivity.class);
//            startActivity(i);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    public void renderQuestion( InteractionTO ito, View.OnClickListener listener ) {

        SeriesSessionTO sessionTO = AppxUtil.thisSession;
        AppxUtil.thisInteraction = ito;

        String question =  getResources().getString(R.string.question);

        statusIndicatorTextView.setText(question + "  : " + ( AppxUtil.currentInteractionCount + 1 ) + " / " + sessionTO.getInteractionTos().size());
        SeriesTO sto = ito.getSto();
        String seriesText = sto.getSeries();
        seriesText = seriesText.replace(","," ");
        seriesText = seriesText.replace(" ","&nbsp;");
        seriesText = seriesText.replace("?","<strong><font size=\"3\" color=\"red\">?</strong></font>");


        Spanned sp = Html.fromHtml( seriesText );

        seriesValueTextView.setText(sp);

        option1Btn.setText(sto.getOptions().get(0));
        option2Btn.setText(sto.getOptions().get(1));
        option3Btn.setText(sto.getOptions().get(2));
        option4Btn.setText(sto.getOptions().get(3));



        ito.start();

    }

    //// TODO: 9/30/2016
    @Override
    protected void onResume() {
        super.onResume();

    }

    //// TODO: 9/30/2016
    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);


    }


    public void initViews(){

        seriesValueTextView =  (TextView) findViewById(R.id.seriesValueTextView);

        option1Btn = (Button) findViewById(R.id.option1Btn);;
        option2Btn = (Button) findViewById(R.id.option2Btn);
        option3Btn = (Button) findViewById(R.id.option3Btn);
        option4Btn = (Button) findViewById(R.id.option4Btn);
        statusIndicatorTextView = (TextView) findViewById(R.id.statusIndicatorTextView);

    }

    public int getQuestionLevel(){

        String sQLevel =  sharedPrefs.getString("questionLevel","one");

        if ( "one".equals(sQLevel)){
            sQLevel= "1";
        }
        else if ( "two".equals(sQLevel)){
            sQLevel= "2";
        }
        else if ( "three".equals(sQLevel)){
            sQLevel= "3";
        } else {
            sQLevel= "1";
        }

        return Integer.parseInt(sQLevel);
    }


    private void updateTexts()
    {
        seriesInstructionTextView = (TextView) findViewById(R.id.seriesInstructionTextView);;
        seriesInstructionTextView.setText(R.string.qString);
        getQuestionLevel();

        // Update dropdowns in the menu when something changes.






    }

    public void loadLocale()
    {
        String langPref = "Language";
        String language = sharedPrefs.getString(langPref, "");
        changeLang(language);
    }

    public void saveLocale(String lang)
    {
        String langPref = "Language";
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void saveLevel(String level)
    {
        String levelPref = "questionLevel";
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(levelPref, level);
        editor.commit();
    }


    public void changeLang(String lang)
    {
        if (lang.equalsIgnoreCase(""))
            return;

        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
    }

    public void initiateSession(){

        updateTexts();

        int qLevel = getQuestionLevel();

        SeriesSessionTO sessionTo = AppxUtil.initiateSession(questionCount,qLevel);

        InteractionTO ito =  sessionTo.getInteractionTos().get(AppxUtil.currentInteractionCount);

        renderQuestion(ito,answerListner);
    }

}
