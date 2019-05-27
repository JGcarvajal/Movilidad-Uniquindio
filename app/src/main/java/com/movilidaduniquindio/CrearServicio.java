package com.movilidaduniquindio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CrearServicio extends AppCompatActivity implements OnMapReadyCallback {

    private Spinner spInicio,spFin;

    private GoogleMap mMap;
    private double latitud;
    private double longitud;
    private double latUQ=4.553994;
    private double longUQ= -75.660229;
    private Usuario usuario;
    private LatLng latLngIni;
    private LatLng latLngFin;
    Coordenadas coordenads;
    Preference preference;
    Servicio servicio;
    CrearServiciosRequest crearServiciosRequest;
    private Marker markerInicio;
    private Marker markerFin;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_servicio);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spInicio=(Spinner) findViewById(R.id.spInicio);
        spFin=(Spinner) findViewById(R.id.spFin);
        usuario = Preference.getSavedObjectFromPreference(this,
                "mPreference", "USER", Usuario.class);

        spInicio.setOnItemSelectedListener(spInicioListener);
        spFin.setOnItemSelectedListener(spFinListener);

    }

    private AdapterView.OnItemSelectedListener spInicioListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String seleccion=spInicio.getItemAtPosition(position).toString();
            spInicio.setBackgroundColor (Color.parseColor("#f7a8ad"));

            if (position!=0){
                spInicio.setBackgroundColor (Color.parseColor("#a1edac"));

                if(seleccion.equals("Universidad")){
                    latLngIni=new LatLng(latUQ,longUQ);

                    agregarMarker(latLngIni,markerInicio);
                }
                if(seleccion.equals("Casa")){
                    double latitud=Double.parseDouble(usuario.getLatitud());
                    double longitud=Double.parseDouble(usuario.getLongitud());

                    latLngIni=new LatLng(latitud,longitud);

                    agregarMarker(latLngIni,markerInicio);
                }
                if(seleccion.equals("Otro")){
                    double latitud;
                    double longitud;

                    Intent intent=new Intent(CrearServicio.this,Coordenadas.class);
                    CrearServicio.this.startActivity(intent);

                    final String coordenadas[]=obtenerCoordenadas().split(";");
                    latitud=Double.parseDouble(coordenadas[0]);
                    longitud=Double.parseDouble(coordenadas[1]);

                    latLngIni=new LatLng(latitud,longitud);

                    agregarMarker(latLngIni,markerInicio);
                }
            }
        }



        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            spInicio.setBackgroundColor(Color.parseColor("#f7a8ad"));
        }
    };

    private AdapterView.OnItemSelectedListener spFinListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String seleccion=spFin.getItemAtPosition(position).toString();
            spFin.setBackgroundColor (Color.parseColor("#f7a8ad"));

            if (position!=0){
                spFin.setBackgroundColor (Color.parseColor("#a1edac"));

                if(seleccion.equals("Universidad")){
                    latLngFin=new LatLng(latUQ,longUQ);

                    agregarMarker(latLngFin,markerFin);
                }
                if(seleccion.equals("Casa")){
                    double latitud=Double.parseDouble(usuario.getLatitud());
                    double longitud=Double.parseDouble(usuario.getLongitud());

                    latLngFin=new LatLng(latitud,longitud);
                    agregarMarker(latLngFin,markerFin);

                }
                if(seleccion.equals("Otro")){
                    double latitud;
                    double longitud;

                    Intent intent=new Intent(CrearServicio.this,Coordenadas.class);
                    CrearServicio.this.startActivity(intent);

                    final String coordenadas[]=obtenerCoordenadas().split(";");
                    latitud=Double.parseDouble(coordenadas[0]);
                    longitud=Double.parseDouble(coordenadas[1]);

                    latLngFin=new LatLng(latitud,longitud);
                    agregarMarker(latLngFin,markerFin);

                    if (markerFin !=null) {
                        markerFin.remove();
                    }

                    trazarRuta();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private String obtenerCoordenadas() {

        SharedPreferences settings = getSharedPreferences(Constantes.ALMACENAR_COORDENADAS, MODE_PRIVATE);
        return settings.getString(Constantes.COORDENADAS, "");
    }

    public void OnClickSiguiente(View view) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void agregarMarker(LatLng ubicacion, Marker miMarker) {

        if (miMarker !=null) {
            miMarker.remove();
        }

        miMarker = mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación")
                .snippet("Arrastra para tomar coordenadas").draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(ubicacion, 16);
        mMap.animateCamera(miUbicacion);
    }

    private void trazarRuta(){
       String url= gerRequestUrl(latLngIni,latLngFin);

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonObject=new JSONObject(response);

                    pintarRuta(jsonObject);

                    Log.i("Ruta",""+response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private void pintarRuta(JSONObject jso) {

                JSONArray jRoutes;
                JSONArray jLegs;
                JSONArray jSteps;

                try {
                    jRoutes = jso.getJSONArray("routes");
                    for (int i=0; i<jRoutes.length();i++){

                        jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                        for (int j=0; j<jLegs.length();j++){

                            jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                            for (int k = 0; k<jSteps.length();k++){


                                String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                                Log.i("end",""+polyline);
                                List<LatLng> list = PolyUtil.decode(polyline);
                                mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(5));



                            }



                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private String gerRequestUrl(LatLng inicio, LatLng fin) {
        //Construimos las direcciones de origen y fin de la ruta
        String st_Origen="origin="+inicio.latitude+","+inicio.longitude;
        String st_Fin="destination="+fin.latitude+","+fin.longitude;

        //enviamos el valor enable del sensor
        String sensor="sensor=false";

        //Modo de viaje
        String modo="mode=driving";

        //concatenamos todos los parametros

        String parametros=st_Origen+"&"+st_Fin+"&"+sensor+"&"+modo;

        //formato de salida
        String salida="json";

        //url para la peticion del json con la ruta
       String url="https://maps.googleapis.com/maps/api/directions/"+salida+"?"+parametros;

       return url;
    }
}
