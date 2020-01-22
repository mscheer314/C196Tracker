package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.UI.NoteAdapter;
import com.example.android.c196tracker.UI.SwipeToDeleteCallback;
import com.example.android.c196tracker.ViewModel.NoteViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssessmentDetails extends BaseActivity {

    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    private String assessmentName;
    private String assessmentDueDate;
    private TextView assessmentNameTextView;
    private TextView assessmentDueDateTextView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NoteViewModel noteViewModel;
    private int courseId;
    private int assessmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentNameTextView = findViewById(R.id.assessment_details_title);
        assessmentDueDateTextView = findViewById(R.id.assessment_details_due_date);

        setAssessmentDetails();
        setCourseDetails();
        setRecyclerView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assessmentName = data.getStringExtra("assessmentName");
                assessmentDueDate = data.getStringExtra("assessmentDueDate");

                assessmentNameTextView.setText(assessmentName);
                assessmentDueDateTextView.setText(assessmentDueDate);
            }
        }
    }

    private void setAssessmentDetails() {
        Intent intent = getIntent();
        assessmentName = intent.getStringExtra("assessmentName");
        assessmentDueDate = intent.getStringExtra("assessmentDueDate");
        assessmentId = intent.getIntExtra("assessmentId", 0);

        assessmentNameTextView.setText(assessmentName);
        assessmentDueDateTextView.setText(assessmentDueDate);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.assessment_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        if (item.getItemId() == R.id.edit_assessment) {
            Intent intent = new Intent(AssessmentDetails.this, AddAssessmentDialog.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isNewAssessment", false);
            bundle.putInt("courseId", courseId);
            bundle.putInt("assessmentId", assessmentId);
            intent.putExtras(bundle);
            startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
        }
        if (item.getItemId() == R.id.share_note) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        if (item.getItemId() == R.id.add_assessment_notification) {
            Date assessmentDate = new Date();
            try {
                assessmentDate = formatter.parse(assessmentDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            scheduleNotification(getNotification("Assessment upcoming"), assessmentDate.getTime());
            Toast.makeText(this,R.string.notification_scheduled,Toast.LENGTH_LONG).show();
        }

        if (item.getItemId() == R.id.add_goal_alert) {
            
        }
        return super.onOptionsItemSelected(item);
    }
}
