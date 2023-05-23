package com.example.foode.Listeners;

import com.example.foode.Models.Recipe;
import com.example.foode.Models.RecipeDetails;

public interface RecipeDetailListener {
    void didFetch(RecipeDetails response, String message);
    void didError(String message);
}
