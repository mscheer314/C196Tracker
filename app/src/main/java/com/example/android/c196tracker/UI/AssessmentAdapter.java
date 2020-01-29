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

import com.example.android.c196tracker.AssessmentDetails;
import com.example.android.c196tracker.CourseDetails;
import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.Entities.CourseEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;
import com.example.android.c196tracker.ViewModel.CourseViewModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-YYYY");
    private List<AssessmentEntity> assessments;
    private int assessmentId;
    private Activity activity;
    private AssessmentEntity deletedItem;
    private AssessmentViewModel assessmentViewModel;
    private CourseViewModel courseViewModel;
    private boolean isInCourseDetails;


    public AssessmentAdapter(Context context, Activity activity, boolean isInCourseDetails) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.isInCourseDetails = isInCourseDetails;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (assessments != null) {
            AssessmentEntity current = assessments.get(position);
            holder.courseId = current.getCourseId();
            holder.assessmentId = current.getAssessmentId();
            holder.assessmentName.setText(current.getAssessmentName());
            holder.assessmentDueDate.setText(dateFormatter.format(current.getAssessmentDate()));
            holder.assessmentType.setText(current.getAssessmentType());
            if (isInCourseDetails) {
                courseViewModel = new ViewModelProvider((CourseDetails) activity).get(CourseViewModel.class);
            } else {
                courseViewModel = new ViewModelProvider((AssessmentDetails) activity).get(CourseViewModel.class);
            }
            List<CourseEntity> course = courseViewModel.getCourseByCourseId(holder.courseId);
            int indexOfCourse = 0;
            holder.courseStart = dateFormatter.format(course.get(indexOfCourse).getCourseStart());
            holder.courseEnd = dateFormatter.format(course.get(indexOfCourse).getCourseEnd());
        } else {
            holder.assessmentName.setText("nothing");
            holder.assessmentDueDate.setText("nothing");
        }
        holder.assessmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assessmentId = holder.assessmentId;
                String assessmentName = holder.assessmentName.getText().toString();
                String assessmentDate = holder.assessmentDueDate.getText().toString();
                String assessmentType = holder.assessmentType.getText().toString();
                int assessmentCourseId = holder.courseId;
                String courseStart = holder.courseStart;
                String courseEnd = holder.courseEnd;

                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentId", assessmentId);
                intent.putExtra("assessmentName", assessmentName);
                intent.putExtra("assessmentDueDate", assessmentDate);
                intent.putExtra("assessmentType", assessmentType);
                intent.putExtra("courseId", assessmentCourseId);
                intent.putExtra("courseStart", courseStart);
                intent.putExtra("courseEnd", courseEnd);


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (assessments != null)
            return assessments.size();
        else return 0;
    }

    public void deleteItem(int position) {
        deletedItem = assessments.get(position);
        assessmentViewModel = new ViewModelProvider((CourseDetails) activity).get(AssessmentViewModel.class);
        assessmentViewModel.delete(deletedItem);
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentDueDate;
        private final TextView assessmentType;
        public String courseStart;
        public String courseEnd;

        private int assessmentId;
        private int courseId;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_name);
            assessmentDueDate = itemView.findViewById(R.id.assessment_date);
            assessmentType = itemView.findViewById(R.id.assessment_type);
            assessmentId = -1;
            courseId = -1;
        }
    }
}

