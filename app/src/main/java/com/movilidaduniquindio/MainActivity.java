package com.movilidaduniquindio;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private TextView tvRegistrar,etEntrar ;
    private EditText etCorreo,etClave;
    private Usuario usuario;
    LoguinRequest loguinRequest;
    Constantes constantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvRegistrar=(TextView) findViewById(R.id.tvRegistrarse);
        etEntrar=(TextView) findViewById(R.id.tvEntrar);
        etCorreo=(EditText) findViewById(R.id.etCorreo);
        etClave=(EditText) findViewById(R.id.etclave);


        etEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo =etCorreo.getText().toString();
                final String clave =etClave.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse =new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");

                            if (success){
                                String nombres= jsonResponse.getString("nombres");
                                String apellidos= jsonResponse.getString("apellidos");
                                String telefono= jsonResponse.getString("telefono");
                                String correo= jsonResponse.getString("correo");
                                String clave= jsonResponse.getString("clave");
                                String identificacion= jsonResponse.getString("num_id");
                                String facultad= jsonResponse.getString("facultad");
                                String fNacimineto= jsonResponse.getString("fNacimiento");
                                String direccion= jsonResponse.getString("direccion");
                                String latitud= jsonResponse.getString("latitud");
                                String longitud= jsonResponse.getString("longitud");

                                usuario=new Usuario(nombres,apellidos,telefono,
                                        correo,clave,identificacion,facultad,fNacimineto,direccion,
                                        latitud,longitud);

                                Preference.saveObjectToSharedPreference(MainActivity.this,
                                        "mPreference", "USER", usuario);

                                Toast.makeText(getBaseContext(),"hola "+nombres+" "+apellidos,
                                        Toast.LENGTH_LONG).show();

                                Intent intent =new Intent(MainActivity.this, Servicios.class);
                                MainActivity.this.startActivity(intent);


                            }else{
                                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Error en de loguin").setNegativeButton("Ok",null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LoguinRequest loguinRequest=new LoguinRequest(correo,clave,responseListener);
                RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(loguinRequest);
            }
        });

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,Registro.class);
                MainActivity.this.startActivity(intent);
            }
        });


    }
}