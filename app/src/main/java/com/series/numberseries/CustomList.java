package com.series.numberseries;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String> {
    private String[] series;
    private String[] answer;
    private String[] yourAnswer;
    private String[] explanation;
    private Integer[] images;
    private Activity context;

    public CustomList(Activity context, String[] series, String[] answer, String[] yourAnswer, String[] explanation, Integer[] images) {
        super(context, R.layout.activity_result_list_view, series);
        this.context = context;
        this.answer = answer;
        this.yourAnswer = yourAnswer;
        this.images = images;
        this.series = series;
        this.explanation = explanation;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.rowlayout, null, true);

        TextView seriesText = (TextView) listViewItem.findViewById(R.id.seriesText);
        TextView answerText = (TextView) listViewItem.findViewById(R.id.answerText);
        TextView yourAnswerText = (TextView) listViewItem.findViewById(R.id.yourAnswerText);
        TextView explanationText = (TextView) listViewItem.findViewById(R.id.explanationText);



        String ans = answer[position];
        String yrAns = yourAnswer[position];

        if ( ans.equalsIgnoreCase(yrAns)){
            yourAnswerText.setTextColor(Color.parseColor("#458B00"));
        } else {
            yourAnswerText.setTextColor(Color.RED);
        }

        if ( position % 2 == 0 ){
            listViewItem.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        String answerT =  context.getResources().getString(R.string.answer);
        String yrAnswer =   context.getResources().getString(R.string.yourAnswer);

        seriesText.setText(series[position]);
        answerText.setText(answerT + " : " + answer[position]);
        yourAnswerText.setText(yrAnswer + " : " +  yourAnswer[position]);
        explanationText.setText(explanation[position]);
        return  listViewItem;
    }
}