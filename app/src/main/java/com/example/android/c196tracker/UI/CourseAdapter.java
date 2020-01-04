package com.example.android.c196tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.CourseDetails;
import com.example.android.c196tracker.CoursesActivity;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final LayoutInflater inflater;
    private CourseEntity recentlyDeletedItem;
    private int recentlyDeletedItemPosition;
    private final Context context;
    private List<CourseEntity> courses;
    private CoursesActivity activity;
    private int courseId;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private final TextView courseItemView3;
        private String courseStatus;
        private String courseMentorName;
        private String courseMentorEmail;
        private String courseMentorPhone;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.course_name);
            courseItemView2 = itemView.findViewById(R.id.course_start);
            courseItemView3 = itemView.findViewById(R.id.course_end);
        }
    }

    public CourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.courses_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (courses != null) {
            CourseEntity current = courses.get(position);
            courseId = current.getCourseId();
            holder.courseItemView.setText(current.getCourseName());
            holder.courseItemView2.setText(current.getCourseStart());
            holder.courseItemView3.setText(current.getCourseEnd());
            holder.courseStatus = current.getCourseStatus();
            holder.courseMentorName = current.getCourseMentorName();
            holder.courseMentorEmail = current.getCourseMentorEmail();
            holder.courseMentorPhone = current.getCourseMentorPhone();
        } else {
            holder.courseItemView.setText("nothing");
            holder.courseItemView2.setText("nothing");
            holder.courseItemView3.setText("nothing");
        }
        holder.courseItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = holder.courseItemView.getText().toString();
                String courseStart = holder.courseItemView2.getText().toString();
                String courseEnd = holder.courseItemView3.getText().toString();
                String courseStatus = holder.courseStatus;
                String mName = holder.courseMentorName;
                String mEmail = holder.courseMentorEmail;
                String mPhone = holder.courseMentorPhone;


                Intent intent = new Intent(context, CourseDetails.class);

                intent.putExtra("courseId", courseId);
                intent.putExtra("courseName", courseName);
                intent.putExtra("courseStart", courseStart);
                intent.putExtra("courseEnd", courseEnd);
                intent.putExtra("courseStatus", courseStatus);
                intent.putExtra("courseMentorName", mName);
                intent.putExtra("courseMentorEmail", mEmail);
                intent.putExtra("courseMentorPhone", mPhone);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (courses != null)
            return courses.size();
        else return 0;
    }

    public void deleteItem(int position) {
        recentlyDeletedItem = courses.get(position);
        recentlyDeletedItemPosition = position;
        courses.remove(position);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    private void showUndoSnackBar() {
        View view = activity.findViewById(R.id.courses_recyclerview);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
    }

    private void undoDelete() {
        courses.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }
}