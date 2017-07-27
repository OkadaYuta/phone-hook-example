package jp.co.mmi.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 着信イベント用レシーバー
 * Created by rannk on 2017/07/27.
 */
public class PhoneCalledReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();
    /**
     * 着信イベント処理
     *
     * @param context コンテキスト
     * @param intent 院展と
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());

        // 念のため受信イベントチェック
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.w("MY_DEBUG_TAG", state);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.w("MY_DEBUG_TAG", phoneNumber);
                Toast.makeText(context, phoneNumber, Toast.LENGTH_LONG);
            }
        }
    }

    //protected boolean is
}
