package com.dayakar.classroom.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dayakar.classroom.Adapters.Category_Adapter;
import com.dayakar.classroom.Adapters.MainRecycerView_Adapter;
import com.dayakar.classroom.Data.DataContract;
import com.dayakar.classroom.Data.DatabaseHelper;
import com.dayakar.classroom.Data.Facutly_Data_Class;
import com.dayakar.classroom.Data.Syllabus_Data;
import com.dayakar.classroom.Data.Week_Data_class;
import com.dayakar.classroom.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextVIew;
    private RecyclerView mRecyclerView;
    private RecyclerView category_RecyclerView;
    private List<String> subjects=new ArrayList<String>();
    private List<String> lecturers=new ArrayList<String>();
    private List<String> timings=new ArrayList<String>();
    private List<String> location=new ArrayList<String>();
    private ArrayList<Integer> icons=new ArrayList<>();
    private  ArrayList<String> titles=new ArrayList<>();
    private NavigationView mNavigationView;
    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle mToggle;

    TextView categoryText;
    private TextView mSundayText;

    private ImageButton timetable,syllabus,faculty,winnou,exams,e_books;
    private DatabaseReference mDatabaseReference,mDatabase_faculty_Reference,mSyllabusRef;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp_UI();


        readingRoomData();


        MainRecycerView_Adapter adapter=new MainRecycerView_Adapter(subjects,lecturers,timings,location,MainActivity.this);
        mRecyclerView.setAdapter(adapter);

       // loading_categories();

    }
    private void setUp_UI(){


        mRecyclerView= findViewById(R.id.main_RecyclerView);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        category_RecyclerView=(RecyclerView)findViewById(R.id.category_recyclerview);
//        category_RecyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        category_RecyclerView.setHasFixedSize(true);
        timetable= findViewById(R.id.time_table_icon);
        syllabus= findViewById(R.id.sylabus_icon);
        faculty= findViewById(R.id.faculty);
        winnou= findViewById(R.id.winnou);
        exams= findViewById(R.id.exams);
        e_books= findViewById(R.id.resources);
        mSundayText= findViewById(R.id.sunday_text);
        mdrawer = (DrawerLayout) findViewById(R.id.main_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.main_navigation);
        mNavigationView.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToggle = new ActionBarDrawerToggle(this, mdrawer, R.string.open, R.string.close);
        mdrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                                              @Override
                                                              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                                  switch (item.getItemId()) {
                                                                      case R.id.nav_settings: {
                                                                          Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                                                          item.setChecked(true);
                                                                          mdrawer.closeDrawer(GravityCompat.START);

                                                                          return true;

                                                                      }
                                                                      case R.id.nav_feedback: {
                                                                          Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                                                          item.setChecked(true);
                                                                          mdrawer.closeDrawer(GravityCompat.START);

                                                                          return true;

                                                                      }
                                                                      case R.id.nav_share: {
                                                                          Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                                                          item.setChecked(true);
                                                                          mdrawer.closeDrawer(GravityCompat.START);

                                                                          return true;

                                                                      }
                                                                      case R.id.nav_about: {
                                                                          Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                                                                          item.setChecked(true);
                                                                          mdrawer.closeDrawer(GravityCompat.START);

                                                                          return true;

                                                                      }

                                                                  }





                                                                  return false;
                                                              }
                                                          });



       timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this, Schedule_Activity.class);
                intent.putExtra("feature","day");
                startActivity(intent);
            }
        });
        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Schedule_Activity.class);
                intent.putExtra("feature","syllabus");
                startActivity(intent);
            }
        });
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Faculty", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,FacultyActivity.class));
            }
        });
        winnou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WnnouActivity.class));
            }
        });
        e_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SetUpActivity.class));
                Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        exams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,BunkManagerActivity.class));
            }
        });
    }

    private void load_data(){

        DatabaseHelper dr = new DatabaseHelper(
                MainActivity.this);
        final SQLiteDatabase data = dr.getWritableDatabase();

        mSyllabusRef = FirebaseDatabase.getInstance().getReference().child("colleges/MGIT/Branches/EEE/EEE_2/year/4-2/syllabus");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("colleges/MGIT/Branches/EEE/EEE_2/year/4-2/timetable");
        mDatabase_faculty_Reference = FirebaseDatabase.getInstance().getReference().child("colleges/MGIT/Branches/EEE/EEE_2/year/4-2/faculty");


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                     Week_Data_class dm=postSnapshot.getValue(Week_Data_class.class);
                    //  Log.d("MainActivty", "onDataChange: "+eventShopsht.getValue());



                      ContentValues values = new ContentValues();
                      values.put(DataContract.WeekEntry.COLUMN_ID,dm.get_id());
                      values.put(DataContract.WeekEntry.COLUMN_DAY, dm.getDay());
                      values.put(DataContract.WeekEntry.PERIOD_1, dm.getPeriod1());
                      values.put(DataContract.WeekEntry.PERIOD_2,dm.getPeriod2());
                      values.put(DataContract.WeekEntry.PERIOD_3, dm.getPeriod3());
                      values.put(DataContract.WeekEntry.PERIOD_4, dm.getPeriod4());
                      values.put(DataContract.WeekEntry.PERIOD_5, dm.getPeriod5());
                      values.put(DataContract.WeekEntry.PERIOD_6, dm.getPeriod6());
                      values.put(DataContract.WeekEntry.TIMINGS,dm.getTimings());
                      //loading room numbers
                  values.put(DataContract.WeekEntry.P1ROOM,dm.getP1r());
                  values.put(DataContract.WeekEntry.P2ROOM,dm.getP2r());
                  values.put(DataContract.WeekEntry.P3ROOM,dm.getP3r());
                  values.put(DataContract.WeekEntry.P4ROOM,dm.getP4r());
                  values.put(DataContract.WeekEntry.P5ROOM,dm.getP5r());
                  values.put(DataContract.WeekEntry.P6ROOM,dm.getP6r());
                  //loading lecturer names
                  values.put(DataContract.WeekEntry.P1Lecturer,dm.getP1l());
                  values.put(DataContract.WeekEntry.P2Lecturer,dm.getP2l());
                  values.put(DataContract.WeekEntry.P3Lecturer,dm.getP3l());
                  values.put(DataContract.WeekEntry.P4Lecturer,dm.getP4l());
                  values.put(DataContract.WeekEntry.P5Lecturer,dm.getP5l());
                  values.put(DataContract.WeekEntry.P6Lecturer,dm.getP6l());



                  try{

                      data.insertOrThrow(DataContract.WeekEntry.TABLE_WEEK, null, values);

                  }
                  catch (SQLiteConstraintException e){

                      Log.e("MAIN_ACTIVITY",e.getMessage());


              }
              }
              dr.close();

          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
              String error=  databaseError.getMessage();
              Log.e("Main_Activity",error);
              Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();


          }
      });

        mSyllabusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    Syllabus_Data dm=postSnapshot.getValue(Syllabus_Data.class);
                    DatabaseHelper db = new DatabaseHelper(
                            MainActivity.this);
                    final SQLiteDatabase data = db.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DataContract.SyllabusEntry.COLUMN_ID,dm.getId());
                    values.put(DataContract.SyllabusEntry.SUB_CODE, dm.getSub_code());
                    values.put(DataContract.SyllabusEntry.SUBJECT, dm.getSubject());
                    values.put(DataContract.SyllabusEntry.UNIT_1,dm.getUnit1());
                    values.put(DataContract.SyllabusEntry.UNIT_2, dm.getUnit2());
                    values.put(DataContract.SyllabusEntry.UNIT_3, dm.getUnit3());
                    values.put(DataContract.SyllabusEntry.UNIT_4, dm.getUnit4());
                    values.put(DataContract.SyllabusEntry.UNIT_5, dm.getUnit5());
                    values.put(DataContract.SyllabusEntry.REFERENCE, dm.getReference());
                    try{

                        data.insertOrThrow(DataContract.SyllabusEntry.TABLE_SYLLABUS, null, values);

                    }
                    catch (SQLiteConstraintException e){

                        Log.e("MAIN_ACTIVITY",e.getMessage());


                    }

                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase_faculty_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot facultySnopshot:dataSnapshot.getChildren()){
                    Facutly_Data_Class fd=facultySnopshot.getValue(Facutly_Data_Class.class);
                    DatabaseHelper dbf = new DatabaseHelper(
                            MainActivity.this);
                    final SQLiteDatabase data = dbf.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(DataContract.FacultyEntry.COLUMN_ID,fd.getId());
                    values.put(DataContract.FacultyEntry.NAME, fd.getName());
                    values.put(DataContract.FacultyEntry.DESIGNATION, fd.getDesignation());
                    values.put(DataContract.FacultyEntry.QUALIFICATION,fd.getQualification());
                    try{

                        data.insertOrThrow(DataContract.FacultyEntry.TABLE_Faculty,null,values);

                    }
                    catch (SQLiteConstraintException e){

                        Log.e("MAIN_ACTIVITY",e.getMessage());


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void readingRoomData() {

        DatabaseHelper dr = new DatabaseHelper(this);
        SQLiteDatabase db = dr.getReadableDatabase();

        String[] projection = {
                DataContract.WeekEntry.TIMINGS,
                DataContract.WeekEntry.COLUMN_ID


        };
        String selection = DataContract.WeekEntry.COLUMN_DAY + " = ?";
        String[] selectionArgs = {getToday() };


        Cursor cursor = db.query(
                DataContract.WeekEntry.TABLE_WEEK,
                null,
                selection,
                selectionArgs,
                null,
                null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //reading room numbers
                int index1 = cursor.getColumnIndex(DataContract.WeekEntry.P1ROOM);
                int index2= cursor.getColumnIndex(DataContract.WeekEntry.P2ROOM);
                int index3 = cursor.getColumnIndex(DataContract.WeekEntry.P3ROOM);
                int index4 = cursor.getColumnIndex(DataContract.WeekEntry.P4ROOM);
                int index5 = cursor.getColumnIndex(DataContract.WeekEntry.P5ROOM);
                int index6 = cursor.getColumnIndex(DataContract.WeekEntry.P6ROOM);
                //reading lecturer names
                int index11 = cursor.getColumnIndex(DataContract.WeekEntry.P1Lecturer);
                int index22= cursor.getColumnIndex(DataContract.WeekEntry.P2Lecturer);
                int index33 = cursor.getColumnIndex(DataContract.WeekEntry.P3Lecturer);
                int index44 = cursor.getColumnIndex(DataContract.WeekEntry.P4Lecturer);
                int index55 = cursor.getColumnIndex(DataContract.WeekEntry.P5Lecturer);
                int index66 = cursor.getColumnIndex(DataContract.WeekEntry.P6Lecturer);
                //reading clases
                int index111 = cursor.getColumnIndex(DataContract.WeekEntry.PERIOD_1);
                int index222= cursor.getColumnIndex(DataContract.WeekEntry.PERIOD_2);
                int index333 = cursor.getColumnIndex(DataContract.WeekEntry.PERIOD_3);
                int index444 = cursor.getColumnIndex(DataContract.WeekEntry.PERIOD_4);
                int index555 = cursor.getColumnIndex(DataContract.WeekEntry.PERIOD_5);
                int index666 = cursor.getColumnIndex(DataContract.WeekEntry.PERIOD_6);

                subjects.add(cursor.getString(index111));
                subjects.add(cursor.getString(index222));
                subjects.add(cursor.getString(index333));
                subjects.add(cursor.getString(index444));
                subjects.add(cursor.getString(index555));
                subjects.add(cursor.getString(index666));

                location.add(cursor.getString(index1));
                location.add(cursor.getString(index2));
                location.add(cursor.getString(index3));
                location.add(cursor.getString(index4));
                location.add(cursor.getString(index5));
                location.add(cursor.getString(index6));

                lecturers.add(cursor.getString(index11));
                lecturers.add(cursor.getString(index22));
                lecturers.add(cursor.getString(index33));
                lecturers.add(cursor.getString(index44));
                lecturers.add(cursor.getString(index55));
                lecturers.add(cursor.getString(index66));

                cursor.moveToNext();
            }
        }
        cursor.close();
// cursor for reading timings
        Cursor cursor2 = db.query(
                DataContract.WeekEntry.TABLE_WEEK, projection,
                null,
                null,
                null,
                null, null);


        if (cursor2.moveToFirst()) {
            while (!cursor2.isAfterLast()) {

                int time=cursor2.getColumnIndex(DataContract.WeekEntry.TIMINGS);

                timings.add(cursor2.getString(time));


                cursor2.moveToNext();
            }
        }
        cursor2.close();


        dr.close();


    }
    private String getToday(){
        String today="";

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        DateFormat format2 = new SimpleDateFormat("EEEE");
        try {
             today = format2.format(df.parse(formattedDate));
            System.out.println("today is "+today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("MainActivity",today);
        if(today.equals("Sunday")){

            mSundayText.setVisibility(View.VISIBLE);


        }
        return today;
    }
    private void loading_categories(){
        icons.add(R.drawable.timetable);
        icons.add(R.drawable.syllabus);
        icons.add(R.drawable.faculty);
        icons.add(R.drawable.winnou);
        icons.add(R.drawable.test);
        icons.add(R.drawable.learning);


        titles.add("Schedule");
        titles.add("Syllabus");
        titles.add("Faculty");
        titles.add("Winnou");
        titles.add("Bunk Manager");
        titles.add("E-Books");

        Category_Adapter adapter=new Category_Adapter(this,icons,titles);

          category_RecyclerView.setAdapter(adapter);
    }



}
