package naman.com.silentwifi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    private int status;
    private SharedPreferences preferences;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, ScannerService.class);
        button = (Button) findViewById(R.id.button);
        preferences = getApplicationContext().getSharedPreferences("com.naman.button.status", Context.MODE_PRIVATE);
        status = preferences.getInt("button", 1);
        Log.d("scanner", "read=" + status);
        updatetext();
    }

    private void updatetext() {
        if(status==0)
            button.setText("Stop \ntracker");
        else
            button.setText("Start \ntracker");
    }

    public void startstopButton(View view)
    {
        if(status == 1) {
            start();

            //button.setBackgroundColor(Color.RED);
            status=0;
            updatetext();
        }
        else {
            stop();

            //button.setBackgroundColor(Color.GREEN);
            status=1;
            updatetext();
        }

        preferences.edit().putInt("button",status).apply();
    }


    public void start() {
        startService(intent);
    }

    public void stop() {
        stopService(intent);
    }
}
