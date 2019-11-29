package com.example.android.c196tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.UI.AddCourseDialog;
import com.example.android.c196tracker.UI.CourseAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallBack;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

public class TermDetails extends AppCompatActivity {

    private Button addCourseButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TermViewModel mTermViewModel;
    private CourseViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        setRecyclerView();

        addCourseButton = findViewById(R.id.button_course_add);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openDialog(); }
        });
    }

    private void openDialog() {
        AddCourseDialog addCourseDialog = new AddCourseDialog();
        addCourseDialog.show(getSupportFragmentManager(), "add_course_dialog");
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerivew_courses_in_term);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(courseAdapter));

    }
}
