package com.example.android.c196tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.c196tracker.Entities.TermEntity;
import com.example.android.c196tracker.R;
import com.example.android.c196tracker.TermDetails;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final TextView termItemView2;
        private final TextView termItemView3;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.term_name);
            termItemView2 = itemView.findViewById(R.id.term_start);
            termItemView3 = itemView.findViewById(R.id.term_end);
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerms;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (mTerms != null) {
            TermEntity current = mTerms.get(position);
            holder.termItemView.setText(current.getTermName());
            holder.termItemView2.setText(current.getTermStart());
            holder.termItemView3.setText(current.getTermEnd());
        } else {
            holder.termItemView.setText("nothing");
            holder.termItemView2.setText("nothing");
            holder.termItemView3.setText("nothing");
        }
        holder.termItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TermDetails.class);
                context.startActivity(intent);
            }
        });
    }

    public void setTerms(List<TermEntity> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }


}