package jp.co.mmi.example.receiver;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import jp.co.mmi.example.activities.MainActivity;

/**
 * リスナー
 *
 * Created by rannk on 2017/07/27.
 */
public class AppPhoneStateListener extends PhoneStateListener {

    private final String TAG = getClass().getSimpleName();

    private final Context context;

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     */
    public AppPhoneStateListener(Context context) {
        this.context = context;
    }

    /**
     * 着信〜終了 CALL_STATE_RINGING > CALL_STATE_OFFHOOK > CALL_STATE_IDLE
     * 不在着信 CALL_STATE_RINGING > CALL_STATE_IDLE
     * @param state ステータス
     * @param callNumber 着信が発生した電話番号
     */
    public void onCallStateChanged(int state, String callNumber) {
        Log.d(TAG, ":" + state+"-PhoneNumber:"+callNumber);

        switch(state){
            case TelephonyManager.CALL_STATE_IDLE:
                //待ち受け（終了時）
                //Toast.makeText(ctx, "CALL_STATE_IDLE", Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                //着信
                this.startMainActivity(callNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //通話
                //Toast.makeText(ctx, "CALL_STATE_OFFHOOK", Toast.LENGTH_LONG).show();
                break;
        }
    }

    protected void startMainActivity(String callNumber) {
        Intent newIntent = new Intent(this.context, MainActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //newIntent.addCategory(Intent.FLAG_A)
        newIntent.putExtra("jp.co.mmi.example.callNumber", callNumber);


        this.context.startActivity(newIntent);
    }

    /**
     * マネージャーへの登録
     *
     * @param manager マネージャー
     */
    public void registTo(TelephonyManager manager) {
        manager.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
        Log.i(TAG, "[AppPhoneStateListener]TelephonyManager listen started!!!!!!");
    }

    /**
     * マネージャーからのリスナー解除
     * @param manager マネージャー
     */
    public void unregistTo(TelephonyManager manager) {
        manager.listen(this, PhoneStateListener.LISTEN_NONE);
        Log.i(TAG, "[AppPhoneStateListener]TelephonyManager listen end!!!!!!");
    }
}
