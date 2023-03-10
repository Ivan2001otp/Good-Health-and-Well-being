package com.ivan.gfghackathon.set_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import com.ivan.gfghackathon.Service.AssetDb
import com.ivan.gfghackathon.Service.DietPlansAdapter
import com.ivan.gfghackathon.databinding.ActivityPlanBinding

class PlanActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPlanBinding
    private lateinit var adapter : DietPlansAdapter
    private var choice:Int=-1

    var flag : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlanBinding.inflate(layoutInflater)

        setContentView(binding.root)
        intent = Intent(this,HomeActivity::class.java)


        adapter = DietPlansAdapter(this,AssetDb.categories,object:DietPlansAdapter.ClickListenerItem{
            override fun onItemClick(position: Int) {
                Log.d("tag", "onItemClick: $position")
                choice=position

                intent.putExtra("planType",position+1)
                startActivity(intent)
                finishAffinity()

            }
        })

        binding.plansListView.adapter=adapter

    }
//
//    private fun showDialogBox(planChoice:Int):AlertDialog.Builder{
//
//    //0-loose weight
//        //1-gain weight
//        //2 - healthy diet
//    val builder = AlertDialog.Builder(this)
//
//        builder.setTitle("Confirmation")
//        if(planChoice==0){
//            builder.setMessage("Do you opt to pick Loosing Calories plan ?")
//        }else if(planChoice==1){
//            builder.setMessage("Do you opt to pick Gaining Calories plan ?")
//        }else{
//            builder.setMessage("Do you opt to pick healthy diet plan ?")
//        }
//
//       return builder
//    }
}