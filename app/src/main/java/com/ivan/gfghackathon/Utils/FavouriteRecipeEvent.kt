package com.ivan.gfghackathon.Utils

import com.ivan.gfghackathon.Model.FavRecipe
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.Sort_Type

sealed interface FavouriteRecipeEvent{
    //save
        data class SaveFavourite_(val recipe: Recipe):FavouriteRecipeEvent
    //delete
        data class DeleteFavourites(val favRecipe: FavRecipe) : FavouriteRecipeEvent

    //sorting type
        data class SortFavourites(val sortType:Sort_Type):FavouriteRecipeEvent
}