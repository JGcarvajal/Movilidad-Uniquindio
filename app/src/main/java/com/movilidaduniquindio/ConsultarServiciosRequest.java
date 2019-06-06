package com.movilidaduniquindio;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class ConsultarServiciosRequest extends StringRequest {

    private static final String CREAR_SERVICIO_REQUEST_URL=Constantes.SERVIDOR+"/movilidadUniquindio/ConsultarServicios.php";
    private Map<String,String> params;

    public ConsultarServiciosRequest (Servicio servicio, Response.Listener<String> listener){

        super(Request.Method.POST,CREAR_SERVICIO_REQUEST_URL,listener,null);
    }

}
