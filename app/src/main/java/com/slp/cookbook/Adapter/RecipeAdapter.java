package com.slp.cookbook.Adapter;

import android.content.Context;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.ui.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lakshmiprasad on 11/09/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> recipeList;
    private RecipeOnClickListener recipeOnClickListener;


    public RecipeAdapter(List<Recipe> recipeList, RecipeOnClickListener recipeOnClickListener) {
        this.recipeList = recipeList;
        this.recipeOnClickListener = recipeOnClickListener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RecipeHolder(layoutInflater.inflate(R.layout.recipe_card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        if(TextUtils.isEmpty(recipeList.get(position).getImage())){
            Picasso.with(holder.recipeImage.getContext()).load(R.drawable.cookbook).into(holder.recipeImage);
        }else{

            Picasso.with(holder.recipeImage.getContext()).load(recipeList.get(position).getImage()).error(R.drawable.cookbook).into(holder.recipeImage);
        }
        holder.recipeName.setText(recipeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface RecipeOnClickListener {
        void onClick(int position);
    }


    public class RecipeHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        TextView recipeName;

        public RecipeHolder(final View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    recipeOnClickListener.onClick(getAdapterPosition());
                }
            });

        }


    }
}
