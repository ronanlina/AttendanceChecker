package com.example.ronanlina.attendancechecker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class StudentListAdapter extends BaseAdapter{

    private Activity mActivity;
    private CollectionReference mCollectionReference;
    private FirebaseFirestore db;
    private String mSection;
    private ArrayList<StudentList> mSnapshotList;
    private Query query;
    private StudentList mStudentList;
    private DocumentReference docRef;

    public StudentListAdapter(Activity activity, String section){

        mActivity = activity;
        mSection = section;
        mCollectionReference = db.getInstance().collection("studentsmobile");
        //mCollectionReference.add
        //mDatabaseReference.addChildEventListener(mListener);
        mStudentList = new StudentList();
        mSnapshotList = new ArrayList<>();

        query = mCollectionReference.whereEqualTo("Section",mSection);

        queryShit(query);


    }

    public void queryShit(Query query)
    {
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                mStudentList = (StudentList) document.getData();
                                mSnapshotList.add(mStudentList);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

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
    public StudentList getItem(int position) {

        return mSnapshotList.get(position);
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

        final StudentList studentlist = getItem(position);
        final StudentListAdapter.ViewHolder holder = (StudentListAdapter.ViewHolder) convertView.getTag();

        String studname = studentlist.getName();

        holder.studentName.setText(studname);

        return convertView;

    }

}
