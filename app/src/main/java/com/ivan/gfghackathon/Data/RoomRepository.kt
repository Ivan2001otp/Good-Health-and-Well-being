package com.ivan.gfghackathon.Data

import androidx.annotation.WorkerThread
import com.ivan.gfghackathon.Model.FavDao
import com.ivan.gfghackathon.Model.FavRecipe
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val dao: FavDao) {

    fun getItemListRepo(): Flow<List<FavRecipe>>{
        return dao.onGetDaoList()
    }

    @WorkerThread
    suspend fun insertFavouritesRepo(favRecipe: FavRecipe){
         dao.onSaveDao(favRecipe)
    }

    @WorkerThread
    suspend fun deleteFavouritesRepo(favRecipe: FavRecipe){
        dao.onDeleteDao(favRecipe)
    }
}