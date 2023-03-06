package com.ivan.gfghackathon.FragmentsHub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.databinding.FragmentDietBinding


class DietFragment : Fragment() {
private var _binding : FragmentDietBinding?=null
private val binding get() = _binding!!
private lateinit var sharedViewModel: DietViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(DietViewModel::class.java)
        binding.floatBtn.setOnClickListener{v->
            NutrientsSettingsFragment().show(requireActivity().supportFragmentManager,"dietFragment")
        }

        //we create observers to get live data

//        sharedViewModel.minCalories.observe(requireActivity()){
//            arguments?.getInt("minCal",30)?.let { it1 ->
//                binding.minCaloriesTv.setText(
//                    it1
//                )
//            }
//        }

        val ans = arguments?.getInt("maxCal",10)
        Log.e("tag", "respnse : $ans")
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