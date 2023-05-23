package com.example.foode.Listeners;

import com.example.foode.Models.RecipeByIngredientsResponse;

import java.util.List;

public interface RecipeByIngredientsListener {
    void didFetch(List<RecipeByIngredientsResponse> response, String message);
    void didError(String message);

}
