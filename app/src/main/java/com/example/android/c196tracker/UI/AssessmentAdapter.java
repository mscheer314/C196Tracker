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
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.ViewModel.AssessmentViewModel;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<AssessmentEntity> assessments;
    private int assessmentId;
    private Activity activity;
    private AssessmentEntity deletedItem;
    private AssessmentViewModel assessmentViewModel;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentDueDate;

        private int assessmentId;
        private int courseId;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_name);
            assessmentDueDate = itemView.findViewById(R.id.assessment_date);
            assessmentId = -1;
            courseId = -1;
        }
    }

    public AssessmentAdapter(Context context, Activity activity) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.AssessmentViewHolder holder, int postion) {
        if (assessments != null) {
            AssessmentEntity current = assessments.get(postion);
            holder.courseId = current.getCourseId();
            holder.assessmentId = current.getAssessmentId();
            holder.assessmentName.setText(current.getAssessmentName());
            holder.assessmentDueDate.setText(current.getAssessmentDate());
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
                int assessmentCourseId = holder.courseId;

                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentId", assessmentId);
                intent.putExtra("assessmentName", assessmentName);
                intent.putExtra("assessmentDueDate", assessmentDate);
                intent.putExtra("courseId", assessmentCourseId);


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
}

