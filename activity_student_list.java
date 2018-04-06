package com.example.ronanlina.attendancechecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_student_list extends AppCompatActivity {


    private DatabaseReference mDatabaseReference;

    private ListView studentList;
    private TextView section;

    private String mEmail;
    private String mSectionTitle;

    private StudentListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        studentList = (ListView) findViewById(R.id.studentListView);
        section = (TextView) findViewById(R.id.classSectionView);
        Intent intent = getIntent();
        mSectionTitle = intent.getStringExtra("section");
        section.setText(mSectionTitle);
        String studSection = mSectionTitle;

        mAdapter = new StudentListAdapter(this, mDatabaseReference, mSectionTitle);
        studentList.setAdapter(mAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mSectionTitle = getSection();

    }

    public String getSection()
    {
        Intent intent = getIntent();
        return intent.getStringExtra("section");
    }

    @Override
    public void onStart(){
        super.onStart();


        //studentList.setAdapter(mAdapter);

    }

    @Override
    public void onStop(){
        super.onStop();

        mAdapter.cleanup();
    }

}
