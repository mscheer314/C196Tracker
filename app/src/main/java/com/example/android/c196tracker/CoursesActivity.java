package com.example.android.c196tracker;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.UI.CourseAdapter;
import com.example.android.c196tracker.ViewModel.CourseViewModel;

import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    private Button addCourseButton;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private CourseViewModel mCourseViewModel;
    private LayoutInflater inflater;
    private PopupWindow popUpWindow;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        recyclerView = findViewById(R.id.recyclerview_courses);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final CourseAdapter mAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        mCourseViewModel.getmAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(@Nullable final List<CourseEntity> courses) {
                mAdapter.setCourses(courses);
            }
        });

        addCourseButton = findViewById(R.id.add_course_button);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO make this load a pop up menu from which you can add a new course
                inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) inflater.inflate(R.layout.dialog_new_term, null);

                popUpWindow = new PopupWindow(
                        container, 900, 900, true);
                popUpWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
            }
        });
    }
}
