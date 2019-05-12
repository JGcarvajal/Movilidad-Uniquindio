package com.movilidaduniquindio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

public class Servicios extends AppCompatActivity {
private BottomNavigationView bMenu;
private Usuario usuario;
public static final String TAG = Servicios.class.getName();

BottomNavigationView btnav_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        btnav_menu=(BottomNavigationView)findViewById(R.id.btnav_menu);
        btnav_menu.setOnNavigationItemSelectedListener( navListener);

        checkPermission();
    }
private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent=null;
                switch (menuItem.getItemId()){
                    case R.id.nav_login:
                        intent=new Intent(Servicios.this,MainActivity.class);
                        Servicios.this.startActivity(intent);
                        break;


                        default:
                            break;
                }

                return true;
            }
        };


    public void onClickCrearservicios(View view) {
        Intent intentCServ =new Intent(Servicios.this,CrearServicio.class);
        Intent intentLoguin =new Intent(Servicios.this,MainActivity.class);
        usuario = Preference.getSavedObjectFromPreference(this, "mPreference", "USER", Usuario.class);

        if (usuario==null) {
            Servicios.this.startActivity(intentLoguin);
    }
        else{
        Servicios.this.startActivity(intentCServ);}

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
