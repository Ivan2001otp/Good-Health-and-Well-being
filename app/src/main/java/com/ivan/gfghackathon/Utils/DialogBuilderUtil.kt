package com.ivan.gfghackathon.Utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import com.ivan.gfghackathon.Data.FavViewModel
import com.ivan.gfghackathon.Model.FavRecipe

class DialogBuilderUtil {
    companion object{
        private lateinit var builder: Builder
        fun DialogBoxBuilder(context:Context,message:String,title:String,recipe: FavRecipe,
                             view_model:FavViewModel,isSave:Boolean) : AlertDialog.Builder{
            builder = AlertDialog.Builder(context)

            builder.setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("yes"){dialogInterface,it->
                    //invoke the save
                        //view_model.onEvent(FavouriteRecipeEvent.SaveFavourite_(recipe))
                    if(isSave){
                        view_model.insertModelVM(recipe)
                        Toast.makeText(context,"Saved Successfully",Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        view_model.deleteModelVM(recipe)
                        Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT)
                            .show()
                    }

                }.setNegativeButton("no"){dialogInterface,it->

                }
            return builder
        }
    }


}