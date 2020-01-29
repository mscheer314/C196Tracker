package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssessmentDetails extends BaseActivity {

    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    private String assessmentName;
    private String assessmentDueDate;
    private String assessmentType;
    private TextView assessmentNameTextView;
    private TextView assessmentDueDateTextView;
    private TextView assessmentTypeTextView;
    private int courseId;
    private String courseStart;
    private String courseEnd;
    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentNameTextView = findViewById(R.id.assessment_details_title);
        assessmentDueDateTextView = findViewById(R.id.assessment_details_due_date);
        assessmentTypeTextView = findViewById(R.id.assessment_details_type);

        setDetails();
    }

    private void setDetails() {
        Intent intent = getIntent();
        assessmentName = intent.getStringExtra("assessmentName");
        assessmentDueDate = intent.getStringExtra("assessmentDueDate");
        assessmentType = intent.getStringExtra("assessmentType");
        assessmentId = intent.getIntExtra("assessmentId", 0);
        courseId = intent.getIntExtra("courseId", 0);
        courseStart = intent.getStringExtra("courseStart");
        courseEnd = intent.getStringExtra("courseEnd");

        assessmentNameTextView.setText(assessmentName);
        assessmentDueDateTextView.setText(assessmentDueDate);
        assessmentTypeTextView.setText(assessmentType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assessmentName = data.getStringExtra("assessmentName");
                assessmentDueDate = data.getStringExtra("assessmentDueDate");
                assessmentType = data.getStringExtra("assessmentType");

                assessmentNameTextView.setText(assessmentName);
                assessmentDueDateTextView.setText(assessmentDueDate);
                assessmentTypeTextView.setText(assessmentType);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.assessment_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        if (item.getItemId() == R.id.edit_assessment) {
            Intent intent = new Intent(AssessmentDetails.this, AddAssessmentDialog.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isNewAssessment", false);
            bundle.putInt("courseId", courseId);
            bundle.putInt("assessmentId", assessmentId);
            bundle.putString("courseStart", courseStart);
            bundle.putString("courseEnd", courseEnd);

            intent.putExtras(bundle);
            startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
        }
        if (item.getItemId() == R.id.add_assessment_notification) {
            Date assessmentDate = new Date();
            try {
                assessmentDate = formatter.parse(assessmentDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            scheduleNotification(getNotification("Assessment upcoming"), assessmentDate.getTime());
            Toast.makeText(this, R.string.notification_scheduled, Toast.LENGTH_LONG).show();
        }

        if (item.getItemId() == R.id.add_goal_alert) {

        }
        return super.onOptionsItemSelected(item);
    }
}
