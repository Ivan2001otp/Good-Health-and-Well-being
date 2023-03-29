@file:Suppress("UNREACHABLE_CODE")

package com.ivan.gfghackathon.set_activities

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivan.gfghackathon.AdapterService.FavouriteItemAdapter
import com.ivan.gfghackathon.Data.FavViewModel
import com.ivan.gfghackathon.Data.FavViewModelFactory
import com.ivan.gfghackathon.Model.FavApplication
import com.ivan.gfghackathon.Model.FavRecipe
import com.ivan.gfghackathon.Model.OnFavouritesClickListener
import com.ivan.gfghackathon.Utils.DialogBuilderUtil
import com.ivan.gfghackathon.databinding.ActivityFavouritesBinding

class FavouritesActivity : AppCompatActivity(),OnFavouritesClickListener {
    private lateinit var binding:ActivityFavouritesBinding
    private lateinit var recyclerViewAdapter:FavouriteItemAdapter

    private val favVM_Instance: FavViewModel by viewModels{
        FavViewModelFactory((application as FavApplication).repository)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appBar_ = supportActionBar
        appBar_!!.title = "Favourites"
        appBar_.setDisplayHomeAsUpEnabled(true)

        binding.favouritesRv.setHasFixedSize(true)
        val layout_mng = LinearLayoutManager(this)
        layout_mng.orientation = LinearLayoutManager.VERTICAL
        binding.favouritesRv.layoutManager = layout_mng

        favVM_Instance.getAllListItemsVM().observe(this, Observer { itemList->
            recyclerViewAdapter = FavouriteItemAdapter(this,itemList,this)
            binding.favouritesRv.adapter = recyclerViewAdapter
        })

    }

    override fun onClickDeleteFavourites(recipe: FavRecipe) {
      val dialogBuilder =   DialogBuilderUtil.DialogBoxBuilder(this,"Do you want to delete ${recipe.food_title} ?"
        ,"Deletion !",recipe,favVM_Instance,false)
        dialogBuilder.show()
    }

}