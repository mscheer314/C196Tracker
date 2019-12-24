package com.example.android.c196tracker.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.c196tracker.Entities.CourseMentorEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.InputChecker;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.CourseMentorViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

public class AddCourseMentorDialog extends AppCompatDialogFragment {
    private static final String TAG = "AddCourseMentorDialog";
    private EditText mentorName;
    private EditText mentorEmail;
    private EditText mentorPhone;
    private CourseMentorViewModel mentorViewModel;
    private String errorMessage;
    private String mentorNameString;
    private String mentorEmailString;
    private String mentorPhoneString;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_course_mentor, null);
        mentorName = view.findViewById(R.id.course_mentor_name_editText);
        mentorEmail = view.findViewById(R.id.course_mentor_email_editText);
        mentorPhone = view.findViewById(R.id.course_mentor_phone_editText);
        mentorViewModel = new ViewModelProvider(this).get(CourseMentorViewModel.class);
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.add_mentor)
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
                        mentorNameString = mentorName.getText().toString();
                        mentorEmailString = mentorEmail.getText().toString();
                        mentorPhoneString = mentorPhone.getText().toString();
                        errorMessage = InputChecker.isValidCourseMentor(mentorNameString,
                                mentorEmailString, mentorPhoneString);
                        if (errorMessage.length() > 0) {
                            showToast(errorMessage);
                        } else {
                            Intent replyIntent = new Intent();

                            replyIntent.putExtra("mentorName", mentorNameString);
                            replyIntent.putExtra("mentorEmail", mentorEmailString);
                            replyIntent.putExtra("mentorPhone", mentorPhoneString);

                            CourseMentorEntity mentor = new CourseMentorEntity(mentorNameString,
                                    mentorEmailString, mentorPhoneString);
                            mentorViewModel.insert(mentor);

                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
