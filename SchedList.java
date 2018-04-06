package com.example.ronanlina.attendancechecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SchedList extends AppCompatActivity {

    //member vars. ui objects
    public ListView SubSchedListView;
    public TextView schedList;
    public TextView tIDTextView;

    //data vars
    private String email;
    private String teacherid;
    private ArrayList<DataSnapshot> snapshot;

    //classes
    private TeacherAccount ta;
    private subjSchedAdapter mAdapter;

    //
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private SharedPreferences prefs;

    //listener

    private String x;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sched_list);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("teacheraccount");
        //ui objects
        SubSchedListView = (ListView) findViewById(R.id.SubSchedListView);
        schedList = (TextView) findViewById(R.id.schedTextView);
        tIDTextView = (TextView) findViewById(R.id.tIDTextView);

        //data vars
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        snapshot = new ArrayList<>();

        Query query = mDatabaseReference.orderByChild("Email")
                                        .equalTo(email)
                                        .limitToFirst(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot finalSnap :dataSnapshot.getChildren()){
                    ta = finalSnap.getValue(TeacherAccount.class);
                    x = ta.getTeacherId(); //the part where it says it returns null
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    mAdapter = new subjSchedAdapter(SchedList.this,db,x);
                    tIDTextView.setText(x);

                    SubSchedListView.setAdapter(mAdapter);

                    SubSchedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SubjectScheds ss = mAdapter.getItem(position);

                            Intent intent = new Intent(SchedList.this, activity_student_list.class);
                            intent.putExtra("section", ss.sectionname);
                            startActivity(intent);
                        }
                    });

                    Log.d("SUPOT96",x);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //fuck(teacherid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        email = getEmail();

        Log.d("SUPOT%%",email+" s");
    }

    public String getEmail()
    {
        Intent intent = getIntent();
        return intent.getStringExtra("email");
    }

    private void Display(String x){

        mAdapter = new subjSchedAdapter(this,mDatabaseReference,x);
        SubSchedListView.setAdapter(mAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();

        // Setting the adapter

        //mAdapter = new subjSchedAdapter(this, mDatabaseReference,teacherid);



        /*mSchedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                SubjectScheds infoPass = mAdapter.getItem(position);
                String sectionTitle = infoPass.sectionname;

                Intent intent = new Intent(SubjectSchedules.this, activity_student_list.class);
                intent.putExtra("SECTION_TITLE", sectionTitle);
                startActivity(intent);
            }
        });*/

    }

    /*@Override
    public void onStop(){
        super.onStop();

        mAdapter.cleanup();
    }*/
}
