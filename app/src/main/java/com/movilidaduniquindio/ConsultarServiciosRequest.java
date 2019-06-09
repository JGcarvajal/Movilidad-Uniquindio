package com.movilidaduniquindio;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class ConsultarServiciosRequest extends StringRequest {

    private static final String CONSULTAR_SERVICIOS_REQUEST_URL=Constantes.SERVIDOR+"/movilidadUniquindio/ConsultarServicios.php";
    private Map<String,String> params;

    public ConsultarServiciosRequest (Response.Listener<String> listener){

        super(Request.Method.POST,CONSULTAR_SERVICIOS_REQUEST_URL,listener,null);
    }

}
