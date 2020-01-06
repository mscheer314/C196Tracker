package com.example.android.c196tracker;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private int termId;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all_terms:
                Intent termsIntent = new Intent(this, TermsActivity.class);
                startActivity(termsIntent);
                return true;
            case R.id.all_courses:
                Intent coursesIntent = new Intent(this, CoursesActivity.class);
                startActivity(coursesIntent);
                return true;
            case R.id.all_notes:
                Intent NotesIntent = new Intent(this, NotesActivity.class);
                startActivity(NotesIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.terms_menu, menu);
        return true;
    }
}
