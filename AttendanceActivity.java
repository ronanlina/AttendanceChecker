package com.example.ronanlina.attendancechecker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    //member vars
    private String mEmail;
    public ListView mAttendanceListView;
    private attendanceListAdapter mAdapter;
    private DatabaseReference mDatabaseReference;

    private String email;
    private ArrayList<DataSnapshot> mSnapshots;
    private String id;
    private TeacherAccount ta;
    private String[] spinItem;
    private SubjectScheds ss;

    public Button saveButton;
    public Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        getTeacherEmail();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mAttendanceListView = (ListView) findViewById(R.id.attendanceListView);
        Button saveButton = (Button) findViewById(R.id.saveAttendanceButton);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        mSnapshots = new ArrayList<>();

        Query query = mDatabaseReference.orderByChild("Email")
                .equalTo(email)
                .limitToFirst(1);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot finalSnap :dataSnapshot.getChildren()){

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("teacheraccounts");
                    ta = finalSnap.getValue(TeacherAccount.class);
                    id = ta.getTeacherId(); //the part where it says it returns null

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference.child("subjects").child("teacherid").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> sections = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("sectionname");
                    SubjectScheds subs = areaSnapshot.getValue(SubjectScheds.class);
                    String secName = subs.getSectionname();
                    sections.add(secName);
                }

                Spinner areaSpinner = (Spinner) findViewById(R.id.sectionSpinner2);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<>(AttendanceActivity.this, android.R.layout.simple_spinner_item, sections);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAdapter = new attendanceListAdapter(this,mDatabaseReference,"A",mContext);
        mAttendanceListView.setAdapter(mAdapter);

        saveButton = (Button) findViewById(R.id.saveAttendanceButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(AttendanceActivity.this,R.style.MyDialogTheme);
                alert.setTitle("Save Attendance");
                alert.setCancelable(true);
                alert.setMessage("You are about to save all students marked and identify there attendance as 'present'. Save attendance?");
                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int no =  mAttendanceListView.getAdapter().getCount();

                        for(int i = 0; i < no; i++)
                        {
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                            AttendanceList al = mAdapter.getItem(i);
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String date = df.format(c);

                            Attendance a = new Attendance(al.getSubjId(),al.getStudentId(),al.getName(),al.getSection(),date,"Absent");
                            mDatabaseReference.child("attendance").push().setValue(a);

                        }
                        mAttendanceListView.setAdapter(null);
                        Toast.makeText(getApplicationContext(), "Attendance Recorded!", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });

    }

    private void getTeacherEmail(){

        SharedPreferences prefs = getSharedPreferences(MainActivity.ATTENDANCE_PREFS, MODE_PRIVATE);
        mEmail = prefs.getString(MainActivity.EMAIL_KEY,null);

    }

}
