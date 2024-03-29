package com.example.cgiday4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class MainActivity extends AppCompatActivity {
    private NotificationCompat.Builder mNotifyBuilder;

    private NotificationManager mNotifyManager;

    private static final int NOTIFICATION_ID = 0;
String data = "<html>\\<header>this is a demo from data string</header>\\<body>this is the body</body>\\r\\n</html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webView.loadUrl("file:///android_asset/index.html");
        webView.loadData(data,"text/html", "UTF-8");

    }


    private static final String KEY_TEXT_REPLY = "key_text_reply";

    public void showNotification(View view) {
        createNotificationChannel();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9880979732"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You've been notified! title")
                .setContentText("This is your notification text. text")
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVisibility(VISIBILITY_PUBLIC)
                .setChannelId("345")
                .addAction(R.drawable.ic_launcher_foreground, "action button",pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background);

        Notification myNotification = mNotifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID,  myNotification);


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "my notification channel";
                    //getString(R.string.channel_name);
            String description = "channel description";
                    //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("345", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            mNotifyManager.createNotificationChannel(channel);
        }
    }
}
