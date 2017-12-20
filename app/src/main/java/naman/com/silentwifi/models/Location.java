package naman.com.silentwifi.models;

/**
 * Created by naman on 5/11/17.
 */

public class Location {
    private String title;
    private float latitude;
    private float longitude;

    public Location(String title, float latitude, float longitude) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
