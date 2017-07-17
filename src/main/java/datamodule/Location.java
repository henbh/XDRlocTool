package datamodule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("cell_id")
    public int mCellId;
    @JsonProperty("lac")
    public int mLac;
    @JsonProperty("mcc")
    public int mMCC;
    @JsonProperty("mnc")
    public int mMNC;
    @JsonProperty("time")
    public Long mLastUpdateTime;
    @JsonProperty("imei")
    public String mImei;
    @JsonProperty("imsi")
    public String mImsi;
    @JsonProperty("msisdn")
    public String mMsisdn;
    @JsonProperty("latitude")
    public Float mLatitude;
    @JsonProperty("longitude")
    public Float mLongitude;

    public Location(int mCellId, int mLac, int mMCC, int mMNC, Long mLastUpdateTime, String mImei, String mImsi, String mMsisdn) {
        this.mCellId = mCellId;
        this.mLac = mLac;
        this.mMCC = mMCC;
        this.mMNC = mMNC;
        this.mLastUpdateTime = mLastUpdateTime;
        this.mImei = mImei;
        this.mImsi = mImsi;
        this.mMsisdn = mMsisdn;
    }
}
