package com.slp.cookbook.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Steps;

import java.util.List;

/**
 * Created by Lakshmiprasad on 07-09-2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {

    private List<Steps> recipeSteps;
    final private OnItemClickListener itemClickListener;

    public RecipeStepAdapter(List<Steps> steps, OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        recipeSteps = steps;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.recipe_step;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.recipeStepTV.setText(recipeSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null != recipeSteps)
            return recipeSteps.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeStepTV;

        public ViewHolder(final View itemView) {
            super(itemView);
            recipeStepTV = itemView.findViewById(R.id.recipe_step);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }
}
