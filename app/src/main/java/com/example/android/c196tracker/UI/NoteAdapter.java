package com.example.android.c196tracker.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.CourseDetails;
import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.NoteViewModel;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private int recentlyDeletedItemPosition;
    private NoteEntity deletedItem;
    private List<NoteEntity> notes;
    private NoteViewModel noteViewModel;
    private Activity activity;

    public NoteAdapter(Context context, Activity activity) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
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
        deletedItem = notes.get(position);
        noteViewModel = new ViewModelProvider((CourseDetails) activity).get(NoteViewModel.class);
        //noteViewModel.delete(deletedItem);
    }




    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.note_content);
        }
    }
}
