package ru.automize.videotest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WebServerService extends IntentService {
//    // TODO: Rename actions, choose action names that describe tasks that this
//    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//    private static final String ACTION_FOO = "ru.automize.videotest.action.FOO";
//    private static final String ACTION_BAZ = "ru.automize.videotest.action.BAZ";
//
//    // TODO: Rename parameters
//    private static final String EXTRA_PARAM1 = "ru.automize.videotest.extra.PARAM1";
//    private static final String EXTRA_PARAM2 = "ru.automize.videotest.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    // TODO: Customize helper method
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, WebServerService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, WebServerService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    private final Object _lock = new Object();

    private Thread _serverThread = new Thread(new Runnable() {
        private NanoHTTPD _server = new EmptyServer();

        @Override
        public void run() {
            try {
                Log.d("SERVER_THREAD", "starting...");
                _server.start();
                Log.d("SERVER_THREAD", "started");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Log.d("SERVER_THREAD", "waiting...");
                synchronized (_lock) {
                    _lock.wait();
                }
                Log.d("SERVER_THREAD", "finished");
            } catch (InterruptedException e) {
                Log.d("SERVER_THREAD", "interrupted");
                e.printStackTrace();
            }

            Log.d("SERVER_THREAD", "stopping...");
            _server.stop();
            Log.d("SERVER_THREAD", "stopped");
        }
    });

    public WebServerService() {
        super("WebServerService");
    }

    @Override
    public void onCreate() {
        Log.d("SERVICE", "onCreate");
        _serverThread.start();
        x = () -> {

        };
//        try {
//            _server.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("SERVICE", "onDestroy");
        synchronized (_lock) {
            _lock.notifyAll();
        }
//        _server.stop();
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SERVICE", "onHandleIntent");
        try {
            // we are blocked until the thread is interrupted
            Log.d("SERVICE", "to wait....");
            synchronized (_lock) {
                _lock.wait();
            }
            Log.d("SERVICE", "onHandleIntent finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("SERVICE", "onHandleIntent interrupted");
        }

//        if (intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
//        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
//    private void handleActionFoo(String param1, String param2) {
//        // TODO: Handle action Foo
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
//    private void handleActionBaz(String param1, String param2) {
//        // TODO: Handle action Baz
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
