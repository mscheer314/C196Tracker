package com.example.android.c196tracker.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.OnClick;

public class AddCourseDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddCourseDialog";
    private EditText mCourseName;
    private TextView mCourseStart;
    private TextView mCourseEnd;
    private String mTermName;
    private DatePickerDialog.OnDateSetListener mStartSetListener;
    private DatePickerDialog.OnDateSetListener mEndSetListener;
    private CourseViewModel mCourseViewModel;
    private Spinner mTermSpinner;
    private TermViewModel mTermViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_course, null);
        mCourseName = view.findViewById(R.id.course_name_editText);
        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        builder.setView(view)
                .setTitle("Add Course")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent replyIntent = new Intent();

                        if (TextUtils.isEmpty(mCourseName.getText())) {
                            //setResult(RESULT_CANCELED, replyIntent);
                        } else {
                            String courseName = mCourseName.getText().toString();
                            String courseStart = (String) mCourseStart.getText();
                            String courseEnd = (String) mCourseEnd.getText();

                            replyIntent.putExtra("courseName", courseName);
                            replyIntent.putExtra("courseStart", courseStart);
                            replyIntent.putExtra("courseEnd", courseEnd);

                            // TODO figure out how to get the termId
                            CourseEntity course = new CourseEntity(courseName, courseStart, courseEnd, termId);
                            mCourseViewModel.insert(course);
                        }
                    }
                });
        mCourseStart = view.findViewById(R.id.course_start_datepicker);
        mCourseEnd = view.findViewById(R.id.course_end_datepicker);

        mCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        mStartSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });

        mStartSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                mCourseStart.setText(date);
            }
        };
        mCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        mEndSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });

        mEndSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                mCourseEnd.setText(date);
            }
        };

        mTermSpinner = view.findViewById(R.id.term_spinner);
        loadSpinnerData();

        return builder.create();
    }

    private void loadSpinnerData() {
        ArrayList<String> termsList = new ArrayList<>();
        ArrayList<Integer> termIds = new ArrayList<>();
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                for (TermEntity term : terms) {
                    termsList.add(term.getTermName());
                    termIds.add(term.getTermId());
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, termsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTermSpinner.setAdapter(adapter);
        mTermSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        mTermName = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
