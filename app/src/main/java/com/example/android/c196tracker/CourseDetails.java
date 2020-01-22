package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.UI.AssessmentAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallback;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private TextView courseNameTextView;
    private TextView courseStartTextView;
    private TextView courseEndTextView;
    private TextView courseStatusTextView;
    private TextView courseMentorNameTextView;
    private TextView courseMentorPhoneTextView;
    private TextView courseMentorEmailTextView;
    private AssessmentViewModel assessmentViewModel;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        setCourseDetails();
        setAssessmentRecyclerView();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                courseNameString = data.getStringExtra("courseName");
                courseStartString = data.getStringExtra("courseStart");
                courseEndString = data.getStringExtra("courseEnd");
                courseMentorNameString = data.getStringExtra("mentorName");
                courseMentorEmailString = data.getStringExtra("mentorEmail");
                courseMentorPhoneString = data.getStringExtra("mentorPhone");

                courseNameTextView.setText(courseNameString);
                courseStartTextView.setText(courseStartString);
                courseEndTextView.setText(courseEndString);
                courseMentorNameTextView.setText(courseMentorNameString);
                courseMentorEmailTextView.setText(courseMentorEmailString);
                courseMentorPhoneTextView.setText(courseMentorPhoneString);
            }
        }
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

    private void setAssessmentRecyclerView() {
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
        courseNameTextView = findViewById(R.id.course_detail_title);
        courseStartTextView = findViewById(R.id.course_detail_start);
        courseEndTextView = findViewById(R.id.course_detail_end);
        courseStatusTextView = findViewById(R.id.course_detail_status);
        courseMentorNameTextView = findViewById(R.id.course_details_mentor_name);
        courseMentorPhoneTextView = findViewById(R.id.course_detail_phone);
        courseMentorEmailTextView = findViewById(R.id.course_detail_email);

        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);
        courseNameString = intent.getStringExtra("courseName");
        courseStartString = intent.getStringExtra("courseStart");
        courseEndString = intent.getStringExtra("courseEnd");
        courseStatusString = intent.getStringExtra("courseStatus");
        courseMentorNameString = intent.getStringExtra("courseMentorName");
        courseMentorPhoneString = intent.getStringExtra("courseMentorPhone");
        courseMentorEmailString = intent.getStringExtra("courseMentorEmail");

        courseNameTextView.setText(courseNameString);
        courseStartTextView.setText(courseStartString);
        courseEndTextView.setText(courseEndString);
        courseStatusTextView.setText(courseStatusString);
        courseMentorNameTextView.setText(courseMentorNameString);
        courseMentorPhoneTextView.setText(courseMentorPhoneString);
        courseMentorEmailTextView.setText(courseMentorEmailString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.course_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        if (item.getItemId() == R.id.add_start_notification) {
            Date startDate = new Date();
            try {
                startDate = formatter.parse(courseStartString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            scheduleNotification(getNotification("Course Starting"), startDate.getTime());
        }
        if (item.getItemId() == R.id.add_end_notficiation) {
            Date endDate = new Date();
            try {
                endDate = formatter.parse(courseStartString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            scheduleNotification(getNotification("Course Ending"), endDate.getTime());
            Toast.makeText(this,R.string.notification_scheduled,Toast.LENGTH_LONG).show();
        }
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
