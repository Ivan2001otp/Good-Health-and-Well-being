package com.ivan.gfghackathon.Data

import android.content.Context
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.ApiClient
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.Utils.ApiService
import kotlinx.coroutines.*

class RecipeRepository  {
     suspend fun getRecipeListNetworkCall(filter:HashMap<String,Any>) : List<Recipe> {
             val service = ApiClient.getClient().create(ApiService::class.java)
            val responseList:List<Recipe> =  service.getRecipes(filter)
            Log.d("tag", "repo : api invoked")
         return responseList
     }
}