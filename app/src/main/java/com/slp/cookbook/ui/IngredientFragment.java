package com.slp.cookbook.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.utils.CookBookConstants;
import com.slp.cookbook.utils.RecipeUtils;


public class IngredientFragment extends Fragment implements CookBookConstants {


    public IngredientFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        Recipe recipe = getActivity().getIntent().getParcelableExtra(RECIPE);
        ListView ingredients = rootView.findViewById(R.id.ingredients);
        ingredients.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, RecipeUtils.getIngredients(recipe.getIngredients())));
        return rootView;
    }


}
