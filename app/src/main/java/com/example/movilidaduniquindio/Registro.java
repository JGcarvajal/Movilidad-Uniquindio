package com.example.movilidaduniquindio;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Registro extends AppCompatActivity  {
    private Spinner spFacultades;
    private TextView fechaNacimiento;
    private Usuario usuario;
    private Button btnregistrar;

    private EditText eTnombre;
    private EditText eTapellidos;
    private EditText eTtelefono;
    private EditText eTcorreo;
    private EditText eTclave;
    private EditText eTidentificacion;
    private EditText eTdireccion;

    DatePickerDialog.OnDateSetListener myFechaListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        spFacultades=(Spinner)findViewById(R.id.spFacultades);
        fechaNacimiento=(TextView)findViewById(R.id.fechaNacimiento);

        eTnombre=(EditText)findViewById(R.id.etNombre);
        eTapellidos=(EditText)findViewById(R.id.etApellidos);
        eTtelefono=(EditText)findViewById(R.id.etCelular);
        eTcorreo=(EditText)findViewById(R.id.etCorreo);
        eTclave=(EditText)findViewById(R.id.etclave);
        eTidentificacion=(EditText)findViewById(R.id.etIdentificacion);
        eTdireccion=(EditText)findViewById(R.id.etDireccion);
        btnregistrar=(Button)findViewById(R.id.btnRegistrar);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombres=eTnombre.getText().toString();
                final String apellidos=eTapellidos.getText().toString();
                final int telefono=Integer.parseInt(eTtelefono.getText().toString());
                final String correo=eTcorreo.getText().toString();
                final String clave=eTclave.getText().toString();
                final String identificacion=eTidentificacion.getText().toString();
                final String direccion =eTdireccion.getText().toString();
                Spinner spinner = (Spinner) findViewById(R.id.spFacultades);
                final String facultad = spinner.getSelectedItem().toString();
                final String fechaNac =fechaNacimiento.getText().toString();


                usuario=new Usuario(nombres,apellidos,telefono,correo,clave,identificacion,facultad,fechaNac,direccion);

                Response.Listener<String> responsListener= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");

                            if(success) {
                                Toast.makeText(getBaseContext(),"Registro exitoso ",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Registro.this, MainActivity.class);
                                Registro.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder =new AlertDialog.Builder(Registro.this);
                                builder.setMessage("Error en el registro").setNegativeButton("Retry",null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest= new RegisterRequest(usuario,responsListener);
                RequestQueue requestQueue= Volley.newRequestQueue(Registro.this);
                requestQueue.add(registerRequest);

            }
        });

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Registro.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,myFechaListener,year,month,day );

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        myFechaListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               month= month+1;
                Log.d("RegistroUTF","onDataSet: Date "+year+"/"+month+"/"+dayOfMonth);

                String fecha=year+"/"+month+"/"+dayOfMonth;
                fechaNacimiento.setText(fecha);

            }
        };

        spFacultades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(),"Seleccionó: " +parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
