package com.ivan.gfghackathon.Utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

class UtilBuilders {
    fun hideSoftKeyboard(view: View){
        if(view!=null){
            val inputMethodManager:InputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
        }
    }
}