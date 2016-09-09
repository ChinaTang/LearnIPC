package com.di.tang.learnipc;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by tangdi on 2016/9/8.
 */
public class MainActivity01 extends AppCompatActivity {

    private static final String PATH = "mainactivity01.text";

    private static final String TAG = "MainActivity01";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    @Override
    public void onResume() {
        super.onResume();
        persistToFile();
    }

    private void persistToFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(1, "Hello world", false);
                boolean haveSD = Environment.getExternalStorageDirectory().
                        equals(Environment.MEDIA_MOUNTED)? true : false;
                File dir = new File(Environment.getExternalStorageDirectory()
                        + File.separator + PATH);
                if(!dir.exists()){
                    boolean createfile = dir.mkdirs();
                }
                Log.d(TAG, "run: " + dir.getAbsolutePath().toString());

                ObjectOutputStream objectOutputStream = null;
                try{
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(dir));
                    objectOutputStream.writeObject(user);
                    Log.d(TAG, "run: " + user);
                }catch (IOException e){
                    Log.e(TAG, "run: " + e.toString());
                }finally {
                    if(objectOutputStream != null){
                        try{
                            objectOutputStream.close();
                        }catch (IOException e){

                        }
                    }
                }
            }
        }).start();
    }
}
