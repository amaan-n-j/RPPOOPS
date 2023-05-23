package com.example.foode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foode.Adapter.InstructionAdapter;
import com.example.foode.Adapter.InstructionStepsAdapter;
import com.example.foode.Adapter.SimilarRecipeAdapter;
import com.example.foode.Listeners.InstructionListener;
import com.example.foode.Listeners.RecipeClickListener;
import com.example.foode.Listeners.RecipeDetailListener;
import com.example.foode.Listeners.SimilarRecipeListener;
import com.example.foode.Models.InstructionResponse;
import com.example.foode.Models.ProductMatch;
import com.example.foode.Models.RecipeDetails;
import com.example.foode.Models.SimilarRecipe;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView textView_recipe_name , textView_recipe_source,textView_recipe_summary;
    ImageView imageView_food_image;
    RecyclerView recycler_recipe_similar,recycler_instruction;
    RequestHandler requestHandler;
    ProgressDialog dialog;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionAdapter instructionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));

        requestHandler = new RequestHandler(this);

        requestHandler.getRecipeDetals(recipeDetailListener,id);
        requestHandler.getSimialrRecipe(similarRecipeListener,id);
        requestHandler.getInstruction(instructionListener,id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details");
        dialog.show();

    }

    private void findViews() {
        textView_recipe_name = findViewById(R.id.textView_recipe_name);
        textView_recipe_source = findViewById(R.id.textView_recipe_source);
        textView_recipe_summary = findViewById(R.id.textView_recipe_summary);
        imageView_food_image = findViewById(R.id.imageView_food_image);
        recycler_recipe_similar = findViewById(R.id.recycler_recipe_similar);
        recycler_instruction = findViewById(R.id.recycler_instruction);
    }

    private final RecipeDetailListener recipeDetailListener = new RecipeDetailListener() {
        @Override
        public void didFetch(RecipeDetails response, String message) {
            dialog.dismiss();
            textView_recipe_name.setText(response.title);
            textView_recipe_source.setText(response.sourceName);
            String html = String.valueOf(response.summary);
            String data = html.replaceAll("\\<.*?>","");
            textView_recipe_summary.setText(data);
            Picasso.get().load(response.image).into(imageView_food_image);


        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };

    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didfetch(List<SimilarRecipe> response, String message) {
            recycler_recipe_similar.setHasFixedSize(true);
            recycler_recipe_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL , false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this,response,recipeClickListener);
            recycler_recipe_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void OnRecipeClick(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this,RecipeDetailsActivity.class)
                    .putExtra("id",id));
        }
    };

    private final InstructionListener instructionListener = new InstructionListener() {
        @Override
        public void didFetch(List<InstructionResponse> response, String message) {
                recycler_instruction.setHasFixedSize(true);
                recycler_instruction.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL,false));
                instructionAdapter = new InstructionAdapter(RecipeDetailsActivity.this,response);
                recycler_instruction.setAdapter(instructionAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };

}