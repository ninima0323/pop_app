package com.example.kimnahyeon.pop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kimnahyeon on 2017. 7. 23..
 */

public class ScreenReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if(Manager.isAlways==true){
            System.out.println(intent.getAction());
            if (intent.getAction().equals(Intent.ACTION_USER_PRESENT))
            {
                Intent intent1 = new Intent(context,MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }

    }
}
