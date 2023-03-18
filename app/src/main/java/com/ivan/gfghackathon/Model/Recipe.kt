package com.ivan.gfghackathon.Model

import com.google.gson.annotations.SerializedName
import java.net.URI

data class Recipe(
    @SerializedName("id")
    val id:Double,

    @SerializedName("title")
    val title:String,

    @SerializedName("image")
    val image: String,

    @SerializedName("calories")
    val calorie:String,

    @SerializedName("protein")
    val protein:String,

    @SerializedName("fat")
    val fats:String,

    @SerializedName("carbs")
    val carbs:String
    )
