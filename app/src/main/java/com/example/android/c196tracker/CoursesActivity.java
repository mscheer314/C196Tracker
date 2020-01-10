package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.UI.CourseAdapter;
import com.example.android.c196tracker.ViewModel.CourseViewModel;

import java.util.List;

public class CoursesActivity extends BaseActivity {

    private static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    private Button addCourseButton;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        setRecyclerView();

        addCourseButton = findViewById(R.id.courses_add_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.courses_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final CourseAdapter mAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(mAdapter);

        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(mAdapter));
        //itemTouchHelper.attachToRecyclerView(recyclerView);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {
                mAdapter.setCourses(courses);
            }
        });
    }

    public void openDialog() {
        Intent intent = new Intent(CoursesActivity.this, AddCourseDialog.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNewCourse", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
    }
}

