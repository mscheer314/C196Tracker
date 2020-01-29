package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void goToTerms(View view) {
        Intent termsIntent = new Intent(this, TermsActivity.class);
        startActivity(termsIntent);
    }
}
