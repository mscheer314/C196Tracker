package com.example.android.c196tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCourseDialog extends AppCompatActivity implements OnItemSelectedListener {
    private static final String TAG = "AddCourseDialog";
    ArrayList<String> termsList;
    ArrayList<Integer> termIdList;
    private EditText courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private EditText mentorName;
    private EditText mentorEmail;
    private EditText mentorPhone;
    private DatePickerDialog.OnDateSetListener startSetListener;
    private DatePickerDialog.OnDateSetListener endSetListener;
    private CourseViewModel courseViewModel;
    private Spinner termSpinner;
    private Spinner courseStatusSpinner;
    private String courseStatus;
    private TermViewModel termViewModel;
    private int spinnerIndex;
    private Button okButton;
    private Button cancelButton;
    private int termId;
    private String errorMessage;
    private boolean isNewCourse;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isNewCourse = bundle.getBoolean("isNewCourse");
            courseId = bundle.getInt("courseId");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_dialog);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseName = findViewById(R.id.course_name_editText);
        mentorName = findViewById(R.id.course_mentor_name_editText);
        mentorEmail = findViewById(R.id.course_mentor_email_editText);
        mentorPhone = findViewById(R.id.course_mentor_phone_editText);

        loadTermSpinnerData();
        loadCourseStatusSpinner();
        setupDateSelectButtons();

        okButton = findViewById(R.id.course_ok_button);
        okButton.setOnClickListener((view) -> {
            errorMessage = InputChecker.checkItemName(2,
                    courseName.getText().toString());
            errorMessage += InputChecker.checkStartAndEndDates(
                    courseStart.getText().toString(),
                    courseEnd.getText().toString());
            if (errorMessage.length() > 0) {
                Toast.makeText(AddCourseDialog.this, errorMessage, Toast.LENGTH_LONG).show();
            } else {
                Intent replyIntent = new Intent();
                String courseName = AddCourseDialog.this.courseName.getText().toString();
                String courseStart = AddCourseDialog.this.courseStart.getText().toString();
                String courseEnd = AddCourseDialog.this.courseEnd.getText().toString();
                String mentorNameString = AddCourseDialog.this.mentorName.getText().toString();
                String mentorEmailString = AddCourseDialog.this.mentorEmail.getText().toString();
                String mentorPhoneString = AddCourseDialog.this.mentorPhone.getText().toString();


                replyIntent.putExtra("courseName", courseName);
                replyIntent.putExtra("courseStart", courseStart);
                replyIntent.putExtra("courseEnd", courseEnd);

                if (isNewCourse) {
                    CourseEntity course = new CourseEntity(courseName, courseStart, courseEnd, courseStatus,
                            mentorNameString, mentorEmailString, mentorPhoneString, termId);
                    courseViewModel.insert(course);
                } else {
                    CourseEntity course = new CourseEntity(courseId, courseName, courseStart, courseEnd, courseStatus,
                            mentorNameString, mentorEmailString, mentorPhoneString, termId);
                    courseViewModel.update(course);
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

        cancelButton = findViewById(R.id.course_cancel_button);
        cancelButton.setOnClickListener((view) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void setupDateSelectButtons() {
        courseStart = findViewById(R.id.course_start_datepicker);
        courseEnd = findViewById(R.id.course_end_datepicker);

        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddCourseDialog.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        startSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });

        startSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                courseStart.setText(date);
            }
        };
        courseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddCourseDialog.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        endSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });

        endSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                courseEnd.setText(date);
            }
        };
    }

    private void loadTermSpinnerData() {
        termSpinner = findViewById(R.id.term_spinner);
        termSpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
        termsList = new ArrayList<>();
        termsList.add("SELECT A TERM");
        termIdList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCourseDialog.this,
                android.R.layout.simple_spinner_item, termsList);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                for (TermEntity term : terms) {
                    termsList.add(term.getTermName());
                    termIdList.add(term.getTermId());
                }
                adapter.notifyDataSetChanged();
            }
        });
        termSpinner.setAdapter(adapter);
    }

    private void loadCourseStatusSpinner() {
        courseStatusSpinner = findViewById(R.id.course_status_spinner);
        courseStatusSpinner.setOnItemSelectedListener((OnItemSelectedListener) this);
        ArrayList<String> statusOptions = new ArrayList<>();
        statusOptions.add("SELECT A STATUS");
        statusOptions.add("IN PROGRESS");
        statusOptions.add("COMPLETED");
        statusOptions.add("DROPPED");
        statusOptions.add("PLAN TO TAKE");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCourseDialog.this,
                android.R.layout.simple_spinner_item, statusOptions);
        courseStatusSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.term_spinner:
                spinnerIndex = parent.getSelectedItemPosition();
                if (spinnerIndex != 0)
                    termId = termIdList.get(spinnerIndex - 1);
            case R.id.course_status_spinner:
                if (!parent.getSelectedItem().toString().equals("SELECT A STATUS"))
                    courseStatus = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
