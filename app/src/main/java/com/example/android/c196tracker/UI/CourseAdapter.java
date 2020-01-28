package com.example.android.c196tracker.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.CourseDetails;
import com.example.android.c196tracker.CoursesActivity;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.TermDetails;
import com.example.android.c196tracker.ViewModel.CourseViewModel;
import com.example.android.c196tracker.ViewModel.TermViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-YYYY");
    private CourseEntity deletedItem;
    private List<CourseEntity> courses;
    private Activity activity;
    private int courseId;
    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;
    private String termStart;
    private String termEnd;
    private boolean isInTermDetails;

    public CourseAdapter(Context context, Activity activity, Boolean isInTermDetails) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.isInTermDetails = isInTermDetails;
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
            holder.termId = current.getTermId();
            int indexOfTerm = 0;
            if (isInTermDetails) {
                termViewModel = new ViewModelProvider((TermDetails) activity).get(TermViewModel.class);
            } else {
                termViewModel = new ViewModelProvider((CoursesActivity) activity).get(TermViewModel.class);
            }
                List<TermEntity> term = termViewModel.getTermById(holder.termId);
            holder.termStart = dateFormatter.format(term.get(indexOfTerm).getTermStart());
            holder.termEnd = dateFormatter.format(term.get(indexOfTerm).getTermEnd());
            holder.courseId = current.getCourseId();
            holder.courseNameTextView.setText(current.getCourseName());
            holder.courseStartTextView.setText(dateFormatter.format(current.getCourseStart()));
            holder.courseEndTextView.setText(dateFormatter.format(current.getCourseEnd()));
            holder.courseStatus = current.getCourseStatus();
            holder.courseMentorName = current.getCourseMentorName();
            holder.courseMentorEmail = current.getCourseMentorEmail();
            holder.courseMentorPhone = current.getCourseMentorPhone();
        } else {
            holder.courseNameTextView.setText("nothing");
            holder.courseStartTextView.setText("nothing");
            holder.courseEndTextView.setText("nothing");
        }
        holder.courseNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseId = holder.courseId;
                termStart = holder.termStart;
                termEnd = holder.termEnd;
                String courseName = holder.courseNameTextView.getText().toString();
                String courseStart = holder.courseStartTextView.getText().toString();
                String courseEnd = holder.courseEndTextView.getText().toString();
                String courseStatus = holder.courseStatus;
                String mName = holder.courseMentorName;
                String mEmail = holder.courseMentorEmail;
                String mPhone = holder.courseMentorPhone;


                Intent intent = new Intent(context, CourseDetails.class);

                intent.putExtra("courseId", courseId);
                intent.putExtra("termStart", termStart);
                intent.putExtra("termEnd", termEnd);
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
        deletedItem = courses.get(position);
        courseViewModel = new ViewModelProvider((CoursesActivity) activity).get(CourseViewModel.class);
        courseViewModel.delete(deletedItem);
    }

    public void deleteItemFromTermDetails(int position) {
        deletedItem = courses.get(position);
        courseViewModel = new ViewModelProvider((TermDetails) activity).get(CourseViewModel.class);
        courseViewModel.delete(deletedItem);
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameTextView;
        private final TextView courseStartTextView;
        private final TextView courseEndTextView;
        private String courseStatus;
        private String courseMentorName;
        private String courseMentorEmail;
        private String courseMentorPhone;
        private int courseId;
        private int termId;
        private String termStart;
        private String termEnd;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.course_name);
            courseStartTextView = itemView.findViewById(R.id.course_start);
            courseEndTextView = itemView.findViewById(R.id.course_end);
            courseId = -1;

        }
    }
}