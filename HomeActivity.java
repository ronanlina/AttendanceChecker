package com.example.ronanlina.attendancechecker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button scheduleViewButton = (Button) findViewById(R.id.btnViewSched);
        Button attendanceActButton = (Button) findViewById(R.id.attendanceActButton);

        scheduleViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this,SubjectSchedules.class); // 1st param current activity/java file, 2nd param target activity
                startActivity(myIntent);
            }
        });

        attendanceActButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeActivity.this,AttendanceActivity.class);
                startActivity(myIntent);
            }
        });
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
