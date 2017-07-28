package jp.co.mmi.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.Stack;

import io.reactivex.Observable;
import jp.co.mmi.example.activities.MainActivity;
import jp.co.mmi.example.domain.dto.PhoneMonitorDto;

/**
 * 着信イベント用レシーバー
 * Created by rannk on 2017/07/27.
 */
public class PhoneCalledReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();

    //private final Stack<String> statusStack = new Stack<>();

    /**
     * 着信イベント処理
     *
     * @param context コンテキスト
     * @param intent インテント
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        String state = extras.getString(TelephonyManager.EXTRA_STATE);
        Log.d("MY_DEBUG_TAG", state);
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            // 着信時
            this.onCalled(context, extras);
        }
//        if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
//            // 応答時
//            this.onHook(context, extras);
//        }
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // 待機状態時
            this.onIdle(context, extras);
        }
    }

    /**
     * 送信処理
     *
     * @param dto 電話イベント
     */
    protected void send(Context context, PhoneMonitorDto dto) {
        // TODO オブザーバーパターンを利用した処理の汎用化
        // ※現時点では、サンプルということで専用処理
        Intent newIntent = new Intent(context, MainActivity.class);
        //newIntent.setAction(dto.getClass().getName());
        dto.saveTo(newIntent);

        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(newIntent);
        //context.sendBroadcast(newIntent);
    }

    /**
     * 着信発生時処理
     *
     * @param context コンテキスト
     * @param extras データ
     */
    protected void onCalled(Context context, Bundle extras) {
        String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d("[onCalled]phoneNumber:", phoneNumber);

        PhoneMonitorDto dto = new PhoneMonitorDto(phoneNumber, new Date(), PhoneMonitorDto.EventType.ON_CALLED);
        this.send(context, dto);
    }

    /**
     * 通話開始時 or 切断時処理
     *
     * @param context コンテキスト
     * @param extras データ
     */
    protected void onHook(Context context, Bundle extras) {
        String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d("[onHook]phoneNumber:", phoneNumber);

        //PhoneMonitorDto dto = new PhoneMonitorDto(phoneNumber, new Date(), PhoneMonitorDto.EventType.ON_HOOKED);
        //this.send(context, dto);
    }

    /**
     * 通話終了時処理
     *
     * @param context コンテキスト
     * @param extras データ
     */
    protected void onIdle(Context context, Bundle extras) {
        String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        Log.d("[onIdle]phoneNumber:", phoneNumber);

        PhoneMonitorDto dto = new PhoneMonitorDto(phoneNumber, new Date(), PhoneMonitorDto.EventType.ON_IDLE);
        //this.send(context, dto);
    }
}
