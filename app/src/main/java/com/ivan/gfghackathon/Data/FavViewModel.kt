package com.ivan.gfghackathon.Data

import androidx.lifecycle.*
import com.ivan.gfghackathon.Model.FavRecipe
import kotlinx.coroutines.launch

class FavViewModel(private val repository: RoomRepository) :ViewModel(){

    fun getAllListItemsVM(): LiveData<List<FavRecipe>>{
        return repository.getItemListRepo().asLiveData()
    }

    fun insertModelVM(favRecipe: FavRecipe)=viewModelScope.launch {
        repository.insertFavouritesRepo(favRecipe)
    }

    fun deleteModelVM(favRecipe: FavRecipe)=viewModelScope.launch{
        repository.deleteFavouritesRepo(favRecipe)
    }
}

@Suppress("UNCHECKED_CAST")
class FavViewModelFactory(private val repository: RoomRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavViewModel::class.java)){
            return FavViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}