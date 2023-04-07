package com.ivan.gfghackathon.set_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.ivan.gfghackathon.MainActivity
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.databinding.ActivityDonationBinding
import com.ivan.gfghackathon.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashBinding
    private var auth:FirebaseAuth ?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if(auth?.currentUser!=null){
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.getStarted.setOnClickListener{

            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}