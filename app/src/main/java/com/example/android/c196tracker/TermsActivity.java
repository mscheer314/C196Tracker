package com.example.android.c196tracker;

import android.os.Bundle;
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
import com.example.android.c196tracker.UI.AddTermDialog;
import com.example.android.c196tracker.UI.SwipeToDeleteCallBack;
import com.example.android.c196tracker.UI.TermAdapter;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private Button addTermButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TermViewModel mTermViewModel;

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

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
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
}
