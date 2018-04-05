package com.example.ronanlina.attendancechecker;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ronan Lina on 18/03/2018.
 */

public class attendanceListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mSectionId;
    private String mSubjectId;
    private ArrayList<DataSnapshot> mSnapshotList;

    // child event listener

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();

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
    };

    //constructor

    public attendanceListAdapter(Activity activity, DatabaseReference ref, String subjsecid, String subjid){

        mActivity = activity;
        mSectionId = subjsecid;
        mSubjectId = subjid;
        mDatabaseReference = ref.child("studentlist");
        mDatabaseReference.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();
    }

    //view holder

    static class ViewHolder{
        TextView subjId;
        TextView studentid;
        TextView name;
        TextView section;
        CheckBox checkLate;
        CheckBox checkExcuse;
        Button presentButton;
        Button absentButton;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public AttendanceList getItem(int position) {

        DataSnapshot snapshot = mSnapshotList.get(position);

        return snapshot.getValue(AttendanceList.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.attendance_check_list, parent, false);

            final attendanceListAdapter.ViewHolder holder = new attendanceListAdapter.ViewHolder();
            holder.subjId = (TextView) convertView.findViewById(R.id.subjId);
            holder.studentid = (TextView) convertView.findViewById(R.id.studentid);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.section = (TextView) convertView.findViewById(R.id.section);
            holder.checkLate = (CheckBox) convertView.findViewById(R.id.lateCheckbox);
            holder.checkExcuse = (CheckBox) convertView.findViewById(R.id.excusedCheckbox);
            holder.presentButton = (Button) convertView.findViewById(R.id.presentButton);
            holder.absentButton = (Button) convertView.findViewById(R.id.absentButton);
            holder.params = (LinearLayout.LayoutParams) holder.subjId.getLayoutParams();
            convertView.setTag(holder);
        }

        final AttendanceList attendancelist = getItem(position);
        final attendanceListAdapter.ViewHolder holder = (attendanceListAdapter.ViewHolder) convertView.getTag();

        final String subjid = attendancelist.getSubjId();
        final String studentid = attendancelist.getStudentId();
        final String studName = attendancelist.getName();
        final String section = attendancelist.getSection();

        holder.subjId.setText(subjid);
        holder.studentid.setText(studentid);
        holder.name.setText(studName);
        holder.section.setText(section);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //present

        holder.presentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c);

                String attendanceType;

                if(holder.checkLate.isChecked()){
                    attendanceType = "Late";
                }
                else {
                    attendanceType = "Present";
                }

                Attendance attendance = new Attendance(subjid, studentid, studName, section, date, attendanceType);
                mDatabaseReference.child("attendance").push().setValue(attendance);

                mSnapshotList.remove(position);
                notifyDataSetChanged();
                //convertView = null;
                Log.d("buttonTest","WORRRKs");
            }
        });

        //absent

        holder.absentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String date = df.format(c);

                String attendanceType;

                if(holder.checkExcuse.isChecked()){
                    attendanceType = "Excused";
                }
                else {
                    attendanceType = "Absent";
                }

                Attendance attendance = new Attendance(subjid, studentid, studName, section, date, attendanceType);
                mDatabaseReference.child("attendance").push().setValue(attendance);

                mSnapshotList.remove(position);
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    public void cleanup(){

        mDatabaseReference.removeEventListener(mListener);
    }
}