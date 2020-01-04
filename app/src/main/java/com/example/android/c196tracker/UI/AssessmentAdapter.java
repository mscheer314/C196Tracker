package com.example.android.c196tracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.AssessmentActivity;
import com.example.android.c196tracker.Entities.AssessmentEntity;
import com.example.android.c196tracker.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private final LayoutInflater inflater;
    private int recentlyDeletedItemPosition;
    private AssessmentEntity recentlyDeletedItem;
    private final Context context;
    private List<AssessmentEntity> assessments;
    private AssessmentActivity activity;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentName;
        private final TextView assessmentDate;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentName = itemView.findViewById(R.id.assessment_name);
            assessmentDate = itemView.findViewById(R.id.assessment_date);
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
            holder.assessmentName.setText(current.getAssessmentName());
            holder.assessmentDate.setText(current.getAssessmentDate());
        } else {
            holder.assessmentName.setText("nothing");
            holder.assessmentDate.setText("nothing");
        }
        holder.assessmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(context, AssessmentDetails.class);
                // content.setActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (assessments != null)
            return assessments.size();
        else return 0;
    }

    protected void deleteItem(int position) {
        recentlyDeletedItem = assessments.get(position);
        recentlyDeletedItemPosition = position;
        assessments.remove(position);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    private void showUndoSnackBar() {
        // TODO figure out this line. activity returns null
        View view = activity.findViewById(R.id.assessments_recyclerview);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
    }

    private void undoDelete() {
        assessments.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }
}

