package ke.co.keki.com.keki.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import ke.co.keki.com.keki.R;

/**
 * Notification Channel Support for android Oreo and above
 */
public class NotificationChannelSupport {
    public NotificationChannelSupport() {

    }

    public void createNotificationChannel(Context ctx, String ChannelID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Channel Name
            CharSequence name = ctx.getString(R.string.channel_notification_name);
            String description = ctx.getString(R.string.channel_notification_description);
            //Channel Importance
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(ChannelID, name, importance);
            //Channel Description
            channel.setDescription(description);
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            //Create Channel
            notificationManager.createNotificationChannel(channel);
        }
    }


}
