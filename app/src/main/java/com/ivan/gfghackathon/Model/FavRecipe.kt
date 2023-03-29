package com.ivan.gfghackathon.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Fav_Recipe")
data class FavRecipe(
    val v_id:Long,
    val food_title:String,
    val calories:String,
    val proteins:String,
    val carbs:String,
    val fats:String,
    val timeMillis:String,
    @PrimaryKey(autoGenerate = true)
    val p_id:Int=0
)
