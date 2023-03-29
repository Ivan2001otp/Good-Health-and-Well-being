package com.ivan.gfghackathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ivan.gfghackathon.databinding.ActivityMainBinding
import com.ivan.gfghackathon.set_activities.PlanActivity

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.splashBtn.setOnClickListener{v->
            val planActivityIntent = Intent(this,PlanActivity::class.java)
            startActivity(planActivityIntent)
        }

    }
}