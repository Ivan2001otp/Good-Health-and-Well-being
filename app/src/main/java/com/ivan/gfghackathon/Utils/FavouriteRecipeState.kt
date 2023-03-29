package com.ivan.gfghackathon.Utils

import com.ivan.gfghackathon.Model.FavRecipe
import com.ivan.gfghackathon.Service.Sort_Type

data class FavouriteRecipeState(
    val fav_list:List<FavRecipe> = emptyList()
    ,val sortType: Sort_Type = Sort_Type.RECIPE_NAME
)