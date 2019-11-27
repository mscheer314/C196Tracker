package com.example.android.c196tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.NoteDetails;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.TermDetails;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.note_content);
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<NoteEntity> mNotes;

    public NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        if (mNotes != null) {
            NoteEntity current = mNotes.get(position);
            holder.noteItemView.setText(current.getNoteContent());
        } else {
            holder.noteItemView.setText("nothing");
        }
        holder.noteItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteDetails.class);
                context.startActivity(intent);
            }
        });
    }
    public void setNotes(List<NoteEntity> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }
}