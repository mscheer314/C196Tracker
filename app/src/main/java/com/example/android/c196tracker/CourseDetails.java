package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.UI.AssessmentAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallBack;
import com.example.android.c196tracker.ViewModel.CourseViewModel;

public class CourseDetails extends AppCompatActivity {

    private String courseNameString;
    private String courseStartString;
    private String courseEndString;
    private String courseStatusString;
    private String courseInstructorNameString;
    private String courseInstructorPhoneString;
    private String courseInstructorEmailString;

    private Button optionsButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseStatus;
    private TextView courseInstructorName;
    private TextView courseInstructorPhone;
    private TextView courseInstructorEmail;
    private CourseViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setRecyclerView();
        setCourseDetails();
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
    }

    private void setCourseDetails() {
        courseName = findViewById(R.id.course_detail_title);
        courseStart = findViewById(R.id.course_detail_start);
        courseEnd = findViewById(R.id.course_detail_end);
        courseStatus = findViewById(R.id.course_detail_status);
        courseInstructorName = findViewById(R.id.course_details_instructor);
        courseInstructorPhone = findViewById(R.id.course_detail_phone);
        courseInstructorEmail = findViewById(R.id.course_detail_email);

        Intent intent = getIntent();
        courseNameString = intent.getStringExtra("courseName");
        courseStartString = intent.getStringExtra("courseStart");
        courseEndString = intent.getStringExtra("courseEnd");
        courseStatusString = intent.getStringExtra("courseStatus");
        courseInstructorNameString = intent.getStringExtra("courseInstructorName");
        courseInstructorPhoneString = intent.getStringExtra("courseInstructorPhone");
        courseInstructorEmailString = intent.getStringExtra("courseInstructorEmail");

        courseName.setText(courseNameString);
        courseStart.setText(courseStartString);
        courseEnd.setText(courseEndString);
        courseStatus.setText(courseStatusString);
        courseInstructorName.setText(courseInstructorNameString);
        courseInstructorPhone.setText(courseInstructorPhoneString);
        courseInstructorEmail.setText(courseInstructorEmailString);
    }
}
