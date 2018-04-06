package com.example.ronanlina.attendancechecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //Firebase Object
    private FirebaseAuth mAuth;

    //Shared preferences constants
    public static final String ATTENDANCE_PREFS = "AttendancePrefs";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    //member vars
    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        importToFirebase();
        mEmail = (EditText) findViewById(R.id.txtTeacherId);
        mPassword = (EditText) findViewById(R.id.txtPassword);
        mLogin  = (Button) findViewById(R.id.btnLogin);

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    public void signInExistingUser(){
        attemptLogin();
    }

    //saving logged-in email in pass. to sharedPrefs

    private void saveEmailAndPassword(){   //local email save

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        SharedPreferences prefs = getSharedPreferences(ATTENDANCE_PREFS,0);
        prefs.edit().putString(EMAIL_KEY,email).apply();
        prefs.edit().putString(PASSWORD_KEY,password).apply();


    }

    private void attemptLogin(){

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (email.equals("") || password.equals("")) return;
        Toast.makeText(this,"Login in progress...", Toast.LENGTH_SHORT).show();


        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(!task.isSuccessful()){
                    showErrorDialog("There was a problem signing in.");
                }
                else{
                    saveEmailAndPassword();
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    intent.putExtra("email",mEmail.getText().toString());
                    finish();
                    startActivity(intent);
                }

            }
        });


    }


    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void importToFirebase(){
        // Retrieve data from JSON from desktop/web
        // (object)
        // then store them to firebase from here
        // Check if there are existing datas. either overwrite or ewan.
        //to be imported here. custom students.

            /*SubjectScheds subjs = new SubjectScheds("150519","MATH-7","8:00","7-sumting","09112001");
            SubjectScheds test = new SubjectScheds("1234567","ENG-6","10:00","6-sumting","20010911");
            SubjectScheds test1 = new SubjectScheds("7654321","FIL-8","11:00","8-sumting","11092001");
            Teachers teachers = new Teachers("09112001","connivinggod7@gmail.com","aaaaaaa");

            mDatabaseReference.child("subjects").push().setValue(subjs);
            mDatabaseReference.child("subjects").push().setValue(test);
            mDatabaseReference.child("subjects").push().setValue(test1);
            mDatabaseReference.child("teachers").push().setValue(teachers);

            AttendanceList batch1 = new AttendanceList("09112001","1505197","Ronan Lina","IV-Bornas");
            AttendanceList batch2 = new AttendanceList("09112001","1592031","Nathania Saniel","IV-Bornas");
            AttendanceList batch3 = new AttendanceList("09112001","1584756","Lowell Balebuena","IV-Bornas");
            AttendanceList batch4 = new AttendanceList("09112001","1529157","Jay Benamira","IV-Bornas");
            AttendanceList batch5 = new AttendanceList("09112001","1503697","Marvin Ong","IV-Bornas");

            mDatabaseReference.child("studentlist").push().setValue(batch1);
            mDatabaseReference.child("studentlist").push().setValue(batch2);
            mDatabaseReference.child("studentlist").push().setValue(batch3);
            mDatabaseReference.child("studentlist").push().setValue(batch4);
            mDatabaseReference.child("studentlist").push().setValue(batch5);*/
    }
}
