package com.dayakar.classroom.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.dayakar.classroom.Adapters.Schedule_list_adapter;
import com.dayakar.classroom.Data.DataContract;
import com.dayakar.classroom.Data.DatabaseHelper;
import com.dayakar.classroom.R;

import java.util.ArrayList;

public class Schedule_Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> arrayList=new ArrayList<String>();
    private String day_value;
    private Schedule_list_adapter adapter;
    private ArrayList<Integer> foreground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Intent intent=getIntent();
        day_value=intent.getStringExtra("feature");
        setUp_UI();
        setUpHomeRecyclerView();
       if(day_value.equals("day")){
           readingWeekData();

           adapter=new Schedule_list_adapter(this,arrayList,foreground);

           mRecyclerView.setAdapter(adapter);
           adapter.activityName("daily");
       }else{
           readingSubData();

           adapter=new Schedule_list_adapter(this,arrayList,foreground);

           mRecyclerView.setAdapter(adapter);
       }

       }
   private void setUpHomeRecyclerView(){
       mRecyclerView= findViewById(R.id.schd_recyclerVew);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        foreground=new ArrayList<>();

        foreground.add(R.drawable.gradient_back);
        foreground.add(R.drawable.gradient_back1);
        foreground.add(
                R.drawable.gradient_back6);
        foreground.add(
                R.drawable.gradient_back2);
        foreground.add(
                R.drawable.gradient_back3);
        foreground.add(
                R.drawable.gradient_back4);
        foreground.add(
                R.drawable.gradient_back5);
        foreground.add(R.drawable.gradient_back);





    }


    private void setUp_UI(){
        mRecyclerView= findViewById(R.id.schd_recyclerVew);
        linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
    private void readingWeekData() {

        DatabaseHelper dr = new DatabaseHelper(this);
        SQLiteDatabase db = dr.getReadableDatabase();
        String[] projection = {
                DataContract.WeekEntry.COLUMN_DAY


        };
        String selection = DataContract.WeekEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { "1" };


        Cursor cursor = db.query(
                DataContract.WeekEntry.TABLE_WEEK,
                projection,
                null,
                null,
                null,
                null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int index = cursor.getColumnIndex(DataContract.WeekEntry.COLUMN_DAY);

                arrayList.add(cursor.getString(index));


                cursor.moveToNext();
            }
        }
        cursor.close();

        dr.close();


    }
    private void readingSubData() {

        DatabaseHelper dr = new DatabaseHelper(this);
        SQLiteDatabase db = dr.getReadableDatabase();
        String[] projection = {
                DataContract.SyllabusEntry.SUBJECT


        };
        Cursor cursor = db.query(
                DataContract.SyllabusEntry.TABLE_SYLLABUS,
                projection,
                null,
                null,
                null,
                null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int index = cursor.getColumnIndex(DataContract.SyllabusEntry.SUBJECT);

                arrayList.add(cursor.getString(index));


                cursor.moveToNext();
            }
        }
        cursor.close();

        dr.close();


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

