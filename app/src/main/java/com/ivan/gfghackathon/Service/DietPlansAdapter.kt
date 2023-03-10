package com.ivan.gfghackathon.Service

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ivan.gfghackathon.Model.PlansCategory
import com.ivan.gfghackathon.R

class DietPlansAdapter(_context: Context,assetList:List<PlansCategory>,_listener:ClickListenerItem): BaseAdapter() {

    val context = _context
    val plansListView = assetList
    val listener = _listener

    override fun getCount(): Int {
        return plansListView.count()
    }

    override fun getItem(position: Int): PlansCategory {
        return plansListView[position]
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val plansView : View
        val holder : ViewHolder


        if(convertView==null){
            plansView = LayoutInflater.from(context).inflate(R.layout.todo_list_plans_view,null,false)
            holder = ViewHolder()
            holder.dietPlanName  = plansView.findViewById(R.id.todo_plan_name_tv)
            holder.dietImage = plansView.findViewById(R.id.todo_diet_plan_img)

            plansView.tag = holder
        }else{
            holder = convertView.tag as ViewHolder
            plansView = convertView
        }


        //set the text view content and the image to the image view
        val plans_category_obj = getItem(position)
        holder.dietPlanName?.text=(plans_category_obj.dietPlanName)
        val resourceId = context.resources.getIdentifier(plans_category_obj.dietImage,"drawable",context.packageName)
        holder.dietImage?.setImageResource(resourceId)


        plansView.setOnClickListener(View.OnClickListener {
            listener.onItemClick(position)
        })

        return plansView
    }

    private class ViewHolder(){
        var dietPlanName: TextView?=null
        var dietImage : ImageView?=null

    }

    interface ClickListenerItem{
        fun onItemClick(position:Int):Unit
    }
}







