package com.movilidaduniquindio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CrearServicio2 extends AppCompatActivity {

    private TextView tvFecha,tvHora;
    private EditText etObservacion,etPuestos;

    Date dFecha,dHora;
    private Usuario usuario;
    private LatLng latLngIni;
    private LatLng latLngFin;
    Coordenadas coordenads;
    Preference preference;
    Servicio servicio;
    CrearServiciosRequest crearServiciosRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_servicio2);

        tvFecha=(TextView)findViewById(R.id.tvFecha);
        tvHora=(TextView)findViewById(R.id.tvhora);
        etObservacion=(EditText) findViewById(R.id.etObservacion);
        etPuestos=(EditText)findViewById(R.id.etPuestos);
        usuario = Preference.getSavedObjectFromPreference(this,
                "mPreference", "USER", Usuario.class);



        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dFecha=new Date();
                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Calendar ca=Calendar.getInstance();
                String date[] =dateFormat.format(ca.getTime()).split("/");

                int year=Integer.parseInt(date[0]);
                int month=Integer.parseInt(date[1])-1;
                final int day=Integer.parseInt(date[2]);

                final DatePickerDialog datePickerDialog=new DatePickerDialog(CrearServicio2.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                tvFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                                try {
                                    dFecha=dateFormat.parse(dayOfMonth+"/"+month+"/"+year);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        },year,month,day);


                datePickerDialog.show();
            }
        });

        tvHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dHora=new Date();
                final int hour=dHora.getHours();
                int minute=dHora.getMinutes();

                TimePickerDialog timePickerDialog=new TimePickerDialog(CrearServicio2.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvHora.setText(hourOfDay+":"+minute);

                                dHora.setHours(hour);
                                dHora.setMinutes(minute);
                            }
                        },hour,minute,false);


                timePickerDialog.show();
            }
        });
    }



    public void OnClickCrear(View view) {
        Servicio servicio;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hhmm", Locale.getDefault());
        SimpleDateFormat horaFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

        final String numDoc = usuario.getIdentificacion()+dateFormat.format(dFecha)+timeFormat.format(dHora);
        final LatLng latLngInicio=latLngIni;
        final LatLng latLngFini=latLngFin;
        final String fecha=dateFormat.format(dFecha);
        final String hora=horaFormat.format(dHora);
        final String conductor=usuario.getIdentificacion();
        final int puestos=Integer.parseInt(etPuestos.getText().toString());
        final String observacion=etObservacion.getText().toString();

        servicio=new Servicio(numDoc,latLngInicio,latLngFini,fecha,hora,conductor,puestos,
                observacion,"MZC78B");

        if(verificarServicio(servicio)) {

            Response.Listener<String> responsListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Toast.makeText(getBaseContext(), "Servicio creado con exito!! ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(CrearServicio2.this, Servicios.class);
                            CrearServicio2.this.startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CrearServicio2.this);
                            builder.setMessage("Error en el registro").setNegativeButton("OK", null)
                                    .create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            CrearServiciosRequest crearServiciosRequest = new CrearServiciosRequest(servicio, responsListener);
            RequestQueue requestQueue = Volley.newRequestQueue(CrearServicio2.this);
            requestQueue.add(crearServiciosRequest);
        }else{
            Toast.makeText(getBaseContext(),"Por favor llene todos los campos ",
                    Toast.LENGTH_LONG).show();
        }

    }

    private boolean verificarServicio(Servicio servicio){

        if(servicio==null || servicio.getNumDoc()==null|| servicio.getLatLngInicio()==null||
                servicio.getLatLngFin()==null||servicio.getFecha()==null||servicio.getHora()==null||
                servicio.getIdVehiculo()==null){
            return false;
        }
        else{
            return true;
        }
    }
}
