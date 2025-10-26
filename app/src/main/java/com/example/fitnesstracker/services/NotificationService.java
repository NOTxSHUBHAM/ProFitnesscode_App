package com.example.profitness.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.profitness.R;
import com.example.profitness.activities.MainActivity;

public class NotificationService {
    private static final String CHANNEL_ID = "fitness_tracker_channel";
    private static final int NOTIFICATION_ID = 1;

    private Context context;
    private NotificationManager notificationManager;

    public NotificationService(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Fitness Tracker",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Fitness tracking notifications");
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showStepNotification(int steps, int goal) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        int progress = (int) ((steps / (float) goal) * 100);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("ProFitness Tracker")
                .setContentText("Steps: " + steps + " (" + progress + "% of goal)")
                .setSmallIcon(R.drawable.ic_fire)
                .setContentIntent(pendingIntent)
                .setProgress(100, progress, false)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void showGoalAchievedNotification() {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Goal Achieved! 🎉")
                .setContentText("Congratulations! You've reached your daily step goal.")
                .setSmallIcon(R.drawable.ic_fire)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(NOTIFICATION_ID + 1, notification);
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }
}