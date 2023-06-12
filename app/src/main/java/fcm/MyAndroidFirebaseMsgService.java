package fcm;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.MainLeaderActivity;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                //JSONObject json = new JSONObject(remoteMessage.getData().toString());
                String title = remoteMessage.getData().get("title");
                String message = remoteMessage.getData().get("message");
                String imageUrl = remoteMessage.getData().get("imageUrl");

                AppController.getInstance().getSessionManager().putStringData(AppConstant.PARAM_FCM,
                        title);
                sendPushNotification(title,
                        message,
                        imageUrl);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void sendPushNotification(String title, String message, String imageUrl) {
        //optionally we can display the json into log
        try {
            //getting the json data
            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainLeaderActivity.class);

            //if there is no image
            if(imageUrl == null){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /*@Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

            Map data = remoteMessage.getData();
            int code = Integer.parseInt((String) data.get("code"));

            AppController.getInstance().getSessionManager().putStringData(AppConstant.PARAM_FCM,
                    (String) data.get("code"));
        *//*AppController.getInstance().getSessionManager().putStringData(AppConstant.PARAM_FCM,
                remoteMessage.getNotification().getBody());*//*
            //create notification
            createNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void createNotification( String messageBody) {
        String sMsg;
        try{
            sMsg = messageBody.substring(0,9).toUpperCase();
            if (!sMsg.toUpperCase().equals("KEHADIRAN")){
                sMsg = messageBody;
            }
        }catch (Exception e){
            sMsg = messageBody;
        }

        AppConstant.FCM_KEHADIRAN = true;


        Intent intent = new Intent( this , MainLeaderActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstant.PARAM_FCM, sMsg);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.mipmap.bp_batam_app_icon)
                .setContentTitle("Notification")
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI)
                .setPriority(Notification.PRIORITY_MAX)
                .setLights(0xff00ff00, 300, 100)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setVibrate(new long[]{1000, 1000})
                .setTicker("New messages from EAdmin!")
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }*/
}