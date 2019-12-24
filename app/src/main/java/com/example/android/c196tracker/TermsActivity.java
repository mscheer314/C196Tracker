package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.UI.AddCourseMentorDialog;
import com.example.android.c196tracker.UI.AddTermDialog;
import com.example.android.c196tracker.UI.SwipeToDeleteCallBack;
import com.example.android.c196tracker.UI.TermAdapter;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private Button addTermButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        setRecyclerView();

        addTermButton = findViewById(R.id.terms_add_button);
        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.terms_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final TermAdapter mAdapter = new TermAdapter(this);
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(mAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                mAdapter.setTerms(terms);
            }
        });
    }

    public void openDialog() {
        AddTermDialog addTermDialog = new AddTermDialog();
        addTermDialog.show(getSupportFragmentManager(), "add_term_dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_mentor_menu:
                AddCourseMentorDialog addCourseMentorDialog = new AddCourseMentorDialog();
                addCourseMentorDialog.show(getSupportFragmentManager(), "add_course_mentor_dialog");
                return true;
            case R.id.all_courses:
                Intent coursesIntent = new Intent(TermsActivity.this, CoursesActivity.class);
                startActivity(coursesIntent);
                return true;
            case R.id.all_notes:
                Intent NotesIntent = new Intent(TermsActivity.this, NotesActivity.class);
                startActivity(NotesIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}