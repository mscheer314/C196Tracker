package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.UI.AssessmentAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallback;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;

import java.util.List;

public class CourseDetails extends BaseActivity {

    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    private static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private int courseId;
    private String courseNameString;
    private String courseStartString;
    private String courseEndString;
    private String courseStatusString;
    private String courseMentorNameString;
    private String courseMentorPhoneString;
    private String courseMentorEmailString;

    private Button addAssessmentButton;
    private Button addNoteButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseStatus;
    private TextView courseMentorName;
    private TextView courseMentorPhone;
    private TextView courseMentorEmail;
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setCourseDetails();
        setRecyclerView();

        addAssessmentButton = findViewById(R.id.add_assessment_button);
        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssessmentDialog();
            }
        });
        addNoteButton = findViewById(R.id.add_note_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteDialog();
            }
        });
    }

    private void openAddNoteDialog() {
        Intent intent = new Intent(CourseDetails.this, AddNoteDialog.class);
        Bundle bundle = new Bundle();
        bundle.putInt("courseId", courseId);
        intent.putExtras(bundle);
        startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
    }

    private void openAssessmentDialog() {
        Intent intent = new Intent(CourseDetails.this, AddAssessmentDialog.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNewAssessment", true);
        bundle.putInt("courseId", courseId);
        intent.putExtras(bundle);
        startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.course_detail_assessment_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, this);
        recyclerView.setAdapter(assessmentAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeToDeleteCallback(assessmentAdapter));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.course_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_course) {
            Intent intent = new Intent(CourseDetails.this, AddCourseDialog.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isNewTerm", false);
            bundle.putInt("courseId", courseId);
            intent.putExtras(bundle);
            startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }
}
