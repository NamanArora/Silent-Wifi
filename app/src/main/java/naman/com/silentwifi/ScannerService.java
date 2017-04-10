package naman.com.silentwifi;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Naman on 08-04-2017.
 */

public class ScannerService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener   {

    private static final float THRESHOLD_DIST_IN_METRE = 30;
    private static int UPDATE_INTERVAL = 10*1000;
    private static int FAST_INTERVAL = 5*1000;
    public static GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private WifiHandler mWifiHandler;
    Handler handler;
    Runnable r;
    private BroadcastReceiver receiver;

    private void fetchLocation() {
        //TODO: IMPLEMENT ASKING FOR PERMISSION HERE
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationRequest request = new LocationRequest();
        request.setInterval(UPDATE_INTERVAL);
        request.setFastestInterval(FAST_INTERVAL);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, request, this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //first this is called then onstart
        //Log.d("scanner","first");
        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.BROADCAST_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(MainActivity.BROADCAST_ACTION) )
                {
                    Log.d("scanner", "broadcast received");
                }
                else
                    Log.d("scanner", "broadcast failed");
            }
        };
        mWifiHandler = new WifiHandler();
        mCurrentLocation = new Location("");
        mCurrentLocation.setLatitude(0);
        mCurrentLocation.setLongitude(0);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if(!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("scanner","connected");

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                fetchLocation();
            }
        };
        handler.post(r);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("scanner","suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("scanner","failed " + connectionResult.getErrorMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
        handler.removeCallbacks(r);
        unregisterReceiver(receiver);
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.d("scanner","update");
        //TODO: ADD SUPPORT FOR MULTIPLE LOCATIONS AND SAVING THEM!
        if(mCurrentLocation.distanceTo(location) >= THRESHOLD_DIST_IN_METRE) {
            //we are outside safe zone, turn off wifi
            if(mWifiHandler.getStatus())
                mWifiHandler.closeWifi();
            Log.d("scanner", "lat: " + location.getLatitude());
            Log.d("scanner", "long: " + location.getLongitude());
            mCurrentLocation = location;
        }
        else
        {
            //we are in safe zone, turn on wifi
            if(!mWifiHandler.getStatus())
                mWifiHandler.openWifi();
        }
    }




    public class WifiHandler
    {
        public void closeWifi()
        {
            WifiManager wifi;
            wifi=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            Log.d("scanner", "Turning wifi off");
            wifi.setWifiEnabled(false);
        }

        public void openWifi()
        {
            WifiManager wifi;
            wifi=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            Log.d("scanner", "Turning wifi on");
            wifi.setWifiEnabled(true);
        }

        public boolean getStatus()
        {
            WifiManager wifi;
            wifi=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            //TODO: need to handle wifi states later
            return wifi.getWifiState() == 1 ? false : true;
        }
    }
}
