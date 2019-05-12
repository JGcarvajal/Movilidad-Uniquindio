package com.movilidaduniquindio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Registro extends AppCompatActivity  {
    private Spinner spFacultades;
    private TextView fechaNacimiento;
    private Usuario usuario;
    private Button btnregistrar;
    private ImageButton btnCoordenadas;

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
        btnCoordenadas=(ImageButton)findViewById(R.id.ibtCoordenadas);

        pintaButton(obtenerCoordenadas());

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nombres=eTnombre.getText().toString().trim();
                final String apellidos=eTapellidos.getText().toString().trim();
                final int telefono=Integer.parseInt(eTtelefono.getText().toString().trim());
                final String correo=eTcorreo.getText().toString().trim();
                final String clave=eTclave.getText().toString().trim();
                final String identificacion=eTidentificacion.getText().toString().trim();
                final String direccion =eTdireccion.getText().toString().trim();
                final String facultad = spFacultades.getSelectedItem().toString().trim();
                final String fechaNac =fechaNacimiento.getText().toString().trim();
                final String coordenadas[]=obtenerCoordenadas().split(";");
                final String lat=coordenadas[0].trim();
                final String log=coordenadas[1].trim();



                usuario=new Usuario(nombres,apellidos,telefono,correo,clave,identificacion,facultad,fechaNac,direccion,lat,log);
                if(validarDatos(usuario) ){
                Response.Listener<String> responsListener= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");

                            if(success) {
                                Toast.makeText(getBaseContext(),"Registro exitoso ",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Registro.this, Servicios.class);
                                Registro.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder =new AlertDialog.Builder(Registro.this);
                                builder.setMessage("Error en el registro").setNegativeButton("OK",null)
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
                else{
                    Toast.makeText(Registro.this,"Por favor llene todos los datos ",Toast.LENGTH_LONG).show();
                }



            }
        });

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(Registro.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                fechaNacimiento.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                            }
                        },year,month,day );


                datePickerDialog.show();
            }
        });



        spFacultades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(),"Seleccionó: " +parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCoordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registro.this,Coordenadas.class);
                Registro.this.startActivity(intent);
            }
        });
    }

    private String obtenerCoordenadas() {

        SharedPreferences settings = getSharedPreferences(Constantes.ALMACENAR_COORDENADAS, MODE_PRIVATE);
        return settings.getString(Constantes.COORDENADAS, "");
    }

    private void pintaButton(String coodenadas){

        if(!coodenadas.equals("")){
            btnCoordenadas.setBackgroundColor(Color.parseColor("#FC897C"));
        }else{
            btnCoordenadas.setBackgroundColor(Color.parseColor("#48F879"));

        }
    }

    private boolean validarDatos(Usuario usuario){

        if(usuario==null||usuario.getNombres()==null||usuario.getApellidos()==null||
        usuario.getClave()==null||usuario.getDireccion()==null||usuario.getCorreo()==null||
        usuario.getFacultad()==null||usuario.getfNacimiento()==null||usuario.getIdentificacion()==null||
        usuario.getLatitud()==null||usuario.getLongitud()==null||usuario.getTelefono()==0) {
        return false;
        }
        else{
        final String[] correo=usuario.getCorreo().split("@");

        if(correo.length==2) {
            if (correo[1].equals("uqvirtual.edu.co") || correo[1].equals("uniquindio.edu.co")) {
                return true;
            } else{
                Toast.makeText(Registro.this,"Este no es un correo valido ",Toast.LENGTH_LONG).show();
                return false;
            }

        }

        return false;
    }

    }

}
