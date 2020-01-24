package com.example.android.c196tracker.UI;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private NoteAdapter noteAdapter;
    private TermAdapter termAdapter;
    private CourseAdapter courseAdapter;
    private AssessmentAdapter assessmentAdapter;
    private boolean inTermsDetails;
    private int type;

    public SwipeToDeleteCallback(CourseAdapter adapter, boolean inTermsDetails) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        courseAdapter = adapter;
        type = 2;
        this.inTermsDetails = inTermsDetails;
    }

    public SwipeToDeleteCallback(TermAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        termAdapter = adapter;
        type = 1;
    }

    public SwipeToDeleteCallback(CourseAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        courseAdapter = adapter;
        type = 2;
    }

    public SwipeToDeleteCallback(AssessmentAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        assessmentAdapter = adapter;
        type = 3;
    }

    public SwipeToDeleteCallback(NoteAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        noteAdapter = adapter;
        type = 4;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        switch (type) {
            case 1:
                termAdapter.deleteItem(position);
                break;
            case 2:
                if (inTermsDetails) {
                    courseAdapter.deleteItemFromTermDetails(position);
                } else {
                    courseAdapter.deleteItem(position);
                }
                break;
            case 3:
                assessmentAdapter.deleteItem(position);
                break;
            case 4:
                noteAdapter.deleteItem(position);
        }
    }
}
