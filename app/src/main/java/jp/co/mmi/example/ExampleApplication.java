package jp.co.mmi.example;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import jp.co.mmi.example.receiver.AppPhoneStateListener;

/**
 * Created by rannk on 2017/07/27.
 */

public class ExampleApplication extends Application {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = this.getApplicationContext();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager != null) {
            AppPhoneStateListener listener = new AppPhoneStateListener(context);
            listener.registTo(manager);
        } else {
            Log.d(TAG, "TelephonyManager is null");
        }
    }
}
