package com.example.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button wearButton = (Button) findViewById(R.id.btn1);
        wearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent =  new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=playa+de+la+concha"));
                PendingIntent pendingMapIntent = PendingIntent.getActivity(MainActivity.this, 0, mapIntent,0);
                Intent callIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:555123456"));
                PendingIntent callPendingIntent = PendingIntent.getActivity(MainActivity.this, 0 , callIntent,0);
                int notificationId = 001;
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Tittle")
                        .setContentText("Android Wear Notification")
                        .setContentIntent(pendingMapIntent)
                        .addAction(android.R.drawable.ic_menu_call,"Call",callPendingIntent)
                        .build();
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(notificationId, notification);
            }
        });

    }


}
