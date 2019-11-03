package com.dayakar.classroom.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dayakar.classroom.R;

import org.w3c.dom.Text;

public class BunkManagerActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView percentage,summary,num_attend,num_total;
    private Button calculate;
    private EditText total_text,attended_text,require_perce_text;
    private CardView status_cardView;
    private RelativeLayout relativeLayout;

     int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bunk_manager);

        progressBar = findViewById(R.id.progressBar);
        percentage = findViewById(R.id.text_percentage);
        calculate = findViewById(R.id.calculate);
        total_text= findViewById(R.id.total_classes);
        attended_text= findViewById(R.id.attended_classes);
        require_perce_text= findViewById(R.id.require_percentage);
        summary= findViewById(R.id.summary);
        num_attend= findViewById(R.id.attended_num);
        num_total= findViewById(R.id.total_num);
        status_cardView= findViewById(R.id.status_card);
        relativeLayout= findViewById(R.id.inner_relative_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bunk Manager");



        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String button_text = calculate.getText().toString();
                if (button_text.equals("Calculate")) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int per = (int) get_percentage();

                                    progressBar.setProgress(per);
                                    percentage.setText(per + "%");
                                }
                            });

                        }


                    }).start();



                }
                if(button_text.equals("Clear")){
                    total_text.setText("");
                    attended_text.setText("");
                    require_perce_text.setText("75");
                    total_text.setVisibility(View.VISIBLE);
                    attended_text.setVisibility(View.VISIBLE);
                    require_perce_text.setVisibility(View.VISIBLE);
                    status_cardView.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.INVISIBLE);
                    progressBar.setProgress(0);
                    calculate.setText("Calculate");
                    percentage.setText("0%");



                }
            }
        });


    }
    private float get_percentage(){

        String cls_input=total_text.getText().toString().trim();
        String att_clases=attended_text.getText().toString().trim();
        String re_percent_text=require_perce_text.getText().toString().trim();

        if(cls_input.isEmpty()&& att_clases.isEmpty()&&re_percent_text.isEmpty()) {return 00;}


            try {
                float required_percent=Float.parseFloat(re_percent_text);
                float total_classes = Float.parseFloat(cls_input);
                float attended_classes = Float.parseFloat(att_clases);
                if (attended_classes>total_classes){
                    Toast.makeText(this, "Attended classes should not be more than Total clases", Toast.LENGTH_SHORT).show();
                    return 00; }

            float percentage = ((attended_classes / total_classes) * 100);


            System.out.println("percentage is " + percentage);
            float classe_for_75percent = ((required_percent * total_classes) / 100);

            if (percentage < 75) {


                float required_classes = classe_for_75percent - attended_classes;


                summary.setText("You need to attend " + (int)required_classes + " classes to get "+(int)required_percent+"% attendance");
                total_text.setVisibility(View.INVISIBLE);
                attended_text.setVisibility(View.INVISIBLE);
                require_perce_text.setVisibility(View.INVISIBLE);
                num_total.setText(String.valueOf((int)total_classes));
                num_total.setVisibility(View.VISIBLE);
                num_attend.setText(String.valueOf((int)attended_classes));
                num_attend.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                status_cardView.setVisibility(View.VISIBLE);
                calculate.setText("Clear");



            } else if (percentage >= 75) {
                float actual_classes = ((percentage * total_classes) / 100);

                float extra_classes = actual_classes - classe_for_75percent;

                summary.setText("You can Bunk up to " + (int)extra_classes + " classes \n Happy Bunking");
                total_text.setVisibility(View.INVISIBLE);
                attended_text.setVisibility(View.INVISIBLE);
                require_perce_text.setVisibility(View.INVISIBLE);
                num_total.setText(String.valueOf((int)total_classes));
                num_total.setVisibility(View.VISIBLE);
                num_attend.setText(String.valueOf((int)attended_classes));
                num_attend.setVisibility(View.VISIBLE);
                calculate.setText("Clear");
                relativeLayout.setVisibility(View.VISIBLE);
                status_cardView.setVisibility(View.VISIBLE);


            }
            return percentage;
            }catch (Exception e){

                Toast.makeText(this, "Enter correct input", Toast.LENGTH_SHORT).show();
                return 0;

            }








        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:{
                onBackPressed();
            }

        }

        return super.onOptionsItemSelected(item);
    }



    }


