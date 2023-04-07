package com.ivan.gfghackathon.set_activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ivan.gfghackathon.FragmentsHub.DietFragment
import com.ivan.gfghackathon.FragmentsHub.HomeFragment
import com.ivan.gfghackathon.MainActivity
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    var choice:Int = -1
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var viewModel: DietViewModel
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //instances

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //initializing shared view model
        viewModel = ViewModelProvider(this).get(DietViewModel::class.java)//here we gave the activity scope

       choice = intent.getIntExtra("planType",3)


        inflateNavigationDrawer()





        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.myFavourites->{
                    val intent: Intent = Intent(this,FavouritesActivity::class.java)
                   // intent.putExtra("db_inst",db.)
                    startActivity(intent)
                }

                R.id.donate_id->{
                    val donateIntent:Intent = Intent(this,DonationActivity::class.java)
                    startActivity(donateIntent)
                }

                R.id.logout->{
                    val builder = AlertDialog.Builder(this)
                        builder.setMessage("Do you want to signOut ?")
                            .setCancelable(true)
                            .setPositiveButton("Yes"){dialogInterface,it->
                                auth.signOut()
                                val intent = Intent(this@HomeActivity,MainActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                            }.setNegativeButton("No"){dialogInterface,it->

                            }.show()

                }

            }

            true
        }


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



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun inflateNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this,binding.drawableId,R.string.Open,R.string.Close)
        binding.drawableId.addDrawerListener(toggle)
        toggle.syncState()//ready to inflate
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    fun showFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    

}