package com.example.root.traceme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Al Imran Suvro on 2/17/16.
 */
public class StartUpService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Intent i=new Intent(context,GpsService.class);
            Toast.makeText(context, "TraceME Started", Toast.LENGTH_SHORT).show();
            context.startService(i);
        }
    }
}
