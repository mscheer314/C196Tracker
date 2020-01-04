package com.example.android.c196tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Find the 3 image views for the main page
        ImageView terms = findViewById(R.id.imageView);
        ImageView courses = findViewById(R.id.imageView2);
        ImageView assessments = findViewById(R.id.imageView3);

        // set up listeners for the three ImageViews
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a new intent to open the {@link TermsActivity}
                Intent termsIntent = new Intent(MainMenuActivity.this, TermsActivity.class);

                //start new activity
                startActivity(termsIntent);
            }
        });

        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a new intent to open the {@link CoursesActivity}
                Intent coursesIntent = new Intent(MainMenuActivity.this, CoursesActivity.class);

                //start new activity
                startActivity(coursesIntent);
            }
        });

        assessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a new intent to open the {@link NotesActivity}
                Intent AssessmentIntent = new Intent(MainMenuActivity.this, AssessmentActivity.class);

                //start new activity
                startActivity(AssessmentIntent);
            }
        });
    }
}
