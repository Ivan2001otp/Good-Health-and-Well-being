package com.ivan.gfghackathon.FragmentsHub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.gfghackathon.AdapterService.RecipeAdapter
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.ApiClient
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.Utils.ApiService
import com.ivan.gfghackathon.Utils.HelperViewModel
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DietFragment : Fragment() {

private var _binding : FragmentDietBinding?=null
private val binding get() = _binding!!
private  val sharedViewModel: DietViewModel by activityViewModels()

private lateinit var recyclerAdapter:RecipeAdapter


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
        sharedViewModel.transitionSwitch.observe(viewLifecycleOwner,Observer{
            Log.d("tag", "parent frag onviewCreate: "+sharedViewModel.transitionSwitch.value)
        })
/*
        sharedViewModel.transitionSwitch.observe(viewLifecycleOwner,Observer{
            Log.d("tag", "onResume after fetching : $it")
            if(it){
                //launch a coroutine and fetch the content into the recycler view.


                //set the filter
                val filter = HashMap<String,Any>()
                filter.put("minProtein",sharedViewModel.minProteins.value!!)
                filter.put("maxProtein",sharedViewModel.maxProteins.value!!)
                val service = ApiClient.getClient().create(ApiService::class.java)
                //launch a coroutine
                val j =  CoroutineScope(Dispatchers.IO).launch{
                    response = service.getRecipes(filter)
                }

                if(j.isCompleted){
                    sharedViewModel.setRecipeList(response)
                    Log.d("tag", "job completed ")
                }

                Log.d("tag", "onResume: "+response.get(0).title)
                sharedViewModel.onSwitchToChildFragment(false)//-->this to make prepare for the next switch.
            }
        })*/


}



    //parent fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val filter = HashMap<String,Any>()
        filter.put("minProtein",sharedViewModel.minProteins.value!!)
        filter.put("maxProtein",sharedViewModel.maxProteins.value!!)

        val service = ApiClient.getClient().create(ApiService::class.java)
        HelperViewModel().getListFromApibyCoroutine(service,filter,sharedViewModel)

        Log.d("tag", "after api call : "+sharedViewModel.recipeList.value?.get(0)?.image)
        binding.recyclerViewFoodList.setHasFixedSize(true)
        binding.recyclerViewFoodList.layoutManager = LinearLayoutManager(context)



        sharedViewModel.recipeList.observe(viewLifecycleOwner) {response_list->
            Log.d("tag", "onViewCreated: observer executed")

            recyclerAdapter = RecipeAdapter(response_list)
            binding.recyclerViewFoodList.adapter = recyclerAdapter
        }

        //launch a coroutine
       /* val j =  CoroutineScope(Dispatchers.IO).launch{


            binding.recyclerViewFoodList.setHasFixedSize(true)
            binding.recyclerViewFoodList.layoutManager = LinearLayoutManager(requireActivity())
            recyclerAdapter = RecipeAdapter(response)
            binding.recyclerViewFoodList.adapter = recyclerAdapter

            for(item in response){
                Log.d("tag", "api response: "+item.title)
                Log.d("tag", "api response: "+item.image)
            }
        }*/




        /*
        sharedViewModel.recipeList.observe(viewLifecycleOwner,Observer{
            Log.d("tag", "onViewCreated: ${it.get(0).id}")
            recyclerAdapter = RecipeAdapter(it)
            binding.recyclerViewFoodList.adapter = recyclerAdapter
//            recyclerAdapter.notifyDataSetChanged()
        })*/



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