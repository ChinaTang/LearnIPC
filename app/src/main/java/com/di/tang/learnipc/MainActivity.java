package com.di.tang.learnipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int MSG_CLIENT = 0x123;

    private Button bn01, bn02;

    private Messenger messenger, activityMessenger;

    private static Handler activityHandle = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Log.d(TAG, "handleMessage: ");
            switch(msg.what){
                case 0x124:
                    Log.d(TAG, "handleMessage: " + msg.getData().get("msg"));
                    break;
                default:
                    Log.e(TAG, "handleMessage: " + "noMessage");
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            Message msg = Message.obtain(null, MSG_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello, service");
            msg.setData(bundle);
            msg.replyTo = activityMessenger;
            try{
                messenger.send(msg);
            }catch(RemoteException e){
                Log.e(TAG, "onServiceConnected: " + e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMessenger = new Messenger(activityHandle);
        bn01 = (Button)findViewById(R.id.bn01);
        bn02 = (Button)findViewById(R.id.bn02);
        bn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity01.class);
                startActivity(intent);
            }
        });
        bn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity02.class);
                startActivity(intent);
            }
        });
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbindService(mConnection);
    }
}
