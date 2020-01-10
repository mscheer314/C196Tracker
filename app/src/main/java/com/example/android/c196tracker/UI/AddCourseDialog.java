package com.example.android.c196tracker.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.InputChecker;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCourseDialog extends AppCompatDialogFragment
        implements AdapterView.OnItemSelectedListener {
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
    private int termId;
    private String errorMessage;
    private boolean isNewCourse;
    private int courseId;

    public AddCourseDialog(){
        this.isNewCourse = true;
    }

    public AddCourseDialog(boolean isNewCourse, int courseId) {
        this.isNewCourse = isNewCourse;
        this.courseId = courseId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_course, null);
        courseName = view.findViewById(R.id.course_name_editText);
        mentorName = view.findViewById(R.id.course_mentor_name_editText);
        mentorEmail = view.findViewById(R.id.course_mentor_email_editText);
        mentorPhone = view.findViewById(R.id.course_mentor_phone_editText);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        if (isNewCourse)
            dialog.setTitle(R.string.add_course);
        else
            dialog.setTitle(R.string.edit_course);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        errorMessage = InputChecker.checkItemName(2,
                                courseName.getText().toString());
                        errorMessage += InputChecker.checkStartAndEndDates(
                                courseStart.getText().toString(),
                                courseEnd.getText().toString());
                        if (errorMessage.length() > 0) {
                            showToast(errorMessage);
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
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        setupDateSelectButtons(view);
        loadTermSpinnerData(view);
        loadCourseStatusSpinner(view);
        return dialog;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void setupDateSelectButtons(View view) {
        courseStart = view.findViewById(R.id.course_start_datepicker);
        courseEnd = view.findViewById(R.id.course_end_datepicker);

        courseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
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

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
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

    private void loadTermSpinnerData(View view) {
        termSpinner = view.findViewById(R.id.term_spinner);
        termSpinner.setOnItemSelectedListener(this);
        termsList = new ArrayList<>();
        termsList.add("SELECT A TERM");
        termIdList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
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

    private void loadCourseStatusSpinner(View view) {
        courseStatusSpinner = view.findViewById(R.id.course_status_spinner);
        courseStatusSpinner.setOnItemSelectedListener(this);
        ArrayList<String> statusOptions = new ArrayList<>();
        statusOptions.add("SELECT A STATUS");
        statusOptions.add("IN PROGRESS");
        statusOptions.add("COMPLETED");
        statusOptions.add("DROPPED");
        statusOptions.add("PLAN TO TAKE");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
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
