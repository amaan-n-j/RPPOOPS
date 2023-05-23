package com.example.foode.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foode.Listeners.RecipeClickListener;
import com.example.foode.Models.SimilarRecipe;
import com.example.foode.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<similarRecipeViewHolder>{
    Context context;
    List<SimilarRecipe> list;
    RecipeClickListener listener;

    public SimilarRecipeAdapter(Context context, List<SimilarRecipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public similarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new similarRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull similarRecipeViewHolder holder, int position) {
        holder.similar_recipe_title.setText(list.get(position).title);
        holder.similar_recipe_title.setSelected(true);
        holder.similar_recipe_serving.setText(list.get(position).servings + " Persons");
        Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id+"-556x370."+list.get(position).imageType).into(holder.imageView_similar);

        holder.similar_recipe_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class similarRecipeViewHolder extends RecyclerView.ViewHolder{
    CardView similar_recipe_holder;
    TextView similar_recipe_title, similar_recipe_serving;
    ImageView imageView_similar;

    public similarRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        similar_recipe_holder = itemView.findViewById(R.id.similar_recipe_holder);
        similar_recipe_serving = itemView.findViewById(R.id.similar_recipe_serving);
        similar_recipe_title = itemView.findViewById(R.id.similar_recipe_title);
        imageView_similar = itemView.findViewById(R.id.imageView_similar);

    }
}
