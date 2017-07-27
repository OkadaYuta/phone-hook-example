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

    /** リスナー */
    private AppPhoneStateListener phoneStateListener;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(new ExampleLifecycleHandler());
//        Context context = this.getApplicationContext();
//        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (manager != null) {
//            this.phoneStateListener = new AppPhoneStateListener(context);
//            phoneStateListener.registTo(manager);
//        }
    }

    @Override
    public void onTerminate() {
//        Context context = this.getApplicationContext();
//        if (this.phoneStateListener != null) {
//            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (manager != null) {
//                this.phoneStateListener.unregistTo(manager);
//                this.phoneStateListener = null;
//            }
//        }
        super.onTerminate();
    }
}
