package mec.mec.excel16;

/**
 * Created by jerin on 27/7/16.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;

import com.directions.route.Routing;
import com.directions.route.RoutingListener;


import com.getbase.floatingactionbutton.FloatingActionButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jerin on 6/5/16.
 */
public class mapfragment extends Fragment implements OnMapReadyCallback{

    private SupportMapFragment map;
    GoogleMap gmap;
    private final LatLng LOCATION_MEC = new LatLng(10.02836, 76.328829);
    private static View view;
    double latitude,longitude;
    GPSTracker gps;
    FloatingActionButton direction,indoor;
    LatLng startingpoint;
    private List<Polyline> polylines;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    private static final int COLORS = R.color.colorAccent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            setUpMap(inflater, container);
        }
        catch(Exception e) {

        }
        return  view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Read Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);

                if (showRationale) {
                    // do something here to handle degraded mode
                }
                    else {
                        Toast.makeText(getContext(), "Read Location permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
    }

    private void setUpMap(LayoutInflater inflater, ViewGroup container) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.mapfragment, container, false);
            polylines = new ArrayList<>();
            gps = new GPSTracker(getActivity());

            if(gps.canGetLocation()){
                latitude= gps.getLatitude();
                longitude = gps.getLongitude();

            }else{

                gps.showSettingsAlert();
            }


            // Toast.makeText(getActivity(), latitude+"long : "+longitude, Toast.LENGTH_SHORT).show();



        } catch (InflateException e) {
		        /* map is already there, just return view as it is */
        }
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        map.getMapAsync(this);
        direction=(FloatingActionButton) view.findViewById(R.id.direction);
        indoor=(FloatingActionButton) view.findViewById(R.id.ar);
        indoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissionForLocation();

                if(gps.canGetLocation()){
                    latitude= gps.getLatitude();
                    longitude = gps.getLongitude();

                }else{

                    gps.showSettingsAlert();
                }

                RoutingListener listener=new RoutingListener() {
                    @Override
                    public void onRoutingFailure(RouteException e) {

                    }

                    @Override
                    public void onRoutingStart() {

                    }

                    @Override
                    public void onRoutingSuccess(ArrayList<Route> route, int  shortestRouteIndex) {
                        CameraUpdate center = CameraUpdateFactory.newLatLng(startingpoint);
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                        gmap.moveCamera(center);
                        if(polylines.size()>0) {
                            for (Polyline poly : polylines) {
                                poly.remove();
                            }
                        }
                        polylines = new ArrayList<>();
//add route(s) to the map.
                        int i=0;
//In case of more than 5 alternative routes
                        int colorIndex = COLORS;
                        PolylineOptions polyOptions = new PolylineOptions();
                        polyOptions.color(getResources().getColor(COLORS));
                        polyOptions.width(10 + i * 3);
                        polyOptions.addAll(route.get(i).getPoints());
                        Polyline polyline = gmap.addPolyline(polyOptions);
                        polylines.add(polyline);
                        Toast.makeText(getActivity(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();

// Start marker
                        MarkerOptions options = new MarkerOptions();
                        options.position(startingpoint);
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapme));
                        gmap.addMarker(options);
// End marker
                        options = new MarkerOptions();
                        options.position(LOCATION_MEC);
                        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapcolllge));
                        gmap.addMarker(options);

                    }

                    @Override
                    public void onRoutingCancelled() {

                    }
                };
                startingpoint=new LatLng(latitude,longitude);

                Routing routing = new Routing.Builder()
                        .travelMode(AbstractRouting.TravelMode.DRIVING)
                        .withListener(listener)
                        .alternativeRoutes(true)
                        .waypoints(startingpoint, LOCATION_MEC)
                        .build();
                routing.execute();
            }
        });


    }

    public void getPermissionForLocation() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(LOCATION_MEC).title("Model Engineering College"));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_MEC, 16);
        googleMap.animateCamera(update);
        gmap=googleMap;

    }
}

