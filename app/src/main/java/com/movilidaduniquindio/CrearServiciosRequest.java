package com.movilidaduniquindio;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class CrearServiciosRequest extends StringRequest {

    private static final String CREAR_SERVICIO_REQUEST_URL=Constantes.SERVIDOR+"/servicios/CrearServicio.php";
    private Map<String,String> params;

    public CrearServiciosRequest (Servicio servicio, Response.Listener<String> listener){
        super(Request.Method.POST,CREAR_SERVICIO_REQUEST_URL,listener,null);
        params=new HashMap<>();
        final String numDoc=servicio.getNumDoc();
        final String idVehiculo=servicio.getIdVehiculo();
       final LatLng latLngIni=servicio.getLatLngInicio();
        final LatLng latLngFin=servicio.getLatLngFin();
        final String fecha=servicio.getFecha();
        final String hora=servicio.getHora();
        final String conductor=servicio.getConductor();
        final String puestos= Integer.toString(servicio.getPuestos());
        final String observacion=servicio.getObservacion();



        params.put("numDoc",numDoc);
        params.put("idVehiculo",idVehiculo);
        params.put("latInicio",Double.toString(latLngIni.latitude));
        params.put("logInicio",Double.toString(latLngIni.longitude));
        params.put("latFin",Double.toString(latLngFin.latitude));
        params.put("logFin",Double.toString(latLngFin.longitude));
        params.put("fecha",fecha);
        params.put("hora",hora);
        params.put("conductor",conductor);
        params.put("puestos",puestos);
        params.put("observacion",observacion);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
