package com.example.foode;

import android.content.Context;

import com.example.foode.Listeners.InstructionListener;
import com.example.foode.Listeners.RandomRecipeResponseListeners;
import com.example.foode.Listeners.RecipeByIngredientsListener;
import com.example.foode.Listeners.RecipeDetailListener;
import com.example.foode.Listeners.SimilarRecipeListener;
import com.example.foode.Models.InstructionResponse;
import com.example.foode.Models.RandomRecipeResponse;
import com.example.foode.Models.RecipeByIngredientsResponse;
import com.example.foode.Models.RecipeDetails;
import com.example.foode.Models.SimilarRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestHandler {
    Context context;
    Retrofit retorfit  = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestHandler(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListeners listener , List<String> tags){
        CallRandomRecipes callRandomRecipes = retorfit.create(CallRandomRecipes.class);
        Call<RandomRecipeResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10",tags);
        call.enqueue(new Callback<RandomRecipeResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeResponse> call, Response<RandomRecipeResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeByIngredients(RecipeByIngredientsListener listener, List<String> ingredients){
        CallRecipeIngredients callRecipeIngredients = retorfit.create(CallRecipeIngredients.class);
        Call<List<RecipeByIngredientsResponse>> call = callRecipeIngredients.callRecipeByIngredients(context.getString(R.string.api_key),ingredients);
        call.enqueue(new Callback<List<RecipeByIngredientsResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeByIngredientsResponse>> call, Response<List<RecipeByIngredientsResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<RecipeByIngredientsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void getRecipeDetals(RecipeDetailListener listener , int id){
        CallRecipeDetails callRecipeDetails = retorfit.create(CallRecipeDetails.class);
        Call<RecipeDetails> call = callRecipeDetails.callRecipeDetails(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetails>() {
            @Override
            public void onResponse(Call<RecipeDetails> call, Response<RecipeDetails> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetails> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSimialrRecipe(SimilarRecipeListener listener , int id){
        CallSimilarRecipe callSimilarRecipe = retorfit.create(CallSimilarRecipe.class);
        Call<List<SimilarRecipe>> call = callSimilarRecipe.callSimilarRecipe(id,"6", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipe>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipe>> call, Response<List<SimilarRecipe>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didfetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipe>> call, Throwable t) {
                    listener.didError(t.getMessage());
            }
        });
    }

    public void getInstruction(InstructionListener listener, int id){
        CallInstruction callInstrucion = retorfit.create(CallInstruction.class);
        Call<List<InstructionResponse>> call = callInstrucion.callInstruction(id,context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionResponse>> call, Response<List<InstructionResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeResponse> callRandomRecipe(
                @Query("apiKey") String apikey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    private interface CallRecipeIngredients {
        @GET("recipes/findByIngredients")
        Call<List<RecipeByIngredientsResponse>> callRecipeByIngredients(
            @Query("apiKey") String apikey,
            @Query("ingredients") List<String> ingredients
        );
    }

    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetails> callRecipeDetails(
            @Path("id") int id,
            @Query("apiKey") String apikey
        );

    }

    private interface CallSimilarRecipe{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipe>> callSimilarRecipe(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apikey
        );
    }

    private interface CallInstruction{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionResponse>> callInstruction(
                @Path("id") int id,
                @Query("apiKey") String apikey
        );
    }

}
