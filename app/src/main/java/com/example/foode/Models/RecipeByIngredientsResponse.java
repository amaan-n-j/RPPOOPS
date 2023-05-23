package com.example.foode.Models;

import java.util.ArrayList;

public class RecipeByIngredientsResponse {
    public int id;
    public String image;
    public String imageType;
    public int likes;
    public int missedIngredientCount;
    public ArrayList<MissedIngredient> missedIngredients;
    public String title;
    public ArrayList<UnusedIngredient> unusedIngredients;
    public int usedIngredientCount;
    public ArrayList<UsedIngredient> usedIngredients;
}
