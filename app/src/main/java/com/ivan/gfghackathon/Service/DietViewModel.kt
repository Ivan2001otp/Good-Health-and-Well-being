package com.ivan.gfghackathon.Service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivan.gfghackathon.Model.Recipe
import java.nio.file.attribute.AclEntry.Builder

class DietViewModel: ViewModel() {
    private val _minProteins=MutableLiveData<Int>(2)
    private val _maxProteins = MutableLiveData<Int>(50)
    private val _minFats = MutableLiveData<Int>(10)
    private val _maxFats = MutableLiveData<Int>(55)
    private val _minCalories = MutableLiveData<Int>(33)
    private val _maxCalories = MutableLiveData<Int>(200)
    private val _minCarbs = MutableLiveData<Int>(10)
    private val _maxCarbs = MutableLiveData<Int>(100)

    private var _recipeList = MutableLiveData<List<Recipe>>()

    private val _transitionSwitch = MutableLiveData(false)//initially not switched to child fragment



    val transitionSwitch:LiveData<Boolean> = _transitionSwitch

    val minProteins:LiveData<Int> = _minProteins
    val maxProteins:LiveData<Int> = _maxProteins
    val minFats:LiveData<Int> = _minFats
    val maxFats :LiveData<Int> =_maxFats
    val minCalories:LiveData<Int> = _minCalories
    val maxCalories : LiveData<Int> = _maxCalories
    val minCarbs : LiveData<Int> = _minCarbs
    val maxCarbs : LiveData<Int> = _maxCarbs
    val recipeList : LiveData<List<Recipe>> = _recipeList


    fun onSwitchToChildFragment(switchValue:Boolean){
        _transitionSwitch.value = switchValue
    }

    fun setRecipeList(response:List<Recipe>){
        _recipeList = MutableLiveData(response)
    }

    fun setMaxProteinParams(mxProtien:Int){
        _maxProteins.value=mxProtien
    }

    fun setMinProteinParams(mnProtien:Int){
        _minProteins.value = mnProtien
    }

    fun setMinFatsParams(mnFat:Int){
        _minFats.value = mnFat
    }

    fun setMaxFatsParams(mxFat:Int){
        _maxFats.value = mxFat
    }

    fun setMinCalsParams(mnCals:Int){
        _minCalories.value = mnCals
    }

    fun setMaxCalsParams(mxCals:Int){
        _maxCalories.value = mxCals
    }

    fun setMaxCarbsParams(mxCarbs:Int){
        _maxCarbs.value = mxCarbs
    }

    fun setMinCarbsParams(mnCarbs:Int){
        _minCarbs.value = mnCarbs
    }

}