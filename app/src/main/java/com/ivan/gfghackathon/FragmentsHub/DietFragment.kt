package com.ivan.gfghackathon.FragmentsHub

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ivan.gfghackathon.Service.ApiClient
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.Utils.ApiService
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class DietFragment : Fragment() {
private var _binding : FragmentDietBinding?=null
private val binding get() = _binding!!
private  val sharedViewModel: DietViewModel by activityViewModels()


    override fun onResume() {
        super.onResume()

        sharedViewModel.minCalories.observe(viewLifecycleOwner,Observer<Any> {
            println(it)
           binding.minCaloriesTv.text = "minCals:"+sharedViewModel.minCalories.value.toString()
           binding.minProteinsTv.text = "MinProteins:"+sharedViewModel.minProteins.value.toString()

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
        filter.put("maxCarbs",100)
        filter.put("minCarbs",50)
        filter.put("random",1)
        filter.put("cuisine","")
        filter.put("diet","")

        CoroutineScope(Dispatchers.IO).launch{
            val response = service.getRecipes(filter)

            try{
                if(response.isSuccessful){
                    Log.e("tag", "fetched successfully")
                    Log.d("tag", "api data : "+response.body())
                }else{
                    Log.e("tag", "error: ${response.code()}" )
                }
            }catch (e: HttpException){
                println(e.message)
                e.printStackTrace()
            }catch(e:Throwable){
                e.printStackTrace()
            }finally {
                //close the resource
            }
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