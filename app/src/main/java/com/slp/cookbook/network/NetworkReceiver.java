package com.slp.cookbook.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Lakshmiprasad on 08-09-2017.
 */

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       if(NetworkUtils.isNetworkAvailable(context)){

       }
    }


}


