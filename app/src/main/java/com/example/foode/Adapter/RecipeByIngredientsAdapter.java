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
import com.example.foode.Models.RecipeByIngredientsResponse;
import com.example.foode.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeByIngredientsAdapter  extends RecyclerView.Adapter<RecipeByIngredientsViewHolder>{
    Context context;
    List<RecipeByIngredientsResponse> list;
    RecipeClickListener listener;

    public RecipeByIngredientsAdapter(Context context, List<RecipeByIngredientsResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeByIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeByIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_recipe_by_ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeByIngredientsViewHolder holder, int position) {
        holder.search_ingredients_title.setText(list.get(position).title);
        holder.search_ingredients_title.setSelected(true);
        Picasso.get().load(list.get(position).image).into(holder.imageView_search_ingredients);

        holder.ingredient_by_Search_holder.setOnClickListener(new View.OnClickListener() {
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

class RecipeByIngredientsViewHolder extends RecyclerView.ViewHolder{

    CardView ingredient_by_Search_holder;
    TextView search_ingredients_title;
    ImageView imageView_search_ingredients;

    public RecipeByIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ingredient_by_Search_holder = itemView.findViewById(R.id.ingredient_by_Search_holder);
        search_ingredients_title = itemView.findViewById(R.id.search_ingredients_title);
        imageView_search_ingredients = itemView.findViewById(R.id.imageView_search_ingredients);
    }
}
