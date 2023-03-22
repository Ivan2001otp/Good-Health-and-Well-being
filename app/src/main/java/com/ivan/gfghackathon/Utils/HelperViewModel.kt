package com.ivan.gfghackathon.Utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.DietViewModel
import kotlinx.coroutines.launch

class HelperViewModel : ViewModel() {
    private lateinit var responseList : List<Recipe>


    fun getListFromApibyCoroutine(service: ApiService,filter: HashMap<String, Any>, sharedViewModel: DietViewModel){
        Log.d("tag", "helper view model invoked")
        responseList = listOf<Recipe>()

        viewModelScope.launch {
            Log.d("tag", "helper view model - inside the coroutine")
            responseList = service.getRecipes(filter)
            sharedViewModel.setRecipeList(responseList)
            //here the list is not empty
        }

        //here , if i print the list , its empty.!!!
        Log.d("tag", "helper view model terminated")
    }
}