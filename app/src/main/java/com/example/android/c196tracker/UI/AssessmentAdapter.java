package com.example.android.c196tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.AssessmentDetails;
import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private final LayoutInflater inflater;
    private final Context context;
    private List<AssessmentEntity> assessments;
    private int assessmentId;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentDueDate;

        private int assessmentId;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_name);
            assessmentDueDate = itemView.findViewById(R.id.assessment_date);
            assessmentId = -1;
        }
    }

    public AssessmentAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
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

                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentId", assessmentId);
                intent.putExtra("assessmentName", assessmentName);
                intent.putExtra("assessmentDueDate", assessmentDate);

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

    public void setAssessments(List<AssessmentEntity> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }
}

