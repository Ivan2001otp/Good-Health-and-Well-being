package com.ivan.gfghackathon.set_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ivan.gfghackathon.FragmentsHub.DietFragment
import com.ivan.gfghackathon.FragmentsHub.HomeFragment
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    var choice:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

       choice = intent.getIntExtra("planType",3)

        showFragment(HomeFragment())

       binding.bottomNavBarId.setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.home_nav->showFragment(HomeFragment())
                R.id.diet_food_list_nav->showFragment(DietFragment())
                else->{}
            }
         true
        }

        
    }

    fun showFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}