package com.example.android.c196tracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (mCourses != null) {
            CourseEntity current = mCourses.get(position);
            holder.courseItemView.setText(current.getCourseName());
            holder.courseItemView2.setText(current.getCourseStart());
            holder.courseItemView3.setText(current.getCourseEnd());
        } else {
            holder.courseItemView.setText("nothing");
            holder.courseItemView2.setText("nothing");
            holder.courseItemView3.setText("nothing");
        }
        holder.courseItemView.setOnClickListener((v) -> {
            //TODO create CourseDetails class

        });
    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private final TextView courseItemView3;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.course_name);
            courseItemView2 = itemView.findViewById(R.id.course_start);
            courseItemView3 = itemView.findViewById(R.id.course_end);
        }
    }
}