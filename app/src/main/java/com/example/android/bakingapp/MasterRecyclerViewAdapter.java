package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Classes.Recipe;
import com.example.android.bakingapp.Classes.Step;

import java.util.List;

public class MasterRecyclerViewAdapter extends RecyclerView.Adapter<MasterRecyclerViewAdapter.MasterViewHolder> {

    private Context mContext;
    private List<Step> mStepList;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MasterRecyclerViewAdapter(Context context, List<Step> steps, RecyclerViewItemClickListener listener){
        mContext = context;
        mStepList = steps;
        recyclerViewItemClickListener = listener;
    }

    public Step getItem(int position){
        return mStepList.get(position);
    }

    @Override
    public MasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_card, parent, false);
        return new MasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterViewHolder holder, int position) {
        holder.stepDescription.setText(mStepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public class MasterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView stepDescription;

        public MasterViewHolder(View itemView) {
            super(itemView);
            stepDescription = itemView.findViewById(R.id.step_card_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
