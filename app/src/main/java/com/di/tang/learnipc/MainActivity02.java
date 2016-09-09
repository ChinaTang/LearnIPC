package com.di.tang.learnipc;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by tangdi on 2016/9/8.
 */
public class MainActivity02 extends AppCompatActivity {

    private static final String TAG = "MainActivity02";
    private static final String PATH = "MainActivity01.text";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onResume(){
        super.onResume();
        recoverFromFile();
    }

    private String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    private void recoverFromFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cacheFile = new File(getInnerSDCardPath()
                        + File.separator + PATH);

                Log.d(TAG, "run: " + cacheFile.getAbsolutePath().toString());
                ObjectInputStream objectInputStream = null;
                try{
                    objectInputStream = new ObjectInputStream(new FileInputStream(cacheFile));
                    user = (User)objectInputStream.readObject();
                    Log.d(TAG, "run: " + user);
                }catch(IOException e){
                    Log.e(TAG, "run: " + e.toString() );
                }catch(ClassNotFoundException e){
                    Log.e(TAG, "run: " + e.toString() );
                }finally {
                    if(objectInputStream != null){
                        try{
                            objectInputStream.close();
                        }catch (IOException e){

                        }
                    }
                }
            }
        }).start();
    }
}
