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

    private int mRecentlyDeletedItemPosition;
    private AssessmentEntity mRecentlyDeletedItem;
    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessments;
    private AssessmentActivity mActivity;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessment_details_title);
        }
    }

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.AssessmentViewHolder holder, int postion) {
        if (mAssessments != null) {
            AssessmentEntity current = mAssessments.get(postion);
            holder.assessmentItemView.setText(current.getAssessmentName());
        } else {
            holder.assessmentItemView.setText("nothing");
        }
        holder.assessmentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(context, AssessmentDetails.class);
                // content.setActivity(intent);
            }
        });
    }

    protected void deleteItem(int position) {
        mRecentlyDeletedItem = mAssessments.get(position);
        mRecentlyDeletedItemPosition = position;
        mAssessments.remove(position);
        notifyItemRemoved(position);
        showUndoSnackBar();
    }

    private void showUndoSnackBar() {
        // TODO figure out this line. mActivity returns null
        View view = mActivity.findViewById(R.id.assessments_recyclerview);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
    }

    private void undoDelete() {
        mAssessments.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    public void setmAssessments(List<AssessmentEntity> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}

