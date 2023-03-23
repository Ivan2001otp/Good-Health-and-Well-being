package com.ivan.gfghackathon.AdapterService

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.FinalKeyAssets

class RecipeAdapter(var recipeList:List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item_recipe,parent,false)

        return RecipeViewHolder(view)
    }

    fun onItemListChanged(recipeList: List<Recipe>){
        this.recipeList = recipeList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.apply{
            this.findViewById<TextView>(R.id.row_carbs_tv).text =  buildString {
        append(FinalKeyAssets.Carbohydrates)
        append(recipeList.get(position).carbs)
    }
            this.findViewById<TextView>(R.id.row_fat_tv).text = buildString {
        append(FinalKeyAssets.Fats)
        append(recipeList.get(position).fats)
    }
            this.findViewById<TextView>(R.id.row_cal_tv).text = buildString {
        append(FinalKeyAssets.Calories)
        append(recipeList.get(position).calorie)
    }
            this.findViewById<TextView>(R.id.row_proteins_tv).text = buildString {
        append(FinalKeyAssets.proteins)
        append(recipeList.get(position).protein)
    }
            this.findViewById<TextView>(R.id.row_recipe_title_tv).text = recipeList.get(position).title

            val uri_string = Uri.parse(recipeList.get(position).image)

            Glide.with(holder.itemView.context)
                .load(uri_string)
                .placeholder(R.drawable.meal)
                .centerCrop()
                .into(this.findViewById(R.id.row_food_img))
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}