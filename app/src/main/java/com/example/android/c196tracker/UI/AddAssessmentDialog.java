package com.example.android.c196tracker.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.InputChecker;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAssessmentDialog extends AppCompatDialogFragment
        implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddAssessmentDialog";
    ArrayList<String> assessmentList;
    private TextView assessmentName;
    private TextView assessmentDueDate;
    private DatePickerDialog.OnDateSetListener dueDateSetListener;
    private AssessmentViewModel assessmentViewModel;
    private String errorMessage;
    private int courseId;
    private String courseStart;
    private String courseEnd;
    private Spinner assessmentTypeSpinner;
    private String assessmentType;
    private String activeState;
    private Switch activeSwitch;

    public AddAssessmentDialog(int itemId, String start,
                               String end) {
        courseId = itemId;
        courseStart = start;
        courseEnd = end;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_assessment, null);
        assessmentName = view.findViewById(R.id.assessment_name_editText);
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("ADD ASSESSMENT")
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
                        errorMessage = InputChecker.checkItemName(3,
                                assessmentName.getText().toString());
                        errorMessage += InputChecker.checkAssessmentDate(
                                assessmentDueDate.getText().toString());
                        if (errorMessage.length() > 0) {
                            showToast(errorMessage);
                        } else {
                            AssessmentEntity assessment = new AssessmentEntity(
                                    assessmentName.getText().toString(),
                                    assessmentType,
                                    assessmentDueDate.getText().toString(),
                                    courseId
                            );
                            assessmentViewModel.insert(assessment);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        loadDatePicker(view);
        loadSpinnerData(view);
        return dialog;
    }

    private void loadDatePicker(View view) {
        assessmentDueDate = view.findViewById(R.id.assessment_due_date);
        assessmentDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
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
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
                assessmentDueDate.setText(date);
            }
        };
    }

    private void loadSpinnerData(View view) {
        List<String> assessmentTypes = new ArrayList<>();
        assessmentTypes.add("PERFORMANCE");
        assessmentTypes.add("OBJECTIVE");

        assessmentTypeSpinner = view.findViewById(R.id.assessment_type_spinner);
        assessmentTypeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, assessmentTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        assessmentType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
