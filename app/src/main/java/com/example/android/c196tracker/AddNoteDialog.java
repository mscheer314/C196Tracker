package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.ViewModel.NoteViewModel;

public class AddNoteDialog extends AppCompatActivity {
    private static final String TAG = "AddNoteDialog";
    private EditText noteContent;
    private Button noteOkButton;
    private Button noteCancelButton;
    private NoteViewModel noteViewModel;
    private int courseId;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String mentorName;
    private String mentorEmail;
    private String mentorPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            courseId = bundle.getInt("courseId");
            courseName = bundle.getString("courseName");
            courseStart = bundle.getString("courseStart");
            courseEnd = bundle.getString("courseEnd");
            courseStatus = bundle.getString("courseStatus");
            mentorName = bundle.getString("mentorName");
            mentorEmail = bundle.getString("mentorEmail");
            mentorPhone = bundle.getString("mentorPhone");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_dialog);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        setViews();
        setNoteOkButtonListener();
        setNoteCancelButton();
    }

    private void setViews() {
        noteContent = findViewById(R.id.note_content);
        noteOkButton = findViewById(R.id.note_ok_button);
        noteCancelButton = findViewById(R.id.note_cancel_button);
    }

    private void setNoteOkButtonListener() {
        noteOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(noteContent.getText())) {
                    Toast.makeText(AddNoteDialog.this, "Enter a note", Toast.LENGTH_LONG).show();
                } else {
                    String noteContent = AddNoteDialog.this.noteContent.getText().toString();

                    replyIntent.putExtra("noteContent", noteContent);
                    replyIntent.putExtra("courseName", courseName);
                    replyIntent.putExtra("courseStart", courseStart);
                    replyIntent.putExtra("courseEnd", courseEnd);
                    replyIntent.putExtra("courseStatus", courseStatus);
                    replyIntent.putExtra("mentorName", mentorName);
                    replyIntent.putExtra("mentorEmail", mentorEmail);
                    replyIntent.putExtra("mentorPhone", mentorPhone);

                    NoteEntity note = new NoteEntity(noteContent, courseId);
                    noteViewModel.insert(note);
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void setNoteCancelButton() {
        noteCancelButton.setOnClickListener((view) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
