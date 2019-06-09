package com.movilidaduniquindio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Servicios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = Servicios.class.getName();
    private Usuario usuario;
    private RecyclerView recyclerViewServicio;
    private ReciclerVewAdaptador adaptadorServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewServicio=(RecyclerView)findViewById(R.id.RecyclerServicio);
        recyclerViewServicio.setLayoutManager(new LinearLayoutManager(this));
        adaptadorServicio=new ReciclerVewAdaptador(obtenerServicios());
        recyclerViewServicio.setAdapter(adaptadorServicio);

        checkPermission();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(FlButtonOnClickLister);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private List<Servicio> obtenerServicios(){
        List<Servicio> servicioList=new ArrayList<>();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LatLng inicio = new LatLng(0,0);
                    LatLng fin= new LatLng(0,0);
                    Servicio servicio;
                    JSONArray jsonArray =new JSONArray(response);
                    boolean success=jsonArray.getJSONObject(0).getBoolean("success");

                    if (success){

                        for (int i =1;i<jsonArray.length();i++) {
                            String longDestino = jsonArray.getJSONObject(i).getString("longDestino");
                            String latDestino = jsonArray.getJSONObject(i).getString("latDestino");
                            String longOrigen = jsonArray.getJSONObject(i).getString("longOrigen");
                            String latOrigen = jsonArray.getJSONObject(i).getString("latOrigen");
                            String descripcion = jsonArray.getJSONObject(i).getString("descripcion");
                            String conductor = jsonArray.getJSONObject(i).getString("conductor");
                            String hora = jsonArray.getJSONObject(i).getString("hora");
                            String fecha = jsonArray.getJSONObject(i).getString("fecha");
                            String idVehiculo = jsonArray.getJSONObject(i).getString("idVehiculo");
                            String numPuestos = jsonArray.getJSONObject(i).getString("numPuestos");
                            String codRuta = jsonArray.getJSONObject(i).getString("codRuta");

                            inicio=new LatLng(Double.parseDouble(latOrigen),Double.parseDouble(longOrigen));
                            fin=new LatLng(Double.parseDouble(latDestino),Double.parseDouble(longDestino));

                            servicio = new Servicio(codRuta,inicio,fin,fecha,hora,conductor,Integer.parseInt(numPuestos),descripcion,idVehiculo);

                        }
                    }else{
                        AlertDialog.Builder builder =new AlertDialog.Builder(Servicios.this);
                        builder.setMessage("Error cargando servicios").setNegativeButton("Ok",null)
                                .create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        ConsultarServiciosRequest consultarServiciosRequest=new ConsultarServiciosRequest(responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(Servicios.this);
        requestQueue.add(consultarServiciosRequest);
        return servicioList;
    }


    private FloatingActionButton.OnClickListener FlButtonOnClickLister =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /**   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             .setAction("Action", null).show();**/

            Intent intentCServ =new Intent(Servicios.this,CrearServicio.class);
            Intent intentLoguin =new Intent(Servicios.this,MainActivity.class);
            usuario = Preference.getSavedObjectFromPreference(Servicios.this, "mPreference", "USER", Usuario.class);

            if (usuario==null ||usuario.getLongitud()==null) {
                Servicios.this.startActivity(intentLoguin);
            }
            else{
                Servicios.this.startActivity(intentCServ);}
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.servicios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent=null;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_entrar) {
            intent=new Intent(Servicios.this,MainActivity.class);
            Servicios.this.startActivity(intent);

            return true;
        }

        if (id == R.id.action_salir) {
           usuario=new Usuario();

            Preference.saveObjectToSharedPreference(Servicios.this,
                    "mPreference", "USER", usuario);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkPermission() {
        //permisos almacenamiento
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            checkPermissionStorage();
        } else
            //permisos telefono
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                checkPermissionPhone();
            } else
                //permisos ubicacion
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionLocation();
                } else
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionCamera();
                }
    }

    private void checkPermissionStorage() {
        Log.e(TAG, "checkPermissionStorage-> ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if ( ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE )) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constantes.RESP_PERMISOS_STORAGE);
            } else if (  ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)  ){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.RESP_PERMISOS_STORAGE);
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.RESP_PERMISOS_STORAGE);
            }
        }
    }

    private void checkPermissionPhone() {
        Log.e(TAG, "checkPermissionPhone-> ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, Constantes.RESP_PERMISOS_PHONE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, Constantes.RESP_PERMISOS_PHONE);
            }
        }
    }

    private void checkPermissionCamera() {
        Log.e(TAG, "checkPermissionCamera-> ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constantes.RESP_PERMISOS_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constantes.RESP_PERMISOS_CAMERA);
            }
        }
    }

    private void checkPermissionLocation() {
        Log.e(TAG, "checkPermissionLocation-> ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constantes.RESP_PERMISOS_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constantes.RESP_PERMISOS_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constantes.RESP_PERMISOS_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_GRANTED" + 1);
                    checkPermission();
                } else {
                    // permission denied
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_DENIED" + 2);
                    //mostrarMensajePermisos(Const.RESP_PERMISOS_STORAGE);
                }
                return;
            }

            case Constantes.RESP_PERMISOS_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_GRANTED" + 1);
                    checkPermission();
                } else {
                    // permission denied
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_DENIED" + 2);
                    //mostrarMensajePermisos(Const.RESP_PERMISOS_PHONE);
                }
                return;
            }

            case Constantes.RESP_PERMISOS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_GRANTED" + 1);
                    checkPermission();
                } else {
                    // permission denied
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_DENIED" + 2);
                    //mostrarMensajePermisos(Const.RESP_PERMISOS_LOCATION);
                }
                return;
            }

            case Constantes.RESP_PERMISOS_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_GRANTED" + 1);
                    checkPermission();
                } else {
                    // permission denied
                    Log.e(TAG, "onRequestPermissionsResult-> PERMISSION_DENIED" + 2);
                    //mostrarMensajePermisos(Const.RESP_PERMISOS_CAMERA);
                }
                return;
            }

        }
    }
}
