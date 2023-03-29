package com.ivan.gfghackathon.AdapterService

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivan.gfghackathon.FragmentsHub.DietFragment
import com.ivan.gfghackathon.Model.OnRecipeCheckClickListener
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.FinalKeyAssets

class RecipeAdapter(private val context: Context,
                    private var recipeList:List<Recipe>,
                    private val onRecipeCheckClickListener: OnRecipeCheckClickListener
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        init{
                itemView.findViewById<ImageButton>(R.id.row_add_fav).setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val p = adapterPosition
            val item:Recipe= recipeList.get(p)

            val view_id : Int = v?.id!!

            when(view_id){
                R.id.row_add_fav->onRecipeCheckClickListener.getClickedRecipe(item,p)
            }
        }
    }

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


