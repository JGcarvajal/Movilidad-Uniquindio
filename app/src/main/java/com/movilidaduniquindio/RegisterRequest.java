package com.movilidaduniquindio;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL="http://169b62b0.ngrok.io/movilidadUniquindio/registerPHP.php";
    private Map<String,String> params;

    public RegisterRequest (Usuario usuario, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();

        String nombre =usuario.getNombres();
        String apellidos =usuario.getApellidos();
        String celular =Integer.toString(usuario.getTelefono());
        String correo =usuario.getCorreo();
        String clave =usuario.getClave();
        String identificacion =usuario.getIdentificacion();
        String facultad =usuario.getFacultad();
        String fNacimiento =usuario.getfNacimiento();
        String direccion =usuario.getDireccion();
        String latitud =usuario.getLatitud();
        String longitud =usuario.getLongitud();

        params.put("nombres",nombre);
        params.put("apellidos",apellidos);
        params.put("identificacion",identificacion);
        params.put("fNacimiento",fNacimiento);
        params.put("correo",correo);
        params.put("clave",clave);
        params.put("celular",celular);
        params.put("direccion",direccion);
        params.put("facultad",facultad);
        params.put("latitud",latitud);
        params.put("longitud",longitud);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
