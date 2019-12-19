package com.example.android.c196tracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.NotesActivity;
import com.example.android.c196tracker.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final LayoutInflater inflater;
    private int recentlyDeletedItemPosition;
    private NoteEntity recentlyDeletedItem;
    private final Context context;
    private List<NoteEntity> notes;
    private NotesActivity activity;

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.note_content);
        }
    }

    public NoteAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        if (notes != null) {
            NoteEntity current = notes.get(position);
            holder.noteItemView.setText(current.getNoteContent());
        } else {
            holder.noteItemView.setText("nothing");
        }
        holder.noteItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(context, NoteDetails.class);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (notes != null)
            return notes.size();
        else return 0;
    }

    protected void deleteItem(int position) {
        recentlyDeletedItem = notes.get(position);
        recentlyDeletedItemPosition = position;
        notes.remove(position);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    private void showUndoSnackBar() {
        // TODO figure out this line. activity returns null
        View view = activity.findViewById(R.id.notes_recyclerview);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        notes.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }
}
