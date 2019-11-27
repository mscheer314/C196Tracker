package com.example.android.c196tracker.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.NoteEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.NoteViewModel;

public class AddNoteDialog extends AppCompatDialogFragment {
    private static final String TAG = "AddNoteDialog";
    private EditText mNoteContent;
    private NoteViewModel mNoteViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_note, null);
        mNoteContent = view.findViewById(R.id.note_content);
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        builder.setView(view)
                .setTitle("Add Note")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent replyIntent = new Intent();

                        if (TextUtils.isEmpty(mNoteContent.getText())) {
                            //setResult(RESULT_CANCELED, replyIntent);
                        } else {
                            String noteContent = mNoteContent.getText().toString();

                            replyIntent.putExtra("noteContent", noteContent);

                            NoteEntity note = new NoteEntity(noteContent);
                            mNoteViewModel.insert(note);
                        }
                    }
                });
        return builder.create();
    }
}
