package com.example.foode.Listeners;

import com.example.foode.Models.RandomRecipeResponse;

public interface RandomRecipeResponseListeners {
    void didFetch(RandomRecipeResponse response , String message);
    void didError(String message);
}
