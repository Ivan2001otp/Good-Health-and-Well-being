package com.ivan.gfghackathon.FragmentsHub

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import com.ivan.gfghackathon.databinding.FragmentNutrientsSettingsBinding
import com.ivan.gfghackathon.databinding.FragmentNutrientsSettingsBinding.*
import java.net.HttpCookie.parse
import java.time.Instant.parse


class NutrientsSettingsFragment : BottomSheetDialogFragment() {

private val sharedViewModel: DietViewModel by activityViewModels()

private var _binding:FragmentNutrientsSettingsBinding?=null
private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            //fetch the data from sharedViewModel and display in ui
            binding.btFragMinCalorieTv.setText(sharedViewModel.minCalories.value.toString())
            binding.btFragMaxCalorieTv.setText(sharedViewModel.maxCalories.value.toString())
            binding.btFragMaxProteinTv.setText(sharedViewModel.maxProteins.value.toString())
            binding.btFragMinProteinTv.setText(sharedViewModel.minProteins.value.toString())
            binding.btFragMinFatsTv.setText(sharedViewModel.minFats.value.toString())
            binding.btFragMaxFatsTv.setText(sharedViewModel.maxFats.value.toString())
            binding.btFragMaxCarbsTv.setText(sharedViewModel.maxCarbs.value.toString())
            binding.btFragMinCarbsTv.setText(sharedViewModel.minCarbs.value.toString())


        //listener for the save button
        binding.saveBtn.setOnClickListener{v->
            val minCalorie:String = binding.btFragMinCalorieTv.text.toString()
            val maxProtein:String = binding.btFragMaxProteinTv.text.toString()
            val minProtein :String = binding.btFragMinProteinTv.text.toString()
            val maxCalorie :String = binding.btFragMaxCalorieTv.text.toString()
            val minFat:String = binding.btFragMinFatsTv.text.toString()
            val maxFat:String = binding.btFragMaxFatsTv.text.toString()
            val maxCarbs : String = binding.btFragMaxCarbsTv.text.toString()
            val minCarbs:String = binding.btFragMinCarbsTv.text.toString()


            //uploading the input data to the sharedview model of this child fragment
            sharedViewModel.setMinCalsParams(minCalorie.toInt())
            sharedViewModel.setMaxCalsParams(maxCalorie.toInt())
            sharedViewModel.setMaxFatsParams(maxFat.toInt())
            sharedViewModel.setMinFatsParams(minFat.toInt())
            sharedViewModel.setMaxProteinParams(maxProtein.toInt())
            sharedViewModel.setMinProteinParams(minProtein.toInt())
            sharedViewModel.setMaxCarbsParams(maxCarbs.toInt())
            sharedViewModel.setMinCarbsParams(minCarbs.toInt())

            dismiss()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutrientsSettingsBinding.inflate(inflater,container,false)
        val ans = binding.btFragMinCalorieTv.text.toString()
        val bundle = Bundle()
        bundle.putString("minCal",ans)
        DietFragment().arguments=bundle
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}