package com.example.movilidaduniquindio;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
private TextView tvRegistrar,etEntrar ;
private EditText etCorreo,etClave;
private Button btnCrearServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegistrar=(TextView) findViewById(R.id.tvRegistrarse);
        etEntrar=(TextView) findViewById(R.id.tvEntrar);
        etCorreo=(EditText) findViewById(R.id.etCorreo);
        etClave=(EditText) findViewById(R.id.etclave);

        btnCrearServicio=findViewById(R.id.btnCrearServicio);

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

                                Toast.makeText(getBaseContext(),"hola "+nombres+" "+apellidos+", estas logueado",
                                        Toast.LENGTH_LONG).show();

                            }else{
                                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Error en de loguin").setNegativeButton("Retri",null)
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

        btnCrearServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCServ =new Intent(MainActivity.this,CrearServicio.class);
                MainActivity.this.startActivity(intentCServ);
            }
        });
    }
}
