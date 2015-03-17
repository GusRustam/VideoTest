package ru.automize.videotest;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final Class<?> _serviceClass = PersistentService.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startWebServer(View view) {
        if (isMyServiceRunning(_serviceClass)) {
            Toast.makeText(this, "Server already running", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, _serviceClass);
            startService(intent);

            updateServiceStatus();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void refreshWebServer(View view) {
        updateServiceStatus();
    }

    private void updateServiceStatus() {
        TextView text = (TextView) findViewById(R.id.server_status);
        boolean myServiceRunning = isMyServiceRunning(_serviceClass);
        text.setText(myServiceRunning ? "Running" : "Stopped");

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formattedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

        text = (TextView) findViewById(R.id.ip_address);
        if (text != null) {
            text.setText(myServiceRunning ? "Address is http://" + formattedIpAddress + ":" + 8080 : "Not started");
        }
    }


    private int mId = 1214;
    private String notificationTitle = "Hello World";
    private int notificationNumber = 12;

    private String getNotificationTitle() {
        return String.format("%s - %d", notificationTitle, notificationNumber);
    }

    public void disposeNotification(View view) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(mId);
    }

    public void updateNotification(View view) {
        notificationNumber++;

        // todo check if notification exists


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Sets an ID for the notification, so it can be updated
        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.server_notification);

        // Start of a loop that processes data and then notifies the user
        mNotifyBuilder
                .setContentTitle(getNotificationTitle())
                .setNumber(notificationNumber);

        // Because the ID remains unchanged, the existing notification is
        // updated.
        mNotificationManager.notify(
                mId,
                mNotifyBuilder.build());
    }

    public void createNotification(View view) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.server_notification)
                        .setContentTitle(getNotificationTitle())
                        .setContentText("A content text here");

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }


    public void stopWebServer(View view) {
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, _serviceClass);
        stopService(intent);

        updateServiceStatus();
    }

}