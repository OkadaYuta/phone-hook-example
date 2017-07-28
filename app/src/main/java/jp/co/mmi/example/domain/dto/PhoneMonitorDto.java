package jp.co.mmi.example.domain.dto;

import android.content.Intent;
import android.telephony.TelephonyManager;

import java.util.Date;

/**
 * 電話監視DTO
 *
 * Created by rannk on 2017/07/28.
 */
public class PhoneMonitorDto {

    /** 電話番号 */
    private String phoneNumber;

    /** イベント日時 */
    private Date eventTime;

    /** イベントタイプ */
    private EventType eventType;

    /**
     * コンストラクタ[非公開]
     */
    private PhoneMonitorDto() {

    }

    /**
     * コンストラクタ
     *
     * @param phoneNumber 電話番号
     * @param eventTime イベント開始時間
     * @param eventType イベントタイプ
     */
    public PhoneMonitorDto(String phoneNumber, Date eventTime, EventType eventType) {
        this.phoneNumber = phoneNumber;
        this.eventTime = eventTime;
        this.eventType = eventType;
    }

    /**
     * 電話番号取得
     *
     * @return 電話番号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 電話番号設定
     *
     * @param phoneNumber 電話番号
     */
    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * イベント時間取得
     *
     * @return イベント時間
     */
    public Date getEventTime() {
        return eventTime;
    }

    /**
     * イベント時間設定
     * @param eventTime イベント時間
     */
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * イベントタイプ取得
     *
     * @return イベントタイプ
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * イベントタイプ設定
     *
     * @param eventType イベントタイプ
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * シリアライズ処理
     *
     * @param intent インテント
     */
    public void saveTo(Intent intent) {
        intent.putExtra(PhoneMonitorDto.class.getName(), true);

        intent.putExtra(PhoneMonitorDto.class.getName() + ".phoneNumber", phoneNumber);
        intent.putExtra(PhoneMonitorDto.class.getName() + ".eventTime", this.eventTime.getTime());
        intent.putExtra(PhoneMonitorDto.class.getName() + ".eventType", this.eventType.toString());
    }

    /**
     * 復元処理
     *
     * @param intent インテント
     * @return 電話監視DTO
     */
    public static PhoneMonitorDto create(Intent intent) {
        if(!intent.getBooleanExtra(PhoneMonitorDto.class.getName(), false)) {
            return null;
        }

        PhoneMonitorDto dto = new PhoneMonitorDto();

        dto.phoneNumber = intent.getStringExtra(PhoneMonitorDto.class.getName() + ".phoneNumber");
        long eventTimeMills = intent.getLongExtra(PhoneMonitorDto.class.getName() + ".eventTime", 0L);
        dto.eventTime = new Date(eventTimeMills);
        String eventTypeName = intent.getStringExtra(PhoneMonitorDto.class.getName() + ".eventType");
        dto.eventType = EventType.valueOf(eventTypeName);

        return dto;
    }

    public static enum EventType {
        ON_CALLED,ON_HOOKED,ON_IDLE
    }
}
