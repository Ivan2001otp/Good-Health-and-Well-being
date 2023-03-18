package com.ivan.gfghackathon.FragmentsHub

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.Service.ApiClient
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.Utils.ApiService
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import retrofit2.HttpException


class DietFragment : Fragment() {
private var _binding : FragmentDietBinding?=null
private val binding get() = _binding!!
private  val sharedViewModel: DietViewModel by activityViewModels()


    override fun onResume() {
        super.onResume()

        //checking internet connectivity
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager
//        val network = connectivityManager.activeNetwork
//        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
//
//        if(networkCapabilities!=null  && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
//
//        }else{
//
//        }





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



    }


    //parent fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.floatBtn.setOnClickListener{v->
            NutrientsSettingsFragment().show(requireActivity().supportFragmentManager,"dietFragment")
        }


        val service = ApiClient.getClient().create(ApiService::class.java)
        //run coroutine
        val filter = HashMap<String,Any>()
        filter.put("maxCarbs",sharedViewModel.maxCarbs.value!!)
        filter.put("minCarbs",sharedViewModel.minCarbs.value!!)
        filter.put("random",1)
        filter.put("maxFat",sharedViewModel.maxFats.value!!)
        filter.put("minFat",sharedViewModel.minFats.value!!)
        filter.put("minProtein",sharedViewModel.minProteins.value!!)
        filter.put("maxProtein",sharedViewModel.maxProteins.value!!)
       // filter.put("minCalories",sharedViewModel.minCalories.value!!)
       // filter.put("maxCalories",sharedViewModel.maxCalories.value!!)


        //converting json array to kotlin objects.





//        using coroutine
        val job = CoroutineScope(Dispatchers.IO).launch{
            Log.e("tag", "invoked coroutine", )



            val response : List<Recipe> = service.getRecipes(filter)
            Log.e("tag",response.get(0).calorie)
            Log.e("tag",response.get(0).title)
            Log.e("tag",response.get(0).image)

        /*
            try{

                val response = JSONArray(service.getRecipes(filter))
                System.err.println(response.toString())
                val recipeListType = object : TypeToken<List<Recipe>>(){}.type
                val recipeList:List<Recipe> = gson.fromJson(response.toString(),recipeListType)


                if(!recipeList.isEmpty()){
                    Log.d("tag", recipeList.get(0).image)
                    Log.d("tag", recipeList.get(1).title)
                    Log.d("tag", recipeList.get(0).calorie)
                    Log.e("tag", "fetched successfully")

                }else{
                    Log.e("tag", "error: list is empty")
                }
            }catch (e: HttpException){
                println(e.message)
                e.printStackTrace()
            }catch(e:Throwable){
                e.printStackTrace()
            }finally {
                //close the resource
            }*/
        }

        if(job.isActive){
            Log.e("tag", "active")
        }
        if(job.isCompleted) {
            Log.e("tag", "job completed!")
        }
        if(job.isCancelled){
            Log.e("tag", "job cancelled!")
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