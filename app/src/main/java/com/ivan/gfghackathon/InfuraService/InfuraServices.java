package com.ivan.gfghackathon.InfuraService;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;

public class InfuraServices {
    private Web3j web3;
    public void makeTransaction(Context context, View v) throws Exception{
        //take the value from et
        //conver to string
        String key = InfureAssets.InfuraKeyApi;
        web3 = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/key"));

    }

    public void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
