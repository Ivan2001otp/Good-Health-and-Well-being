package com.ivan.gfghackathon.AdapterService

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivan.gfghackathon.Model.FavDao
import com.ivan.gfghackathon.Model.FavRecipe
import com.ivan.gfghackathon.Model.OnFavouritesClickListener
import com.ivan.gfghackathon.Model.Recipe
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.FinalKeyAssets

class FavouriteItemAdapter(private val context: Context, private val list_fav:List<FavRecipe>
,private val onFavouritesClickListener: OnFavouritesClickListener)
    : RecyclerView.Adapter<FavouriteItemAdapter.ViewHolder>()
{
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        //set the onclick listener.
        init{
            itemView.findViewById<ImageView>(R.id.delete_img).setOnClickListener(this)
        }
        override fun onClick(position: View?) {
                 val obj:FavRecipe = list_fav.get(adapterPosition)

                val view_id = position!!.id

            when(view_id){
                R.id.delete_img -> onFavouritesClickListener.onClickDeleteFavourites(obj)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(context)
           .inflate(R.layout.todo_favourites,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply{
            this.findViewById<TextView>(R.id.row_fav_food_title).text = list_fav.get(position).food_title
            this.findViewById<TextView>(R.id.row_fav_cal_tv).text = buildString {
                append(FinalKeyAssets.Calories)
                append(list_fav.get(position).calories)
            }

            this.findViewById<TextView>(R.id.row_fav_carbs_tv).text = buildString {
                append(FinalKeyAssets.aliterCarbs)
                append(list_fav.get(position).carbs)
            }

            this.findViewById<TextView>(R.id.row_fav_prot_tv).text = buildString {
                append(FinalKeyAssets.proteins)
                append(list_fav.get(position).proteins)
            }

            this.findViewById<TextView>(R.id.row_fav_fats_tv).text = buildString {
                append(FinalKeyAssets.Fats)
                append(list_fav.get(position).fats)
            }

        }
    }

    override fun getItemCount(): Int {
       return list_fav.count()
    }
}