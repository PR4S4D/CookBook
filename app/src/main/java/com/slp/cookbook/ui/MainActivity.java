package com.slp.cookbook.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.slp.cookbook.R;
import com.slp.cookbook.data.Recipe;
import com.slp.cookbook.network.NetworkUtils;
import com.slp.cookbook.utils.CookBookConstants;
import com.slp.cookbook.utils.RecipeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CookBookConstants, Callback<List<Recipe>> {

    private List<Recipe> recipes;
    @Bind(R.id.progress_bar_layout)
    FrameLayout progress_bar_layout;

    private NetworkReceiver networkReceiver;
    private boolean isNetworkReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progress_bar_layout.setVisibility(View.VISIBLE);
        NetworkUtils.getRecipes(this);
    }

    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        progress_bar_layout.setVisibility(View.INVISIBLE);

        recipes = response.body();
        GridView recipeGV = (GridView) findViewById(R.id.recipes);
        recipeGV.setAdapter(new RecipeAdapter(this, RecipeUtils.getRecipeNames(recipes)));
        setOnItemClickListener(recipeGV);
    }

    private void setOnItemClickListener(final GridView recipeGV) {
        recipeGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("onItemClick: ", recipes.get(i).getName());
                Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
                intent.putExtra(RECIPE, recipes.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(Call<List<Recipe>> call, Throwable t) {
        progress_bar_layout.setVisibility(View.INVISIBLE);
        Toast.makeText(this, getString(R.string.failure_message), Toast.LENGTH_LONG).show();
    }

    private class RecipeAdapter extends ArrayAdapter<String> {
        final private Context context;
        final private List<String> recipeList;

        public RecipeAdapter(Context context, List<String> recipes) {
            super(context, -1, recipes);
            this.context = context;
            recipeList = recipes;
        }

        @Override
        public int getCount() {
            if (null != recipeList)
                return recipeList.size();

            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View recipeCard = inflator.inflate(R.layout.recipe_card, parent, false);
            TextView recipeTV = recipeCard.findViewById(R.id.recipe_name);
            recipeTV.setText(recipeList.get(position));
            return recipeCard;
        }
    }

    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                if (null == recipes) {
                    progress_bar_layout.setVisibility(View.VISIBLE);
                    NetworkUtils.getRecipes(MainActivity.this);
                }

            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkReceiverRegistered) {
            if (null == networkReceiver)
                networkReceiver = new NetworkReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            registerReceiver(networkReceiver, filter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isNetworkReceiverRegistered) {
            unregisterReceiver(networkReceiver);
            isNetworkReceiverRegistered = false;
            networkReceiver = null;
        }
    }
}
