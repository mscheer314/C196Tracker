package com.example.android.c196tracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddGoalDialog extends BaseActivity {
    private String goalContent;
    private CalendarView goalDateCalendarView;
    private ZonedDateTime zonedDateTime;
    private EditText goalContentEditText;
    private LocalDate goalDate;
    private Button goalOkButton;
    private Button goalCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_goal);

        goalContentEditText = findViewById(R.id.goal_content_editText);
        goalDateCalendarView = findViewById(R.id.goal_calendar);
        goalOkButton = findViewById(R.id.goal_ok_button);
        goalCancelButton = findViewById(R.id.goal_cancel_button);

        goalDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onSelectedDayChange: mm-dd-yyyy: " + month + "-" + dayOfMonth + "-" + year);
                goalDate = LocalDate.of(year, month, dayOfMonth);
            }
        });

        goalOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalContent = goalContentEditText.getText().toString();
                zonedDateTime = goalDate.atStartOfDay(ZoneId.systemDefault());
                scheduleNotification(getNotification(goalContent), zonedDateTime.toEpochSecond());

                finish();
            }
        });

        goalCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
