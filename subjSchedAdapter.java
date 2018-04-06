package com.example.ronanlina.attendancechecker;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class subjSchedAdapter extends BaseAdapter{

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mTeacherId;
    private ArrayList<DataSnapshot> mSnapshotList;
    private Query query;
    private Context mContext;
    // child event listener

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
            Log.d("SUPOT%$#@!", "here");

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();
            Log.d("SUPOT%$#@!", "here");
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

    public subjSchedAdapter(Activity activity, DatabaseReference ref, String id){

        mActivity = activity;

        mDatabaseReference = ref.child("subjects");

        query = mDatabaseReference.orderByChild("teacherid").equalTo(id);

        query.addChildEventListener(mListener);

        mSnapshotList = new ArrayList<>();

    }

    //view holder

    static class ViewHolder{
        TextView subjectCodeAndName;
        TextView time;
        TextView sectionName;
        TextView teacherId;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public SubjectScheds getItem(int position) {

        DataSnapshot snapshot = mSnapshotList.get(position);

        return snapshot.getValue(SubjectScheds.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subject_sched_list, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.subjectCodeAndName = (TextView) convertView.findViewById(R.id.subjectCodeAndName);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.sectionName = (TextView) convertView.findViewById(R.id.sectionName);
            holder.teacherId = (TextView) convertView.findViewById(R.id.teacherid);
            holder.params = (LinearLayout.LayoutParams) holder.subjectCodeAndName.getLayoutParams();
            convertView.setTag(holder);
        }

        final SubjectScheds subjectsched = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        String subjcodeandname = "   " + subjectsched.getSubjectCode() + " - " + subjectsched.getSubjectName();
        String time = "   time : " + subjectsched.getTime();
        String sectionName = "   section : " + subjectsched.getSectionname();
        String teacherId = "   teacher : " + subjectsched.getTeacherid();

        holder.subjectCodeAndName.setText(subjcodeandname);
        holder.time.setText(time);
        holder.sectionName.setText(sectionName);
        holder.teacherId.setText(teacherId);

        return convertView;
    }

    public void cleanup(){

        mDatabaseReference.removeEventListener(mListener);

    }
}
