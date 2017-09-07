package com.slp.cookbook.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slp.cookbook.Adapter.RecipeStepAdapter;
import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.data.Steps;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment {

    private List<Steps> recipeSteps;
    private RecyclerView recipeStepsRV;

    public RecipeStepsFragment() {
    }

    public void setRecipeSteps(List<Steps> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        if (null != recipeSteps && recipeSteps.size() > 0) {
            recipeStepsRV = rootView.findViewById(R.id.recipe_steps_rv);
            recipeStepsRV.setAdapter(new RecipeStepAdapter(recipeSteps));
            recipeStepsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recipeStepsRV.setHasFixedSize(true);

        }
        return rootView;
    }

}
