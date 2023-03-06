package com.ivan.gfghackathon.set_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ivan.gfghackathon.FragmentsHub.DietFragment
import com.ivan.gfghackathon.FragmentsHub.HomeFragment
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.ApiClient
import com.ivan.gfghackathon.Service.DietViewModel
import com.ivan.gfghackathon.Utils.ApiService
import com.ivan.gfghackathon.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    var choice:Int = -1
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var viewModel: DietViewModel

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
                R.id.theme_type->{
                    Log.e("tag", "theme ")
                }
                R.id.filter_diet_id->{
                    Log.e("tag", "filter diet ")
                }
                R.id.donate_id->{
                }

            }

            true
        }


        //working with retrofit
        val service = ApiClient.getClient().create(ApiService::class.java)
        //run coroutine
        val filter = HashMap<String,Int>()
        filter.put("maxCarbs",100)
        filter.put("minCarbs",50)
        filter.put("random",0)
        CoroutineScope(Dispatchers.IO).launch{
            val response = service.getRecipes(filter)

            try{
                if(response.isSuccessful){
                    Log.e("tag", "fetched successfully")
                    Log.d("tag", "api data : "+response.body())
                }else{
                    Log.e("tag", "error: ${response.code()}" )
                }
            }catch (e:HttpException){
                println(e.message)
                e.printStackTrace()
            }catch(e:Throwable){
                e.printStackTrace()
            }finally {
               //close the resource
            }
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