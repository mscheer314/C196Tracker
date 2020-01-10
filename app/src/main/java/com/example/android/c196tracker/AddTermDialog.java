package com.example.android.c196tracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.Calendar;

public class AddTermDialog extends AppCompatActivity {

    private static final String TAG = "AddTermDialog";
    private boolean isNewTerm;
    private int termId;
    private EditText termName;
    private TextView termStart;
    private TextView termEnd;
    private DatePickerDialog.OnDateSetListener startSetListener;
    private DatePickerDialog.OnDateSetListener endSetListener;
    private Button okButton;
    private Button cancelButton;
    private TermViewModel termViewModel;
    private String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isNewTerm = bundle.getBoolean("isNewTerm");
            termId = bundle.getInt("termId");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_dialog);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termName = findViewById(R.id.term_name_editText);
        setupDateSelectButtons();

        okButton = findViewById(R.id.button_ok);
        okButton.setOnClickListener((view) -> {
                errorMessage = InputChecker.checkItemName(1,
                        termName.getText().toString());
                errorMessage += InputChecker.checkStartAndEndDates(
                        termStart.getText().toString(),
                        termEnd.getText().toString());
                if (errorMessage.length() > 0) {
                    Toast.makeText(AddTermDialog.this, errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    Intent replyIntent = new Intent();
                    String termName = AddTermDialog.this.termName.getText().toString();
                    String termStart = (String) AddTermDialog.this.termStart.getText();
                    String termEnd = (String) AddTermDialog.this.termEnd.getText();
                    if (isNewTerm) {
                        TermEntity term = new TermEntity(termName, termStart, termEnd);
                        termViewModel.insert(term);
                    } else {
                        TermEntity term = new TermEntity(termId, termName, termStart, termEnd);
                        termViewModel.update(term);
                    }
                    setResult(RESULT_OK, replyIntent);
                    finish();
            }
        });

        cancelButton = findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener((view) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void setupDateSelectButtons() {
        termStart = findViewById(R.id.term_start_datepicker);
        termEnd = findViewById(R.id.term_end_datepicker);

        termStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTermDialog.this,
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
                termStart.setText(date);
            }
        };

        termEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTermDialog.this,
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
                termEnd.setText(date);
            }
        };
    }
}
