package com.dayakar.classroom.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.dayakar.classroom.Adapters.Syllabus_Adapter;
import com.dayakar.classroom.Data.DataContract;
import com.dayakar.classroom.Data.DatabaseHelper;
import com.dayakar.classroom.R;

import java.util.ArrayList;

public class Syllabus_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> units=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_);

        recyclerView= findViewById(R.id.syllabus_recyclerVew);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        for(int i=0;i<=5;i++){
         units.add("this is "+i+"st unit");}
        Syllabus_Adapter adapter=new Syllabus_Adapter(units,this);
        recyclerView.setAdapter(adapter);

    }
    private void reading_syllabus(){


        DatabaseHelper dr = new DatabaseHelper(this);
        SQLiteDatabase db = dr.getReadableDatabase();

        String selection = DataContract.SyllabusEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { "1" };


        Cursor cursor = db.query(
                DataContract.SyllabusEntry.TABLE_SYLLABUS,
                null,
                selection,
                selectionArgs,
                null,
                null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //reading room numbers
                int index1 = cursor.getColumnIndex(DataContract.SyllabusEntry.UNIT_1);
                int index2= cursor.getColumnIndex(DataContract.SyllabusEntry.UNIT_2);
                int index3 = cursor.getColumnIndex(DataContract.SyllabusEntry.UNIT_3);
                int index4 = cursor.getColumnIndex(DataContract.SyllabusEntry.UNIT_4);
                int index5 = cursor.getColumnIndex(DataContract.SyllabusEntry.UNIT_5);
                int index6 = cursor.getColumnIndex(DataContract.SyllabusEntry.REFERENCE);
                units.add(cursor.getString(index1));
                units.add(cursor.getString(index2));
                units.add(cursor.getString(index3));
                units.add(cursor.getString(index4));
                units.add(cursor.getString(index5));
                units.add(cursor.getString(index6));


                cursor.moveToNext();
            }
        }
        cursor.close();


        dr.close();
        //reading lecturer names
    }

}
