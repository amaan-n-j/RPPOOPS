package com.example.foode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foode.Adapter.RandomRecipeAdapter;
import com.example.foode.Adapter.RecipeByIngredientsAdapter;
import com.example.foode.Listeners.RandomRecipeResponseListeners;
import com.example.foode.Listeners.RecipeByIngredientsListener;
import com.example.foode.Listeners.RecipeClickListener;
import com.example.foode.Models.RandomRecipeResponse;
import com.example.foode.Models.RecipeByIngredientsResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestHandler requestHandler;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    SearchView searchView;
    Button search_ingredients_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading ..");

        searchView = findViewById(R.id.search_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query.toLowerCase());
                requestHandler.getRandomRecipes(randomRecipeResponseListener,tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        spinner = findViewById(R.id.spinner_text);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        requestHandler = new RequestHandler(this);

        search_ingredients_button = findViewById(R.id.buttonView_ingredients);
        search_ingredients_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchByIngredientsActivity.class));
            }
        });


    }
    private final RandomRecipeResponseListeners randomRecipeResponseListener =  new RandomRecipeResponseListeners() {
        @Override
        public void didFetch(RandomRecipeResponse response, String message) {
            dialog.dismiss();
            recyclerView = findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this,response.recipes,recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };



    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            requestHandler.getRandomRecipes(randomRecipeResponseListener,tags);
            dialog.show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void OnRecipeClick(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id",id));
        }
    };
}