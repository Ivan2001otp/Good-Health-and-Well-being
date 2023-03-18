package com.ivan.gfghackathon.Service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private  var retrofit:Retrofit?=null

    fun getClient():Retrofit{
        if(retrofit==null){
//
          /* val okHttpClient = OkHttpClient.Builder()
               .addInterceptor{chain->
                   val request = chain.request()
                       .newBuilder()
                       .addHeader("Authorization","Bearer ${FinalKeyAssets.SpoonacularApiKey}")
                       .build()
                   chain.proceed(request)
            }.build()*/

           retrofit = Retrofit.Builder()
               .baseUrl("https://api.spoonacular.com")
               //.client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
        }

      return retrofit!!//non nullable
    }
}