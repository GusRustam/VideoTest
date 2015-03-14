package ru.automize.videotest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class PersistentService extends Service {

    private static final String TAG = "PersistentService";
    private NanoHTTPD _server = new EmptyServer();
    public PersistentService() {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        try {
            _server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart");
        //Note: You can start a new thread and use it for long background processing from here.
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        _server.stop();
        Log.d(TAG, "onDestroy");
    }
}
