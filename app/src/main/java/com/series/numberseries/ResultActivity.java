package com.series.numberseries;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.series.numberseries.util.AppxMessages;
import com.series.numberseries.util.AppxUtil;
import com.series.numberseries.util.InteractionTO;
import com.series.numberseries.util.SeriesSessionTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ResultActivity extends Activity {

    TextView seriesResultFailTextView = null;
    TextView seriesResultPassTextView = null;
    Button exitBtn = null;
    Button restartBtn = null;

    private ArrayAdapter<String> listAdapter ;
    ListView listView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list_view);
        seriesResultFailTextView =  (TextView) findViewById(R.id.seriesResultFailTextView);
        seriesResultPassTextView =  (TextView) findViewById(R.id.seriesResultPassTextView);
        exitBtn = (Button) findViewById(R.id.exitBtn);
        restartBtn = (Button) findViewById(R.id.restartBtn);

        List<String> seriesList = new ArrayList<String>();
        List<Integer> passfailIcon = new ArrayList<Integer>();
        List<String> answerList = new ArrayList<String>();
        List<String> yourAnswer = new ArrayList<String>();
        List<String> explanationList = new ArrayList<String>();

        SeriesSessionTO ssto = AppxUtil.sessionMap.get(AppxUtil.currentSession);
//        System.out.println("Current Session id " + ssto.getId());
        int passCount = 0;
        int failCount = 0;
        // Reset the count to zero
        AppxUtil.currentInteractionCount = 0 ;
        List<InteractionTO> interactionTOList = ssto.getInteractionTos();

            for (Iterator iterator = interactionTOList.iterator(); iterator.hasNext();) {
                InteractionTO ito = (InteractionTO) iterator.next();
                seriesList.add(ito.getSto().getSeries());
                answerList.add(ito.getSto().getAnswer());
                yourAnswer.add(ito.getInput());
                explanationList.add(AppxMessages.getString(ito.getSto().getExplanation(), ito.getSto().getStart(), ito.getSto().getIncrement()));
                if ( ito.isStatus()){

                    passCount++;
                } else {

                    failCount++;
                }

            }

        String[] seriesArray = seriesList.toArray(new String[0]);
        String[] exparray = explanationList.toArray(new String[0]);
        String[] ansArray = answerList.toArray(new String[0]);
        String[] yourAnswerArray = yourAnswer.toArray(new String[0]);

        Integer[] pfailArray = passfailIcon.toArray(new Integer[0]);

        CustomList customList = new CustomList(this, seriesArray,ansArray,yourAnswerArray,exparray,pfailArray);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.mainListView);

        listView.setAdapter(customList);

       String passResult =  getResources().getString(R.string.passResult);
       String failResult =  getResources().getString(R.string.failResult);



        seriesResultPassTextView.setText(passResult + " : " + passCount);
        seriesResultFailTextView.setText(failResult + " : " + failCount);

        restartBtn.setOnClickListener(restartListener);

            exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



                                            }

    View.OnClickListener restartListener = new View.OnClickListener() {
        boolean clicked = false;
        int numClicks = 0;

        @Override
        public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

        }
    };
}
