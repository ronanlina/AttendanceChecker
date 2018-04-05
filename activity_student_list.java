package com.example.ronanlina.attendancechecker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_student_list extends AppCompatActivity {


    private CollectionReference mCollectionReference;
    private FirebaseFirestore db;

    private ListView studentList;
    private TextView section;

    private String mEmail;
    private String mSectionTitle;

    private StudentListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        db = FirebaseFirestore.getInstance();
        studentList = (ListView) findViewById(R.id.studentListView);
        section = (TextView) findViewById(R.id.classSectionView);
        mSectionTitle = getIntent().getStringExtra("SECTION_TITLE");
        section.setText(mSectionTitle);

    }


    @Override
    public void onStart(){
        super.onStart();

        mAdapter = new StudentListAdapter(this, mSectionTitle);

        studentList.setAdapter(mAdapter);


    }

}
