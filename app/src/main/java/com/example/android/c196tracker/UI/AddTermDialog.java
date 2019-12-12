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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTermDialog extends AppCompatDialogFragment {
    private static final String TAG = "AddTermDialog";
    private EditText mTermName;
    private TextView mTermStart;
    private TextView mTermEnd;
    private DatePickerDialog.OnDateSetListener mStartSetListener;
    private DatePickerDialog.OnDateSetListener mEndSetListener;
    private TermViewModel mTermViewModel;
    private String errorMessage;
    private String startDateString;
    private String endDateString;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_term, null);
        mTermName = view.findViewById(R.id.term_name_editText);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.add_term)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkInput();
                        if (errorMessage.length() > 0) {
                            showToast(errorMessage);
                        } else {
                            Intent replyIntent = new Intent();
                            String termName = mTermName.getText().toString();
                            String termStart = (String) mTermStart.getText();
                            String termEnd = (String) mTermEnd.getText();

                            replyIntent.putExtra("termName", termName);
                            replyIntent.putExtra("termStart", termStart);
                            replyIntent.putExtra("termEnd", termEnd);

                            TermEntity term = new TermEntity(termName, termStart, termEnd);
                            mTermViewModel.insert(term);

                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        setupDateSelectButtons(view);
        return dialog;
    }

    private String checkInput() {
        errorMessage = "";
        if (TextUtils.isEmpty(mTermName.getText())) {
            errorMessage += "Please enter a term name.\n";
        }
        if (mTermStart.getText().toString().equals("select date")) {
            errorMessage += "Please select a start date.\n";
            Log.d("AddTermDialog.java", "Term start date wasn't selected.");
        }
        if (mTermEnd.getText().toString().equals("select date")) {
            errorMessage += "Please select an end date.\n";
            Log.d("AddTermDialog.java", "Term end date wasn't selected.");
        }
        if (!isValidCompareStartAndEndDates(startDateString, endDateString)) {
            errorMessage += "The start date must be before the end date.";
        }
        return errorMessage;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void setupDateSelectButtons(View view) {
        mTermStart = view.findViewById(R.id.term_start_datepicker);
        mTermEnd = view.findViewById(R.id.term_end_datepicker);

        mTermStart.setOnClickListener(new View.OnClickListener() {
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
                mTermStart.setText(date);
                startDateString = date;
            }
        };

        mTermEnd.setOnClickListener(new View.OnClickListener() {
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
                mTermEnd.setText(date);
                endDateString = date;
            }
        };
    }

    private boolean isValidCompareStartAndEndDates(String startDateString, String endDateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date startDate = simpleDateFormat.parse(startDateString);
            Date endDate = simpleDateFormat.parse(endDateString);

            return startDate.compareTo(endDate) < 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
