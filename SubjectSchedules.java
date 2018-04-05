package com.example.ronanlina.attendancechecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SubjectSchedules extends AppCompatActivity {

    //member vars;
    private String mEmail;
    private ListView mSchedListView;
    private subjSchedAdapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private String mTeacherId;
    private TeacherAccount ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_schedules);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        SharedPreferences prefs = getSharedPreferences(MainActivity.ATTENDANCE_PREFS, MODE_PRIVATE);
        mEmail = prefs.getString(MainActivity.EMAIL_KEY,null);

        mSchedListView = (ListView) findViewById(R.id.schedListView);



    }

    public void getTeacherId(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("teacheraccount");
        final String[] id = new String[1];

        Query query = db.orderByChild("Email").equalTo(mEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TeacherAccount ta = dataSnapshot.getValue(TeacherAccount.class);
                mTeacherId = ta.getTeacherId();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showErrorDialog(databaseError.toString());
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        //Retrieving the Teacher Id with the current Logged in email

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("teacheraccount");
        final String tID;

        Query query = db.orderByChild("Email").equalTo(mEmail);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ta = dataSnapshot.getValue(TeacherAccount.class);
                mTeacherId = ta.getTeacherId().toString();
                Log.d("teacherID",mTeacherId);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Setting the adapter

        String tempId = mTeacherId;
        mAdapter = new subjSchedAdapter(this, mDatabaseReference,tempId);

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

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
