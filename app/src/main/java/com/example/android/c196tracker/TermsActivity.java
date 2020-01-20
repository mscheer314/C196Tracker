package com.example.android.c196tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.UI.SwipeToDeleteCallback;
import com.example.android.c196tracker.UI.TermAdapter;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.List;

public class TermsActivity extends BaseActivity  {

    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
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

        final TermAdapter adapter = new TermAdapter(this, this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(@Nullable final List<TermEntity> terms) {
                adapter.setTerms(terms);
            }
        });
    }

    //TODO add options Menu with dots

    public void openDialog() {
        Intent intent = new Intent(TermsActivity.this, AddTermDialog.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isNewTerm", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
    }
}