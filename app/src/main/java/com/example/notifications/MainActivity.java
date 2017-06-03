package com.example.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String MY_NOTIFCATION_GROUP = "my_notification_group";
    public static final String VOICE_RESPONSE_EXTRA = "extra_respuesta_por_voz";

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

                int notificationId2 = 002;
                Notification notification2 = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("New Conference").setContentText("Los neutrinos")
                        .setSmallIcon(R.mipmap.ic_action_mail_add)
                        .setGroup(MY_NOTIFCATION_GROUP)
                        .build();
                notificationManager.notify(notificationId2, notification2);


                int notificationId3 = 003;
                Notification notificacion3 = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("2 Conference Notifications")
                        .setSmallIcon(R.mipmap.ic_action_attach)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.conference))
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine("New Conference Los neutrinos")
                                .addLine("New Android Wear Course").setBigContentTitle("2 Conference Notifications")
                                .setSummaryText("info@upv.es")).setNumber(2)
                        .setGroup(MY_NOTIFCATION_GROUP).setGroupSummary(true).build();
                notificationManager.notify(notificationId3, notificacion3);
            }
        });

        // NOTIFICATION VOICE RESPONSE
        Button voiceBtn = (Button) findViewById(R.id.voiceBtn);
        voiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intencion, PendingIntent.FLAG_UPDATE_CURRENT);
                // Creamos la entrada remota para añadirla a la acción
                String[] opcRespuesta = getResources().getStringArray(R.array.opciones_respuesta);
                RemoteInput remoteInput = new RemoteInput.Builder(VOICE_RESPONSE_EXTRA).setLabel("respuesta por voz").setChoices(opcRespuesta).build();
                // RemoteInput remoteInput = new RemoteInput.Builder(VOICE_RESPONSE_EXTRA).setLabel("Voice Response").build();
                // Creamos la acción
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_set_as, "Answer", pendingIntent)
                        .addRemoteInput(remoteInput).build();
                // Creamos la notificación
                int idNotification = 002;
                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat
                        .Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Voice")
                        .setContentText("Indica una respuesta")
                        .extend(new NotificationCompat.WearableExtender()
                                .addAction(action));
                // Lanzamos la notificación
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(idNotification, notificationBuilder.build());
                //Miramos si hemos recibido una respuesta por voz
                Bundle response = RemoteInput.getResultsFromIntent(getIntent());
                if (response != null) {
                    CharSequence texto = response.getCharSequence(VOICE_RESPONSE_EXTRA);
                    ((TextView) findViewById(R.id.txtVResponse)).setText(texto);
                }
            }
        });
    }
}

