package com.movilidaduniquindio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Servicios extends AppCompatActivity {
private BottomNavigationView bMenu;
private Usuario usuario;

BottomNavigationView btnav_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        btnav_menu=(BottomNavigationView)findViewById(R.id.btnav_menu);
        btnav_menu.setOnNavigationItemSelectedListener( navListener);
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
}
