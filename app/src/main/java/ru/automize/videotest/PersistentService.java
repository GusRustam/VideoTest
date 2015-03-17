package ru.automize.videotest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class PersistentService extends Service {

    private static final String TAG = "PersistentService";
    private NanoHTTPD _server = new EmptyServer();

    public PersistentService() {
        Log.d(TAG, "PersistentService");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        try {
            _server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        _server.stop();
    }
}