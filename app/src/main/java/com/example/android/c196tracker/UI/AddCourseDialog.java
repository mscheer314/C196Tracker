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
import com.example.android.c196tracker.Entities.CourseMentorEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.InputChecker;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.CourseMentorViewModel;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddCourseDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddCourseDialog";
    ArrayList<String> termsList;
    ArrayList<Integer> termIdList;
    ArrayList<String> courseMentorList;
    private EditText courseName;
    private TextView courseStart;
    private TextView courseEnd;
    private DatePickerDialog.OnDateSetListener startSetListener;
    private DatePickerDialog.OnDateSetListener endSetListener;
    private CourseViewModel courseViewModel;
    private CourseMentorViewModel courseMentorViewModel;
    private Spinner termSpinner;
    private Spinner courseMentorSpinner;
    private TermViewModel termViewModel;
    private int spinnerIndex;
    private int termId;
    private String errorMessage;
    private Button addCourseMentorButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_course, null);
        courseName = view.findViewById(R.id.course_name_editText);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.add_course)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        errorMessage = InputChecker.checkNewItemInput(false,
                                courseName.getText().toString(),
                                courseStart.getText().toString(),
                                courseEnd.getText().toString());
                        if (errorMessage.length() > 0) {
                            showToast(errorMessage);
                        } else {
                            Intent replyIntent = new Intent();
                            String courseName = AddCourseDialog.this.courseName.getText().toString();
                            String courseStart = AddCourseDialog.this.courseStart.getText().toString();
                            String courseEnd = AddCourseDialog.this.courseEnd.getText().toString();

                            replyIntent.putExtra("courseName", courseName);
                            replyIntent.putExtra("courseStart", courseStart);
                            replyIntent.putExtra("courseEnd", courseEnd);
                            // TODO replace course mentor to the constructor call
                            CourseEntity course = new CourseEntity(courseName, courseStart, courseEnd,
                                    "mentor name", termId);
                            courseViewModel.insert(course);

                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        setupDateSelectButtons(view);
        loadTermSpinnerData(view);
        loadCourseMentorSpinnerData(view);
        loadAddCourseMentorButton(view);
        loadAddCourseMentorButton(view);
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
        termIdList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, termsList);
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
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(adapter);
    }

    // TODO CHECK THAT THIS COURSE MENTOR SPINNER WORKS
    private void loadCourseMentorSpinnerData(View view) {
        courseMentorSpinner = view.findViewById(R.id.course_mentor_spinner);
        courseMentorSpinner.setOnItemSelectedListener(this);
        courseMentorList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseMentorList);
        courseMentorViewModel = new ViewModelProvider(this).get(CourseMentorViewModel.class);
        courseMentorViewModel.getAllCourseMentors().observe(this, new Observer<List<CourseMentorEntity>>() {
            @Override
            public void onChanged(List<CourseMentorEntity> courseMentors) {
                for (CourseMentorEntity courseMentor : courseMentors) {
                    courseMentorList.add(courseMentor.getCourseMentorName());
                }
                adapter.notifyDataSetChanged();
            }
        });
        courseMentorSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerIndex = parent.getSelectedItemPosition();
        termId = termIdList.get(spinnerIndex);

    }

    private void loadAddCourseMentorButton(View view) {
        addCourseMentorButton = view.findViewById(R.id.new_course_mentor_button);
        addCourseMentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewCourseMentorDialog();
            }
        });
    }

    private void openNewCourseMentorDialog() {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
