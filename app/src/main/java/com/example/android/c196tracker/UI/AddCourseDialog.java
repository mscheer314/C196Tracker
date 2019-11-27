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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Database.SchoolTrackerDatabase;
import com.example.android.c196tracker.Database.SchoolTrackerRepository;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.UI.TermAdapter;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.Calendar;
import java.util.List;

public class AddCourseDialog extends AppCompatDialogFragment {
    private static final String TAG = "AddCourseDialog";
    //  TODO UI element for getting the termId
    private EditText mCourseName;
    private TextView mCourseStart;
    private TextView mCourseEnd;
    private DatePickerDialog.OnDateSetListener mStartSetListener;
    private DatePickerDialog.OnDateSetListener mEndSetListener;
    private CourseViewModel mCourseViewModel;
    private Spinner mTermSpinner;

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
                            // TODO get the termId from the UI element

                            replyIntent.putExtra("courseName", courseName);
                            replyIntent.putExtra("courseStart", courseStart);
                            replyIntent.putExtra("courseEnd", courseEnd);

                                 CourseEntity course = new CourseEntity(courseName, courseStart, courseEnd);
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
       /* List<TermEntity> terms = SchoolTrackerRepository rep = ne
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
               // (getContext(), android.R.layout.simple_spinner_item, terms);

        */
    }
}
