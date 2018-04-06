package com.example.ronanlina.attendancechecker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter{

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private ArrayList<DataSnapshot> mSnapshotList;
    private String mSection;
    private Query query;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();

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


    public StudentListAdapter(Activity activity, DatabaseReference ref, String section){

        mActivity = activity;
        mSection = section;
        mDatabaseReference = ref.child("studentlist");

        query = mDatabaseReference.orderByChild("section").equalTo(section);

        query.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();

    }

    static class ViewHolder{
        TextView studentName;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.student_list, parent, false);

            final StudentListAdapter.ViewHolder holder = new StudentListAdapter.ViewHolder();
            holder.studentName = (TextView) convertView.findViewById(R.id.StudentName);
            holder.params = (LinearLayout.LayoutParams) holder.studentName.getLayoutParams();
            convertView.setTag(holder);
        }

        final AttendanceList studentlist = getItem(position);
        final StudentListAdapter.ViewHolder holder = (StudentListAdapter.ViewHolder) convertView.getTag();

        String studname = studentlist.getName();

        holder.studentName.setText(studname);

        return convertView;

    }

    public void cleanup(){

        mDatabaseReference.removeEventListener(mListener);

    }
}
