package com.movilidaduniquindio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class Coordenadas extends AppCompatActivity implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private double latitud;
    private double longitud;
private final int REQUUEST_ACCESS_FINE=1;
private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenadas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnAceptar=(Button)findViewById(R.id.btnAceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            guardarCoordenadas(latitud+";"+longitud);

           Coordenadas.this.finish();
              /**  Intent intent =new Intent(Coordenadas.this,Registro.class);
                Coordenadas.this.startActivity(intent);**/
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        miUbicacion();
        googleMap.setOnMarkerDragListener(this);
    }

    public void agregarMarker(double latitud, double longitud) {

        LatLng ubicacion = new LatLng(latitud, longitud);
        marker = mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación")
                .snippet("Arrastra para tomar coordenadas").draggable(true));

        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(ubicacion, 16);
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarubicacion(Location location) {
        if (location != null) {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            agregarMarker(latitud, longitud);
        }
    }

    private void miUbicacion() {
         double lat;
         double log;
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
             Manifest.permission.ACCESS_FINE_LOCATION},REQUUEST_ACCESS_FINE);
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarubicacion(location);
        lat=location.getLatitude();
        log=location.getLongitude();
        guardarCoordenadas(lat+";"+log);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double lat=marker.getPosition().latitude;
        double log=marker.getPosition().longitude;
    String newTitle=String.format(Locale.getDefault(),
           lat+" "+log,lat,log);

    setTitle(newTitle);
    }
    private void guardarCoordenadas(String coordenadas) {

        SharedPreferences settings = getSharedPreferences(Constantes.ALMACENAR_COORDENADAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constantes.COORDENADAS, coordenadas);
        editor.commit();
    }

}
