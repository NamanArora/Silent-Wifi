package naman.com.silentwifi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import naman.com.silentwifi.adapters.LocationAdapter;

public class SavedLocation extends AppCompatActivity {

    ArrayList locations;

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
        adapter = new LocationAdapter(locations);
        recycler.setAdapter(adapter);
        fetchData();
    }

    private void fetchData() {

        adapter.notifyDataSetChanged();
    }
}
