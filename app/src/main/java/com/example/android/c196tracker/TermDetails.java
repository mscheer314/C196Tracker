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

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.UI.AddCourseDialog;
import com.example.android.c196tracker.UI.CourseAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallBack;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.List;

public class TermDetails extends BaseActivity {

    private String termName;
    private String termStartString;
    private String termEndString;

    private Button addCourseButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView termTitle;
    private TextView termStart;
    private TextView termEnd;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        setTermDetails();
        setRecyclerView();

        addCourseButton = findViewById(R.id.term_details_course_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        AddCourseDialog addCourseDialog = new AddCourseDialog();
        addCourseDialog.show(getSupportFragmentManager(), "add_course_dialog");
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.term_details_course_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeToDeleteCallBack(courseAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAssociatedCourses(termId).observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courseEntities) {
                courseAdapter.setCourses(courseEntities);
            }
        });
    }

    private void setTermDetails() {
        termTitle = findViewById(R.id.term_details_title);
        termStart = findViewById(R.id.term_details_start);
        termEnd = findViewById(R.id.term_details_end);

        Intent intent = getIntent();
        termName = intent.getStringExtra("termName");
        termStartString = intent.getStringExtra("termStart");
        termEndString = intent.getStringExtra("termEnd");
        termId = intent.getIntExtra("termId", 0);
        termTitle.setText(termName);
        termStart.setText(termStartString);
        termEnd.setText(termEndString);
    }
}
