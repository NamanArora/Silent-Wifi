package naman.com.silentwifi.models;

/**
 * Created by naman on 5/11/17.
 */

public class Location {
    private String title;
    private float latitude;
    private float longitude;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
