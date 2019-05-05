package com.example.movilidaduniquindio;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CrearServicio extends AppCompatActivity {

private Spinner spInicio,spFin;
private TextView tvObservacio,tvFecha,tvHora;
    DatePickerDialog.OnDateSetListener myFechaListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_servicio);

        spInicio=(Spinner) findViewById(R.id.spInicio);
        spFin=(Spinner) findViewById(R.id.spFin);
        tvFecha=(TextView)findViewById(R.id.tvFecha);
        tvHora=(TextView)findViewById(R.id.tvhora);
        tvObservacio=(TextView)findViewById(R.id.tvObservacion);

        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(CrearServicio.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                tvFecha.setText(dayOfMonth+"/"+month+"/"+year);
                            }
                        },day,month,year);


                datePickerDialog.show();
            }
        });

        tvHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(CrearServicio.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            tvHora.setText(hourOfDay+":"+minute);
                            }
                        },hour,minute,false);


                timePickerDialog.show();
            }
        });



    }
}
