package com.example.android.c196tracker.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.InputChecker;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.TermViewModel;

public class AddCourseMentorDialog extends AppCompatDialogFragment {
    private static final String TAG = "AddCourseMentorDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_course_mentor, null);
        termName = view.findViewById(R.id.term_name_editText);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.add_term)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        errorMessage = InputChecker.checkNewItemInput(true,
                                termName.getText().toString(),
                                termStart.getText().toString(),
                                termEnd.getText().toString());
                        if (errorMessage.length() > 0) {
                            showToast(errorMessage);
                        } else {
                            Intent replyIntent = new Intent();
                            String termName = AddTermDialog.this.termName.getText().toString();
                            String termStart = (String) AddTermDialog.this.termStart.getText();
                            String termEnd = (String) AddTermDialog.this.termEnd.getText();

                            replyIntent.putExtra("termName", termName);
                            replyIntent.putExtra("termStart", termStart);
                            replyIntent.putExtra("termEnd", termEnd);

                            TermEntity term = new TermEntity(termName, termStart, termEnd);
                            termViewModel.insert(term);

                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        setupDateSelectButtons(view);
        return dialog;
    }
}
