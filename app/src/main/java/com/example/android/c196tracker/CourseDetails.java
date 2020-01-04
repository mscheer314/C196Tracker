package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.UI.AddAssessmentDialog;
import com.example.android.c196tracker.UI.AssessmentAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallBack;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;
import com.example.android.c196tracker.ViewModel.CourseViewModel;

import java.util.List;

public class CourseDetails extends BaseActivity {

    private int courseId;
    private String courseNameString;
    private String courseStartString;
    private String courseEndString;
    private String courseStatusString;
    private String courseMentorNameString;
    private String courseMentorPhoneString;
    private String courseMentorEmailString;

    private Button addAssessmentButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseStatus;
    private TextView courseMentorName;
    private TextView courseMentorPhone;
    private TextView courseMentorEmail;
    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setCourseDetails();
        setRecyclerView();

        addAssessmentButton = findViewById(R.id.add_assessment);
        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        AddAssessmentDialog addAssessmentDialog = new AddAssessmentDialog(
                courseId, courseStartString, courseEndString);
        addAssessmentDialog.show(getSupportFragmentManager(), "add_assessment_dialog");
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.course_detail_assessment_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeToDeleteCallBack(assessmentAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssociatedAssessments(courseId).observe(this,
                new Observer<List<AssessmentEntity>>() {
                    @Override
                    public void onChanged(List<AssessmentEntity> assessmentEntities) {
                        assessmentAdapter.setAssessments(assessmentEntities);
                    }
                });
    }

    private void setCourseDetails() {
        courseName = findViewById(R.id.course_detail_title);
        courseStart = findViewById(R.id.course_detail_start);
        courseEnd = findViewById(R.id.course_detail_end);
        courseStatus = findViewById(R.id.course_detail_status);
        courseMentorName = findViewById(R.id.course_details_mentor_name);
        courseMentorPhone = findViewById(R.id.course_detail_phone);
        courseMentorEmail = findViewById(R.id.course_detail_email);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);
        courseNameString = intent.getStringExtra("courseName");
        courseStartString = intent.getStringExtra("courseStart");
        courseEndString = intent.getStringExtra("courseEnd");
        courseStatusString = intent.getStringExtra("courseStatus");
        courseMentorNameString = intent.getStringExtra("courseMentorName");
        courseMentorPhoneString = intent.getStringExtra("courseMentorPhone");
        courseMentorEmailString = intent.getStringExtra("courseMentorEmail");

        courseName.setText(courseNameString);
        courseStart.setText(courseStartString);
        courseEnd.setText(courseEndString);
        courseStatus.setText(courseStatusString);
        courseMentorName.setText(courseMentorNameString);
        courseMentorPhone.setText(courseMentorPhoneString);
        courseMentorEmail.setText(courseMentorEmailString);
    }
}
