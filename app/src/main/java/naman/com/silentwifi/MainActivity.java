package naman.com.silentwifi;

import android.*;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "naman.com.silentwifi.newloc";
    Intent intent;
    private int status;
    private SharedPreferences preferences;
    private Button button;
    private Button add;
    SQLiteDatabase db;
    private int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new CoordinatesStorage(getApplicationContext()).getWritableDatabase();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);

        intent = new Intent(this, ScannerService.class);
        button = (Button) findViewById(R.id.button);
        add = (Button) findViewById(R.id.addloc);
        preferences = getApplicationContext().getSharedPreferences("com.naman.button.status", Context.MODE_PRIVATE);
        status = preferences.getInt("button", 1);
        if(status == 1)
            add.setVisibility(View.INVISIBLE);
        else
            add.setVisibility(View.VISIBLE);
        Log.d("scanner", "read=" + status);
        updatetext();
    }

    private void updatetext() {
        if (status == 0)
            button.setText("Stop \ntracker");
        else
            button.setText("Start \ntracker");
    }

    public void startstopButton(View view) {
        if (status == 1) {
            start();
            add.setVisibility(View.VISIBLE);
            //button.setBackgroundColor(Color.RED);
            status = 0;
            updatetext();
        } else {
            stop();
            add.setVisibility(View.INVISIBLE);
            //button.setBackgroundColor(Color.GREEN);
            status = 1;
            updatetext();
        }

        preferences.edit().putInt("button", status).apply();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void start() {
        startService(intent);
    }

    public void stop() {
        stopService(intent);
    }

    public void addloc(View view) {

        Intent intent = new Intent(BROADCAST_ACTION);
        Toast.makeText(getApplicationContext(), "Location recorded!", Toast.LENGTH_LONG).show();
        sendBroadcast(intent);

        /*ContentValues values = new ContentValues();
        values.put(CoordinatesStorage.DB.latitude, lat);
        values.put(CoordinatesStorage.DB.longitude, lon);

        long res = db.insert(CoordinatesStorage.DB.table_name,null,values);
        Log.d("scanner", res + "");
        //add to db the current coordinates*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.about:
                Intent i = new Intent(this, About.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
