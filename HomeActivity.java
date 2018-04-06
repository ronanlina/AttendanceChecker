package com.example.ronanlina.attendancechecker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeActivity extends AppCompatActivity {


    private String mTeacherId;
    private String mEmail;
    private TeacherAccount ta;
    public static final String TEACHER_PREFS = "TeacherPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button scheduleViewButton = (Button) findViewById(R.id.btnViewSched);
        Button attendanceActButton = (Button) findViewById(R.id.attendanceActButton);

        SharedPreferences prefs = getSharedPreferences(MainActivity.ATTENDANCE_PREFS, MODE_PRIVATE);
        mEmail = prefs.getString(MainActivity.EMAIL_KEY,null);

        String tID = GetID();
        prefs.edit().putString(TEACHER_PREFS,tID).apply();

        scheduleViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this,SchedList.class); // 1st param current activity/java file, 2nd param target activity
                myIntent.putExtra("email",mEmail);
                startActivity(myIntent);
            }
        });

        attendanceActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this,AttendanceActivity.class);
                myIntent.putExtra("email",mEmail);
                startActivity(myIntent);
            }
        });



    }

    public String GetID()
    {
        //Retrieving the Teacher Id with the current Logged in email

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("teacheraccount");
        final String tID;

        Query query = db.orderByChild("Email").equalTo(mEmail);

        /*query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ta = dataSnapshot.getValue(TeacherAccount.class);
                mTeacherId = ta.getTeacherId();
                Log.d("teacherID2",mTeacherId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
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

        return mTeacherId;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something

            AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            alert.setTitle("Signing-Out");
            alert.setCancelable(true);
            alert.setMessage("You are about to sign-out, Proceed ?");
            alert.setPositiveButton("Sign-Out", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            alert.show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
