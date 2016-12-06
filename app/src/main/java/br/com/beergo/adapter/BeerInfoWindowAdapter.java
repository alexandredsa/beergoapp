package br.com.beergo.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.com.beergo.R;

public class BeerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity mActivity;

    public BeerInfoWindowAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Getting view from the layout file info_window_layout
        View v = mActivity.getLayoutInflater().inflate(R.layout.beer_info_window, null);

        ((TextView) v.findViewById(R.id.txtSnippet)).setText(Html.fromHtml(marker.getSnippet()));
        return v;
    }
}
