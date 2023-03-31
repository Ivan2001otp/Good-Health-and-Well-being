package com.ivan.gfghackathon.FragmentsHub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.gfghackathon.AdapterService.RecipeAdapter
import com.ivan.gfghackathon.Data.FavViewModel
import com.ivan.gfghackathon.Data.FavViewModelFactory
import com.ivan.gfghackathon.Data.RecipeRepository
import com.ivan.gfghackathon.Model.FavApplication
import com.ivan.gfghackathon.Model.FavRecipe
import com.ivan.gfghackathon.Model.OnRecipeCheckClickListener
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.Utils.DialogBuilderUtil
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap


class DietFragment : Fragment(),OnRecipeCheckClickListener {

private var _binding : FragmentDietBinding?=null
private val binding get() = _binding!!
private  val sharedViewModel: DietViewModel by activityViewModels()
private val repository:RecipeRepository = RecipeRepository()
private lateinit var recyclerAdapter:RecipeAdapter
private lateinit var responseList:List<Recipe>

private  val favListViewModel: FavViewModel by viewModels{
    FavViewModelFactory((requireActivity().application as FavApplication).repository)
}


override fun onResume() {
        super.onResume()

        Log.d("tag", "onResume: executed")

        sharedViewModel.minCalories.observe(viewLifecycleOwner,Observer<Any> {

           binding.minCaloriesTv.text = "minCals:"+sharedViewModel.minCalories.value.toString()
       })
        sharedViewModel.maxCalories.observe(viewLifecycleOwner,Observer{
            binding.maxCaloriesTv.text = "MaxCals:"+sharedViewModel.maxCalories.value.toString()
        })
        sharedViewModel.minCarbs.observe(viewLifecycleOwner,Observer{
            binding.minCarbsTv.text = "MinCarbs:"+sharedViewModel.minCarbs.value.toString()

        })
        sharedViewModel.maxCarbs.observe(viewLifecycleOwner,Observer{
            binding.maxCarbsTv.text = "MaxCarbs:"+sharedViewModel.maxCarbs.value.toString()

        })
        sharedViewModel.maxFats.observe(viewLifecycleOwner, Observer {
            binding.maxFatsTv.text = "MaxFats:"+sharedViewModel.maxFats.value.toString()

        })
        sharedViewModel.minFats.observe(viewLifecycleOwner,Observer{
            binding.minFatsTv.text = "MinFats:"+sharedViewModel.minFats.value.toString()

        })
        sharedViewModel.maxProteins.observe(viewLifecycleOwner,Observer{
            binding.maxProteinsTv.text = "MaxProteins:"+sharedViewModel.maxProteins.value.toString()

        })
        sharedViewModel.minProteins.observe(viewLifecycleOwner,Observer{
            binding.minProteinsTv.text = "MinProteins:"+sharedViewModel.minProteins.value.toString()

        })
        sharedViewModel.transitionSwitch.observe(viewLifecycleOwner){isTrue->
            if(isTrue){
                Log.d("tag", "You switched to child fragmt")
                sharedViewModel.onSwitchToChildFragment(false)
                val filter = HashMap<String,Any>()
                filter.put("maxProtein", sharedViewModel.maxProteins.value!!)
                filter.put("minProtein",sharedViewModel.minProteins.value!!)
                filter.put("minCarbs",sharedViewModel.minCarbs.value!!)
                filter.put("maxCarbs",sharedViewModel.maxCarbs.value!!)
                filter.put("minCalories",sharedViewModel.minCalories.value!!)
                filter.put("maxCalories",sharedViewModel.maxCalories.value!!)
                filter.put("minFat",sharedViewModel.minFats.value!!)
                filter.put("maxFat",sharedViewModel.maxFats.value!!)

                viewLifecycleOwner.lifecycleScope.launch(){
                    withTimeout(10000){
                        withContext(Dispatchers.IO){
                            try{
                                responseList = repository.getRecipeListNetworkCall(filter)
                            }catch (e : TimeoutCancellationException){
                                withContext(Dispatchers.Main){
                                    Toast.makeText(context,"Poor Internet or Server is temporarily overloaded !",Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        }
                    }

                    withContext(Dispatchers.Main){
                        if(!responseList.isEmpty()){
                            sharedViewModel.setRecipeList(responseList)
                            Log.d("tag", "in onresume - "+responseList.get(0).title)

                        }

                       recyclerAdapter.onItemListChanged(responseList)

                    }

                }

            }else{
                //false
                Log.d("tag", "You did not switched to child fragmt")

            }
        }

}

    //parent fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val filter = HashMap<String,Any>()
        filter.put("minProtein",sharedViewModel.minProteins.value!!)
        filter.put("maxProtein",sharedViewModel.maxProteins.value!!)
        filter.put("minCarbs",sharedViewModel.minCarbs.value!!)
        filter.put("maxCarbs",sharedViewModel.maxCarbs.value!!)
        filter.put("minCalories",sharedViewModel.minCalories.value!!)
        filter.put("maxCalories",sharedViewModel.maxCalories.value!!)
        filter.put("minFat",sharedViewModel.minFats.value!!)
        filter.put("maxFat",sharedViewModel.maxFats.value!!)

        Log.d("tag", "recycler view - 1")
        binding.recyclerViewFoodList.setHasFixedSize(true)
        val layout_manager = LinearLayoutManager(context)
        layout_manager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewFoodList.layoutManager = layout_manager

        responseList = listOf()
        var switch : Boolean = false
         viewLifecycleOwner.lifecycleScope.launch {
             Log.d("tag", "Going to coroutine-2")
               withContext(Dispatchers.IO){
                   Log.d("tag", "Going to coroutine-3")

                  withTimeout(10000){
                      Log.d("tag", "Going to coroutine-4")

                      try{
                          Log.d("tag", "Going to try - block")

                          responseList = repository.getRecipeListNetworkCall(filter)
                          Log.d("tag", "inside observer : "+responseList.get(0).title)

                      }catch (e : TimeoutCancellationException){
                          Log.d("tag", "Going to catch - block : ",e)
                          e.printStackTrace()
                          switch = true
                      }
                   }

                   withContext(Dispatchers.Main){
                       if(switch){
                           Toast.makeText(requireContext(),"Poor Internet or Server is temporarily overloaded !",Toast.LENGTH_LONG)
                               .show()
                           switch=false
                       }

                   }


                   withContext(Dispatchers.Main){
                       if(!responseList.isEmpty())
                       {      Log.d("tag", "list is not empty ")
                           sharedViewModel.setRecipeList(responseList)
                       }else{
                           Log.d("tag", "list is empty ")
                       }

                       Log.d("tag", "I am near observer !")
                       Log.d("tag", "inside observer : "+responseList.get(0).title)
                       sharedViewModel.recipeList.observe(viewLifecycleOwner){itemList->

                           recyclerAdapter = RecipeAdapter(requireContext(),itemList,this@DietFragment)
                           recyclerAdapter.onItemListChanged(itemList)
                           binding.recyclerViewFoodList.adapter = recyclerAdapter
                       }
                       Log.d("tag", "the size is : "+responseList.size)
                   }
               }

        }


        binding.floatBtn.setOnClickListener { v ->
            NutrientsSettingsFragment().show(
                requireActivity().supportFragmentManager,
                "dietFragment"
            )
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDietBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun getClickedRecipe(recipe: Recipe, adapterPosition: Int) {

        val message = "Do you want to add to your collection ?"
        val title = "FAVOURITES"
        val timeInMillis  = Calendar.getInstance().timeInMillis.toString()
        val obj:FavRecipe = FavRecipe(recipe.id,recipe.title,recipe.calorie,recipe.protein,recipe.carbs,recipe.fats,timeInMillis)
        val builder_dialog = DialogBuilderUtil.DialogBoxBuilder(requireContext(),message
                ,title,obj,favListViewModel,isSave = true)

        builder_dialog.show()
    }
}