package com.ivan.gfghackathon.Model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {

    //save
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun onSaveDao(favRecipe: FavRecipe)

    @Delete
    suspend fun onDeleteDao(favRecipe: FavRecipe)

    @Query("SELECT * FROM Fav_Recipe ORDER BY timeMillis DESC")
    fun onGetDaoList() : Flow<List<FavRecipe>>

    @Query("DELETE FROM Fav_Recipe")
    suspend fun onDeleteAllDao()
}