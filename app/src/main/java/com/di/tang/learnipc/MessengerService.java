package com.di.tang.learnipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by tangdi on 2016/9/9.
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static Messenger fromActvivtyMessenger;

    private static Handler Message = new Handler(){

        @Override
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 0x123:
                    Log.d(TAG, "handleMessage: " + "msgFromMainActivity" + 0x123);
                    fromActvivtyMessenger = msg.replyTo;
                    android.os.Message replyMsg = android.os.Message.obtain(null, 0x124);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "the message Form MessengerService");
                    replyMsg.setData(bundle);
                    try{
                        fromActvivtyMessenger.send(replyMsg);
                    }catch(RemoteException e){
                        Log.e(TAG, "handleMessage: " + e.toString());
                    }
                    break;
                default:
                    Log.e(TAG, "handleMessage: " + "no message");
            }

        }
    };

    private final Messenger messenger = new Messenger(Message);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
