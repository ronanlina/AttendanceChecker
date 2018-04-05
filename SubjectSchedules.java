package com.example.ronanlina.attendancechecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectSchedules extends AppCompatActivity {

    //member vars;
    private String mEmail;
    private ListView mSchedListView;
    private subjSchedAdapter mAdapter;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_schedules);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mSchedListView = (ListView) findViewById(R.id.schedListView);



    }

    @Override
    public void onStart(){
        super.onStart();

        mAdapter = new subjSchedAdapter(this, mDatabaseReference,"11092001");

        mSchedListView.setAdapter(mAdapter);

        mSchedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                SubjectScheds infoPass = mAdapter.getItem(position);
                String sectionTitle = infoPass.sectionname;

                Intent intent = new Intent(SubjectSchedules.this, activity_student_list.class);
                intent.putExtra("SECTION_TITLE", sectionTitle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStop(){
        super.onStop();

        mAdapter.cleanup();
    }

    private void getTeacherEmail(){

        SharedPreferences prefs = getSharedPreferences(MainActivity.ATTENDANCE_PREFS, MODE_PRIVATE);
        mEmail = prefs.getString(MainActivity.EMAIL_KEY,null);

    }
}
