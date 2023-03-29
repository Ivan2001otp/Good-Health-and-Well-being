package com.ivan.gfghackathon.FragmentsHub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.fitness.Fitness
import com.ivan.gfghackathon.Model.ConnectivityObserver
import com.ivan.gfghackathon.Model.OnRecipeCheckClickListener
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.NetworkConnectivityObserver
import com.ivan.gfghackathon.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.sign


class HomeFragment : Fragment() {
private var _binding:FragmentHomeBinding?=null
private val binding get() = _binding!!
private lateinit var connnectivityObserver: ConnectivityObserver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connnectivityObserver = NetworkConnectivityObserver(requireActivity())

        connnectivityObserver.InternetObserver().onEach { result->
//            Toast.makeText(requireContext(),"Internet - $result",Toast.LENGTH_SHORT)
//                .show()
        }.launchIn(lifecycleScope)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        //free the binding
        _binding=null
    }


}