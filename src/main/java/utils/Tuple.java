package utils;

public class Tuple<X, Y> {
    public final X longitude;
    public final Y latitude;
    public Tuple(X longitude, Y latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
