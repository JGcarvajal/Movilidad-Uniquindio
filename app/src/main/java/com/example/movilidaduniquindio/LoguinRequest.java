package com.example.movilidaduniquindio;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoguinRequest extends StringRequest {

    private static final String LOGUIN_REQUEST_URL="http://c7b72675.ngrok.io/movilidadUniquindio/loginpHP.php";
    private Map<String,String> params;

    public LoguinRequest (String correo, String clave, Response.Listener<String> listener){
        super(Request.Method.POST,LOGUIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("correo",correo);
        params.put("clave",clave);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}