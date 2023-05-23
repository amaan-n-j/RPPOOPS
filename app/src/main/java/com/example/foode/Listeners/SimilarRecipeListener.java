package com.example.foode.Listeners;

import com.example.foode.Models.SimilarRecipe;

import java.util.List;

public interface SimilarRecipeListener {
    void didfetch(List<SimilarRecipe> response , String message);
    void didError(String message);
}
