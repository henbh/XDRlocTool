package datamodule;

public class Location {
    public String time;
    public String longtitude;
    public String Latitude;
    public String Address;
    public String CellId;
    public String Msisdn;

    public Location(String time, String longtitude, String latitude, String address, String cellId, String msisdn) {
        this.time = time;
        this.longtitude = longtitude;
        Latitude = latitude;
        Address = address;
        CellId = cellId;
        Msisdn = msisdn;
    }
}
