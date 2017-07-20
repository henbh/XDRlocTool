package datamodule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("cell_id")
    public int cellId;
    @JsonProperty("lac")
    public int lac;
    @JsonProperty("mcc")
    public int mcc;
    @JsonProperty("mnc")
    public int mnc;
    @JsonProperty("time")
    public Long lastUpdateTime;
    @JsonProperty("imei")
    public String imei;
    @JsonProperty("imsi")
    public String imsi;
    @JsonProperty("msisdn")
    public String msisdn;
    @JsonProperty("latitude")
    public Float latitude;
    @JsonProperty("longitude")
    public Float longitude;

    public Location(int cellId, int lac, int mcc, int mnc, Long lastUpdateTime, String imei, String imsi, String msisdn) {
        this.cellId = cellId;
        this.lac = lac;
        this.mcc = mcc;
        this.mnc = mnc;
        this.lastUpdateTime = lastUpdateTime;
        this.imei = imei;
        this.imsi = imsi;
        this.msisdn = msisdn;
    }
}
