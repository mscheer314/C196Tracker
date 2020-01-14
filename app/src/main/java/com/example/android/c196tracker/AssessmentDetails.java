package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AssessmentDetails extends BaseActivity {

    private String assessmentName;
    private String assessmentDueDate;
    private TextView assessmentNameTextView;
    private TextView assessmentDueDateTextView;
    private Button addNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentNameTextView = findViewById(R.id.assessment_details_title);
        assessmentDueDateTextView = findViewById(R.id.assessment_details_due_date);
        addNotificationButton = findViewById(R.id.assessment_notification_button);

        Intent intent = getIntent();
        assessmentName = intent.getStringExtra("assessmentName");
        assessmentDueDate = intent.getStringExtra("assessmentDueDate");

        assessmentNameTextView.setText(assessmentName);
        assessmentDueDateTextView.setText(assessmentDueDate);

        addNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
