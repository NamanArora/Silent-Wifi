package naman.com.silentwifi.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import naman.com.silentwifi.R;
import naman.com.silentwifi.models.Location;

/**
 * Created by naman on 5/11/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private ArrayList<Location> locations;

    public LocationAdapter(ArrayList<Location> locations){
        this.locations = locations;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        //change stuff tv.setText(lcoations[pos].abc)
        holder.title.setText(locations.get(pos).getTitle());
        holder.latitude.setText(locations.get(pos).getLatitude() + "");
        holder.longitude.setText(locations.get(pos).getLongitude() + "");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView title;
        @BindView(R.id.title) TextView latitude;
        @BindView(R.id.title) TextView longitude;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
