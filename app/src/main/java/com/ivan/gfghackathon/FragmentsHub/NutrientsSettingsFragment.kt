package com.ivan.gfghackathon.FragmentsHub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.databinding.FragmentDietBinding
import com.ivan.gfghackathon.databinding.FragmentNutrientsSettingsBinding
import com.ivan.gfghackathon.databinding.FragmentNutrientsSettingsBinding.*
import java.net.HttpCookie.parse
import java.time.Instant.parse


class NutrientsSettingsFragment : BottomSheetDialogFragment() {

private lateinit var sharedViewModel: DietViewModel
private var _binding:FragmentNutrientsSettingsBinding?=null
private val binding get() = _binding!!
    var minCal:Int=0
    var maxCal:Int=0
   /* var minPro = 0
    var maxPro=0
    var minFat=0
    var maxFat=0
    var minCarb=0
    var maxCarb=0
*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(DietViewModel::class.java)

        binding.saveBtn.setOnClickListener{v->
            //all params are non empty
            Log.e("tag", "onViewCreated: $minCal" )
            var temp = binding.btFragMinCalorieTv.text.toString()

            minCal = temp.toInt()
            temp = binding.btFragMaxCalorieTv.text.toString()
            maxCal = temp.toInt()
            println(minCal)
            println(maxCal)
            /*minPro = binding.btFragMinProteinTv.text.toString().toInt()
            maxPro = binding.btFragMaxProteinTv.text.toString().toInt()
            maxFat = binding.btFragMaxFatsTv.text.toString().toInt()
            minFat = binding.btFragMinFatsTv.text.toString().toInt()
            minCarb = binding.btFragMinCarbsTv.text.toString().toInt()
            maxCarb = binding.btFragMaxCarbsTv.text.toString().toInt()
*/
            sharedViewModel.setMaxCalsParams(maxCal)
            sharedViewModel.setMinCalsParams(minCal)
          /*  sharedViewModel.setMaxProteinParams(maxPro)
            sharedViewModel.setMinProteinParams(minPro)
            sharedViewModel.setMaxFatsParams(maxFat)
            sharedViewModel.setMinProteinParams(minFat)
            sharedViewModel.setMaxCarbsParams(maxCarb)
            sharedViewModel.setMaxCarbsParams(minCarb)
*/
            val bundle=Bundle()
            setToBundle(bundle)

            dismiss()
        }
    }

    private fun setToBundle(bundle:Bundle) {
        bundle.putInt("maxCal",maxCal)
        bundle.putInt("minCal",minCal)
        val targetFragment = DietFragment()
        targetFragment.arguments = bundle
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutrientsSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}