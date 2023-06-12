package com.soldis.yourthaitea.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by setia.n on 3/2/2017.
 */

public class AlarmReceiverAutoLogout extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newintent = new Intent(context, ServiceAutoLogOut.class);
        context.startService(newintent);
    }
}
