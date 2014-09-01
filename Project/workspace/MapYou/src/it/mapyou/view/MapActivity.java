/**
 * 
 */
package it.mapyou.view;

import it.mapyou.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author mapyou (mapyouu@gmail.com)
 *
 */
public class  MapActivity extends FragmentActivity {

    final LatLng STARTING_POINT = new LatLng(45.4640704, 9.1719064); // Milano

    final LatLng END_POINT = new LatLng(45.070139, 7.6700892); // Torino

    GoogleMap map; //la nostra mappa

    ProgressDialog pDialog; //da mostrare quando calcola il percorso
    List<LatLng> polyz;
    JSONArray array;

    CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(STARTING_POINT).zoom(11).bearing(10).tilt(30).build();  //animazione su Milano

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);
            setUpMapIfNeeded();

            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.setMyLocationEnabled(true);

            map.addMarker(new MarkerOptions()
                            .position(STARTING_POINT)
                            .title("Mia Località")
                            .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));  //aggiungiamo i marker

            map.addMarker(new MarkerOptions().position(END_POINT).title("Arrivo"));

            map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {  

                    // Al click sull'infowindow viene disegnato il percorso
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                            Thread.currentThread().setContextClassLoader( getClass().getClassLoader());
                            new GetDirection().execute(); //calcola il percorso

                    }
            });

            map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {

                    @Override
                    public boolean onMyLocationButtonClick() {

                            map.getMyLocation();

                            return true;
                    }
            });

    }

    @Override
    protected void onResume() {
            super.onResume();
            setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
            if (map == null) {
                    map = ((SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map)).getMap();
                    if (map != null) {
                            setUpMap();
                    }
            }
    }

    private void setUpMap() {
            map.getUiSettings().setZoomControlsEnabled(false);

            // map.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTING_POINT, 5));
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
    
    class GetDirection extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
                super.onPreExecute();
                super.onPreExecute();
                pDialog = new ProgressDialog(MapActivity.this);
                pDialog.setMessage("Loading route. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

        }

        protected String doInBackground(String... args) {

                String stringUrl = "http://maps.googleapis.com/maps/api/directions/json?origin="
                                + STARTING_POINT.latitude
                                + ","
                                + STARTING_POINT.longitude
                                + "&destination="
                                + END_POINT.latitude
                                + ","
                                + END_POINT.longitude + "&sensor=false";

                StringBuilder response = new StringBuilder();
                try {
                        URL url = new URL(stringUrl);
                        HttpURLConnection httpconn = (HttpURLConnection) url
                                        .openConnection();
                        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                BufferedReader input = new BufferedReader(
                                                new InputStreamReader(httpconn.getInputStream()),
                                                8192);
                                String strLine = null;

                                while ((strLine = input.readLine()) != null) {
                                        response.append(strLine);
                                }
                                input.close();
                        }

                        String jsonOutput = response.toString();

                        JSONObject jsonObject = new JSONObject(jsonOutput);

                        // routesArray contains ALL routes
                        JSONArray routesArray = jsonObject.getJSONArray("routes");
                        // Grab the first route
                        JSONObject route = routesArray.getJSONObject(0);

                        JSONObject poly = route.getJSONObject("overview_polyline");
                        String polyline = poly.getString("points");
                        polyz = decodePoly(polyline);

                } catch (Exception e) {

                }

                return null;

        }

        protected void onPostExecute(String file_url) {

                for (int i = 0; i < polyz.size() - 1; i++) {
                        LatLng src = polyz.get(i);
                        LatLng dest = polyz.get(i + 1);
                        map.addPolyline(new PolylineOptions()
                                        .add(new LatLng(src.latitude, src.longitude),
                                                        new LatLng(dest.latitude, dest.longitude))
                                        .width(4).color(Color.BLUE));

                }
                pDialog.dismiss();

        }
}

/* Method to decode polyline points */
private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
                int b, shift = 0, result = 0;
                do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                        b = encoded.charAt(index++) - 63;
                        result |= (b & 0x1f) << shift;
                        shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                                (((double) lng / 1E5)));
                poly.add(p);
        }

        return poly;
}
}
