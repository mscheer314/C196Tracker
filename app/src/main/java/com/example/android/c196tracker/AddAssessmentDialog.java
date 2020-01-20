package com.example.android.c196tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddAssessmentDialog extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddAssessmentDialog";
    private TextView assessmentNameTextView;
    private TextView assessmentDueDateTextView;
    private DatePickerDialog.OnDateSetListener dueDateSetListener;
    private Button okButton;
    private Button cancelButton;
    private AssessmentViewModel assessmentViewModel;
    private String errorMessage;
    private int courseId;
    private int assessmentId;
    private String courseStart;
    private String courseEnd;
    private Spinner assessmentTypeSpinner;
    private String assessmentType;
    private boolean isNewAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isNewAssessment = bundle.getBoolean("isNewAssessment");
            courseId = bundle.getInt("courseId");
            assessmentId = bundle.getInt("assessmentId");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_dialog);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        assessmentNameTextView = findViewById(R.id.assessment_name_editText);

        loadDatePicker();
        loadSpinnerData();
        loadCancelButton();

        okButton = findViewById(R.id.assessment_ok_button);
        okButton.setOnClickListener((view) -> {
            errorMessage = InputChecker.checkItemName(3,
                    assessmentNameTextView.getText().toString());
            errorMessage += InputChecker.checkAssessmentDate(
                    assessmentDueDateTextView.getText().toString());
            if (errorMessage.length() > 0) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            } else {
                Intent replyIntent = new Intent();

                String assessmentNameString = AddAssessmentDialog.this.assessmentNameTextView.getText().toString();
                String assessmentTypeString = AddAssessmentDialog.this.assessmentType;
                String assessmentDueDateString = AddAssessmentDialog.this.assessmentDueDateTextView.getText()
                        .toString();

                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                Date assessmentDueDate = new Date();
                try {
                    assessmentDueDate = formatter.parse(assessmentDueDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (isNewAssessment) {
                    AssessmentEntity assessment = new AssessmentEntity(
                            assessmentNameString, assessmentTypeString, assessmentDueDate, courseId);
                    assessmentViewModel.insert(assessment);

                } else {
                    AssessmentEntity assessment = new AssessmentEntity(assessmentId,
                            assessmentNameString, assessmentTypeString, assessmentDueDate, courseId);
                    assessmentViewModel.insert(assessment);
                }

                replyIntent.putExtra("assessmentName", assessmentNameString);
                replyIntent.putExtra("assessmentDueDate", assessmentDueDateString);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    private void loadCancelButton() {
        cancelButton = findViewById(R.id.assessment_cancel_button);
        cancelButton.setOnClickListener((view) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void loadDatePicker() {
        assessmentDueDateTextView = findViewById(R.id.assessment_due_date);
        assessmentDueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddAssessmentDialog.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        dueDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });

        dueDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm-dd-yyyy: " + month + "-" + dayOfMonth + "-" + year);
                String date = month + "-" + dayOfMonth + "-" + year;
                assessmentDueDateTextView.setText(date);
            }
        };
    }

    private void loadSpinnerData() {
        List<String> assessmentTypes = new ArrayList<>();
        assessmentTypes.add("PERFORMANCE");
        assessmentTypes.add("OBJECTIVE");

        assessmentTypeSpinner = findViewById(R.id.assessment_type_spinner);
        assessmentTypeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddAssessmentDialog.this,
                android.R.layout.simple_spinner_item, assessmentTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        assessmentType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
