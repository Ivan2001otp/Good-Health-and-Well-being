package com.ivan.gfghackathon.FragmentsHub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.gfghackathon.AdapterService.RecipeAdapter
import com.ivan.gfghackathon.Data.RecipeRepository
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import kotlinx.coroutines.*


class DietFragment : Fragment() {

private var _binding : FragmentDietBinding?=null
private val binding get() = _binding!!
private  val sharedViewModel: DietViewModel by activityViewModels()
private val repository:RecipeRepository = RecipeRepository()
private lateinit var recyclerAdapter:RecipeAdapter
private lateinit var responseList:List<Recipe>

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


                viewLifecycleOwner.lifecycleScope.launch(){
                    withTimeout(4000){
                        withContext(Dispatchers.IO){
                            try{
                                responseList = RecipeRepository().getRecipeListNetworkCall(filter)
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


        Log.d("tag", "onViewCreated executed ")
        val filter = HashMap<String,Any>()
        filter.put("minProtein",sharedViewModel.minProteins.value!!)
        filter.put("maxProtein",sharedViewModel.maxProteins.value!!)

        binding.recyclerViewFoodList.setHasFixedSize(true)
        binding.recyclerViewFoodList.layoutManager = LinearLayoutManager(context)

        responseList = listOf()


        val job =  viewLifecycleOwner.lifecycleScope.launch {

               withContext(Dispatchers.IO){
                  withTimeout(5000){
                      try{
                          responseList = RecipeRepository().getRecipeListNetworkCall(filter)
                      }catch (e : TimeoutCancellationException){
                          withContext(Dispatchers.Main){
                              Toast.makeText(context,"Poor Internet or Server is temporarily overloaded !",Toast.LENGTH_LONG)
                                  .show()
                          }

                      }
                   }

                   withContext(Dispatchers.Main){

                       if(!responseList.isEmpty())
                       {
                           sharedViewModel.setRecipeList(responseList)
                       }else{
                           Log.d("tag", "list is empty ")

                       }
                       sharedViewModel.recipeList.observe(viewLifecycleOwner){itemList->

                           Log.d("tag", "inside observer : "+responseList.get(0).title)
                            recyclerAdapter = RecipeAdapter(itemList)
                            recyclerAdapter.onItemListChanged(itemList)
                            binding.recyclerViewFoodList.adapter = recyclerAdapter
                           /*
                           if(recyclerAdapter.itemCount==0){
                               Log.d("anim", "loadLottieAnimation: playing")
                               if(binding.loadingAnimation.visibility == View.GONE){
                                   binding.loadingAnimation.visibility = View.VISIBLE
                                   binding.loadingAnimation.playAnimation()
                               }
                               Toast.makeText(requireActivity(),"Loading ,please Wait !",Toast.LENGTH_SHORT)
                                   .show()
                           }else{
                               Log.d("anim", "loadLottieAnimation: cancelled")
                               if(binding.loadingAnimation.visibility==View.VISIBLE){
                                   binding.loadingAnimation.visibility = View.GONE
                                   binding.loadingAnimation.cancelAnimation()
                               }

                           }*/
                       }
                       Log.d("tag", "onViewCreated: "+responseList.get(0).image)
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
}