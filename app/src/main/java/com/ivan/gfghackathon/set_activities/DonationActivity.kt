package com.ivan.gfghackathon.set_activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ivan.gfghackathon.R
import com.ivan.gfghackathon.Service.FinalKeyAssets
import com.ivan.gfghackathon.databinding.ActivityDonationBinding
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class DonationActivity : AppCompatActivity(),PaymentResultListener{
    private lateinit var binding:ActivityDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBar_ = supportActionBar
        appBar_!!.title = "Aid to Eliminate Hunger"
        appBar_.setDisplayHomeAsUpEnabled(true)


        Checkout.preload(applicationContext)

        binding.sendBtn.setOnClickListener { view->
            val res : String = binding.donationEt.text.toString().trim()
            if(!res.isBlank()){
                val amt : Int = res.toInt()
                startPayment(amt)
            }else{
                Toast.makeText(this,"Provide your donation please.",Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    private fun startPayment(amount:Int){
        val co =Checkout()
        co.setKeyID(FinalKeyAssets.RazorPayApiKey)

        try{
            val options = JSONObject()
            options.put("name","Fitness & Health")
            options.put("currency","INR")
            options.put("amount",amount*100)
            options.put("image",R.drawable.healthcare)

            val retryObj = JSONObject()
            retryObj.put("max_count",4)
            retryObj.put("enabled",true)

            options.put("retry",retryObj)
            co.open(this@DonationActivity,options)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,p0,Toast.LENGTH_SHORT)
            .show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,p1,Toast.LENGTH_SHORT)
            .show()
    }


}

