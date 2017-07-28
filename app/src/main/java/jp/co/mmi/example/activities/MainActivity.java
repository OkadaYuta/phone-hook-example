package jp.co.mmi.example.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import jp.co.mmi.example.R;
import jp.co.mmi.example.domain.dto.PhoneMonitorDto;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private List<PhoneMonitorDto> items = new ArrayList<>();

    private Observable<PhoneMonitorDto> observables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView logTextView = (TextView) this.findViewById(R.id.logText);
        logTextView.setText("");
        if (savedInstanceState != null) {
            CharSequence logText = savedInstanceState.getCharSequence(this.getClass().getName() + ".logText");
            logTextView.append(logText != null ? logText : "");
        }
        logTextView.append("[onCreate]\n");
    }

    protected void onStart() {
        super.onStart();
        final TextView logTextView = (TextView) this.findViewById(R.id.logText);
        logTextView.append("[onStart]\n");
        this.onPhoneEvent(this.getIntent());

//        if (dto != null) {
//            this.items.add(dto);
//        }
//
//        this.observables = Observable.fromIterable(this.items);
//        this.observables.subscribe(new Consumer<PhoneMonitorDto>() {
//            @Override
//            public void accept(PhoneMonitorDto phoneMonitorDto) throws Exception {
//                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//                StringBuilder sb = new StringBuilder();
//                sb.append("[").append(formatter.format(phoneMonitorDto.getEventTime())).append("]");
//                if (PhoneMonitorDto.EventType.ON_CALLED.compareTo(phoneMonitorDto.getEventType()) == 0) {
//                    sb.append("[").append("着信検知").append("]");
//                }else if (PhoneMonitorDto.EventType.ON_CALLED.compareTo(phoneMonitorDto.getEventType()) == 0) {
//                    sb.append("[").append("通話開始").append("]");
//                }else if (PhoneMonitorDto.EventType.ON_CALLED.compareTo(phoneMonitorDto.getEventType()) == 0) {
//                    sb.append("[").append("通話終了").append("]");
//                }
//                sb.append(phoneMonitorDto.getPhoneNumber());
//
//                logText.append(sb);
//            }
//        });

//        final BroadcastReceiver monitorReciver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//            }
//        };
//        FloatingActionButton startWatch = (FloatingActionButton) findViewById(R.id.startWatch);
//        startWatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "通話状態の監視を開始します。", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                // 登録
//                registerReceiver(monitorReciver, new IntentFilter(PhoneMonitorDto.class.getName()));
//            }
//        });
//
//
//        FloatingActionButton stopWatch = (FloatingActionButton) findViewById(R.id.endWatch);
//        stopWatch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 解除
//                unregisterReceiver(monitorReciver);
//
//                Snackbar.make(view, "通話状態の監視を終了します。", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        final TextView logTextView = (TextView) this.findViewById(R.id.logText);
        logTextView.append("[onNewIntent]\n");
        this.onPhoneEvent(intent);
    }

    private void onPhoneEvent(Intent intent) {
        final TextView logTextView = (TextView) this.findViewById(R.id.logText);
        PhoneMonitorDto phoneMonitorDto = PhoneMonitorDto.create(intent);
        if (phoneMonitorDto != null) {
            final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            StringBuilder sb = new StringBuilder();
            sb.append("[").append(formatter.format(phoneMonitorDto.getEventTime())).append("]");
            if (PhoneMonitorDto.EventType.ON_CALLED.compareTo(phoneMonitorDto.getEventType()) == 0) {
                sb.append("[").append("着信検知").append("]");
            }else if (PhoneMonitorDto.EventType.ON_HOOKED.compareTo(phoneMonitorDto.getEventType()) == 0) {
                sb.append("[").append("通話開始").append("]");
            }else if (PhoneMonitorDto.EventType.ON_IDLE.compareTo(phoneMonitorDto.getEventType()) == 0) {
                sb.append("[").append("通話終了").append("]");
            }
            sb.append(phoneMonitorDto.getPhoneNumber());
            sb.append("\n");
            logTextView.append(sb);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final TextView logTextView = (TextView) this.findViewById(R.id.logText);
        CharSequence logText = logTextView.getText();
        outState.putCharSequence(this.getClass().getName() + ".logText", logText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
