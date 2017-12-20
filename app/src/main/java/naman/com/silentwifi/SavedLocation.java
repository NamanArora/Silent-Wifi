package naman.com.silentwifi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import naman.com.silentwifi.adapters.LocationAdapter;
import naman.com.silentwifi.models.Location;

public class SavedLocation extends AppCompatActivity {

    ArrayList<Location> locations;

    @BindView(R.id.locationRecycler)
    RecyclerView recycler;
    LocationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_location);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recycler.setLayoutManager(layout);
        locations = new ArrayList<>();
        adapter = new LocationAdapter(locations);
        recycler.setAdapter(adapter);
        fetchData();
    }

    private void fetchData() {
        CoordinatesStorage store = new CoordinatesStorage(getApplicationContext());
        SQLiteDatabase db = store.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM LOCATION", null);
        while(c.moveToNext()){
            String title = c.getString(c.getColumnIndex(CoordinatesStorage.DB.title));
            float latitude = c.getFloat(c.getColumnIndex(CoordinatesStorage.DB.latitude));
            float longitude = c.getFloat(c.getColumnIndex(CoordinatesStorage.DB.longitude));
            Location location = new Location(title,latitude,longitude);
            locations.add(location);
        }
        adapter.notifyDataSetChanged();
    }
}
