package com.example.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String MY_NOTIFCATION_GROUP = "my_notification_group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button wearButton = (Button) findViewById(R.id.btn1);
        wearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=playa+de+la+concha"));
                PendingIntent pendingMapIntent = PendingIntent.getActivity(MainActivity.this, 0, mapIntent, 0);

                Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:555123456"));
                PendingIntent callPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, callIntent, 0);

                int notificationId = 001;

                NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_action_call, "Call", callPendingIntent).build();

                List<NotificationCompat.Action> actionList = new ArrayList<NotificationCompat.Action>();
                actionList.add(action);
                actionList.add(new NotificationCompat.Action(R.mipmap.ic_action_locate, "Show Map", pendingMapIntent));

                String s = "Come on, It's such a beautiful day today, let's go to the beach!! ";

                //Creating a BigTextStyle for the second page and third page
                NotificationCompat.BigTextStyle secondPage = new NotificationCompat.BigTextStyle();
                secondPage.setBigContentTitle("Page 2").bigText("More text");

                NotificationCompat.BigTextStyle thirdPage = new NotificationCompat.BigTextStyle();
                thirdPage.setBigContentTitle("Page 3").bigText("Much more text");

                Notification notificationPg2 = new NotificationCompat.Builder(MainActivity.this).setStyle(secondPage).build();
                Notification notificationPg3 = new NotificationCompat.Builder(MainActivity.this).setStyle(thirdPage).build();

                List<Notification> notificationList = new ArrayList<Notification>();
                notificationList.add(notificationPg2);
                notificationList.add(notificationPg3);


                // Stacking notifications


                NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.beach_time))
                        .addActions(actionList)
                        .addPages(notificationList);
                //    .addPage(notificationPg2)

                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Tittle")
                        .setContentText("Let's go to the beach!")
                        .setContentIntent(pendingMapIntent)
                        // .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.beach_time))
                        .addAction(R.mipmap.ic_action_call, "Call", callPendingIntent)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(s + s + s))
                        // .extend(new NotificationCompat.WearableExtender().addActions(actionList))
                        .extend(wearableExtender)
                        .setGroup(MY_NOTIFCATION_GROUP)
                        .build();

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(notificationId, notification);

                int idNotification2 = 002;
                Notification notification2 = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("New Conference").setContentText("Los neutrinos")
                        .setSmallIcon(R.mipmap.ic_action_mail_add)
                        .setGroup(MY_NOTIFCATION_GROUP)
                        .build();
                notificationManager.notify(idNotification2, notification2);


                int idNotificacion3 = 003;
                Notification notificacion3 = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("2 Conference Notifications")
                        .setSmallIcon(R.mipmap.ic_action_attach)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.conference))
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine("New Conference Los neutrinos")
                                .addLine("New Android Wear Course").setBigContentTitle("2 Conference Notifications")
                                .setSummaryText("info@upv.es")).setNumber(2)
                        .setGroup(MY_NOTIFCATION_GROUP).setGroupSummary(true).build();
                notificationManager.notify(idNotificacion3, notificacion3);

            }
        });
    }


}
