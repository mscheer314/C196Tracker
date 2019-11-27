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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.Calendar;

public class AddTermDialog extends AppCompatDialogFragment {
    private static final String TAG = "AddTermDialog";
    private EditText mTermName;
    private TextView mTermStart;
    private TextView mTermEnd;
    private DatePickerDialog.OnDateSetListener mStartSetListener;
    private DatePickerDialog.OnDateSetListener mEndSetListener;
    private TermViewModel mTermViewModel;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_term, null);
        mTermName = view.findViewById(R.id.term_name_editText);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        builder.setView(view)
                .setTitle("Add Term")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent replyIntent = new Intent();

                        if (TextUtils.isEmpty(mTermName.getText())) {
                            //setResult(RESULT_CANCELED, replyIntent);
                        } else {
                            String termName = mTermName.getText().toString();
                            String termStart = mTermStart.toString();
                            String termEnd = mTermEnd.toString();

                            replyIntent.putExtra("termName", termName);
                            replyIntent.putExtra("termStart", termStart);
                            replyIntent.putExtra("termEnd", termEnd);

                                TermEntity term = new TermEntity(termName, termStart, termEnd);
                                mTermViewModel.insert(term);
                        }
                    }
                });
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
            }
        };
        return builder.create();
    }
}
