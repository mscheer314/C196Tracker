package com.example.android.c196tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.UI.AddNoteDialog;
import com.example.android.c196tracker.UI.NoteAdapter;
import com.example.android.c196tracker.ViewModel.NoteViewModel;

import java.util.List;

public class NotesActivity extends BaseActivity {

    private Button addNoteButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        setRecyclerView();

        addNoteButton = findViewById(R.id.notes_add_button);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void setRecyclerView() {
        recyclerView = findViewById(R.id.notes_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final NoteAdapter mAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(mAdapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> notes) {
                mAdapter.setNotes(notes);
            }
        });
    }

    public void openDialog() {
        AddNoteDialog addNoteDialog = new AddNoteDialog();
        addNoteDialog.show(getSupportFragmentManager(), "add_note_dialog");
    }
}

