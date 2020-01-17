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

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.UI.NoteAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallback;
import com.example.android.c196tracker.ViewModel.NoteViewModel;

import java.util.List;

public class AssessmentDetails extends BaseActivity {

    private String assessmentName;
    private String assessmentDueDate;
    private TextView assessmentNameTextView;
    private TextView assessmentDueDateTextView;
    private Button addNotificationButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NoteViewModel noteViewModel;
    private int courseId;

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

        setCourseDetails();
        setNotificationButton();
        setRecyclerView();
    }

    private void setNotificationButton() {
        addNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.assessment_details_note_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final NoteAdapter noteAdapter = new NoteAdapter(this, this);
        recyclerView.setAdapter(noteAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(noteAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //TODO find why this recyclerview isn't populating with notes
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAssociatedNotes(courseId).observe(this,
                new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(List<NoteEntity> noteEntities) {
                        noteAdapter.setNotes(noteEntities);
                    }
                });
    }

    private void setCourseDetails() {
        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", 0);
    }
}
