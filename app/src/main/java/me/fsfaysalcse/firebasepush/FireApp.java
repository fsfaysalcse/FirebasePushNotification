package me.fsfaysalcse.firebasepush;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FireApp extends Application {

    private static final String TAG = "FireAppXX";

    @Override
    public void onCreate() {
        super.onCreate();

        // Get the token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get the FCM token
                        String token = task.getResult();
                        // Log and toast
                        Log.d(TAG, token);
                    }
                });
    }
}
