package com.example.foode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foode.Adapter.RecipeByIngredientsAdapter;
import com.example.foode.Listeners.RecipeByIngredientsListener;
import com.example.foode.Listeners.RecipeClickListener;
import com.example.foode.Models.RecipeByIngredientsResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchByIngredientsActivity extends AppCompatActivity {
    int id;
    RequestHandler requestHandler;
    ProgressDialog dialog;
    RecipeByIngredientsAdapter recipeByIngredientsAdapter;
    RecyclerView recycler_by_ingredients;
    List<String> ingredients = new ArrayList<>();
    SearchView searchView_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_ingredients);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading ..");

        searchView_ingredients = findViewById(R.id.ingredientSearch);
        searchView_ingredients.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ingredients.clear();
                ingredients.add(query.toLowerCase());
                requestHandler.getRecipeByIngredients(recipeByIngredientsListener,ingredients);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        requestHandler = new RequestHandler(this);


    }

    private final RecipeByIngredientsListener recipeByIngredientsListener = new RecipeByIngredientsListener() {
        @Override
        public void didFetch(List<RecipeByIngredientsResponse> response, String message) {
            dialog.dismiss();
            recycler_by_ingredients = findViewById(R.id.recycler_by_ingredients);
            recycler_by_ingredients.setHasFixedSize(true);
            recycler_by_ingredients.setLayoutManager(new GridLayoutManager(SearchByIngredientsActivity.this,1));
            recipeByIngredientsAdapter = new RecipeByIngredientsAdapter(SearchByIngredientsActivity.this,response,recipeClickListener);
            recycler_by_ingredients.setAdapter(recipeByIngredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SearchByIngredientsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void OnRecipeClick(String id) {
            startActivity(new Intent(SearchByIngredientsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id",id));
        }
    };
}
