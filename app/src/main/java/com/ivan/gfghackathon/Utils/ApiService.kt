package com.ivan.gfghackathon.Utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ivan.gfghackathon.Model.Recipe
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {

    // https://api.spoonacular.com/recipes/findByNutrients
    @GET("/recipes/findByNutrients?random=true&apiKey=50e38fd0beff49a0add73c7a30409c4d")
    suspend fun getRecipes(@QueryMap filter:HashMap<String,Any>): List<Recipe>
//@query map is used when more than two params
//@query parameter can be used to send two params
//@path can be used to send only one parameter
//    suspend fun getRecipes(@Path("minProtein_no")minProtein_no:Int,@Path("maxProtein_no") maxProtein_no:Int,@Path("bool_val") bool_val:Boolean): Response<Any>
//    https://api.spoonacular.com/recipes/716429/information?apiKey=YOUR-API-KEY&includeNutrition=true.
}