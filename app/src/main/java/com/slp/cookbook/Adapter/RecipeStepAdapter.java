package com.slp.cookbook.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Steps;
import com.squareup.picasso.Picasso;

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

    public interface OnItemClickListener {
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
        if (TextUtils.isEmpty(recipeSteps.get(position).getThumbnailURL())) {
            Picasso.with(holder.recipeStepImage.getContext()).load(R.drawable.cookbook).into(holder.recipeStepImage);
        } else {
            Picasso.with(holder.recipeStepImage.getContext()).load(recipeSteps.get(position).getThumbnailURL()).error(R.drawable.cookbook).into(holder.recipeStepImage);
        }
    }

    @Override
    public int getItemCount() {
        if (null != recipeSteps)
            return recipeSteps.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeStepTV;
        ImageView recipeStepImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            recipeStepTV = itemView.findViewById(R.id.recipe_step);
            recipeStepImage = itemView.findViewById(R.id.recipe_step_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }
}
