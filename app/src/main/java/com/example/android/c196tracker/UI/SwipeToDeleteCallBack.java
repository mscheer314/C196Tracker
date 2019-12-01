package com.example.android.c196tracker.UI;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallBack extends ItemTouchHelper.SimpleCallback {

    private TermAdapter termAdapter;
    private CourseAdapter courseAdapter;
    private NoteAdapter noteAdapter;
    private AssessmentAdapter assessmentAdapter;

    public SwipeToDeleteCallBack(TermAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        termAdapter = adapter;
    }

    public SwipeToDeleteCallBack(CourseAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        courseAdapter = adapter;
    }

    public SwipeToDeleteCallBack(NoteAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        noteAdapter = adapter;
    }

    public SwipeToDeleteCallBack(AssessmentAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        assessmentAdapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (termAdapter != null) {
            termAdapter.deleteItem(position);
        }
        if (courseAdapter != null) {
            courseAdapter.deleteItem(position);
        }
        if (noteAdapter != null) {
            noteAdapter.deleteItem(position);
        }

        if (assessmentAdapter != null) {
            assessmentAdapter.deleteItem(position);
        }
    }
}
