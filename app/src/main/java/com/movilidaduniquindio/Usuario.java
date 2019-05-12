package com.movilidaduniquindio;


public class Usuario {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String clave;
    private String identificacion;
    private String facultad;
    private String fNacimiento;
    private String direccion;
    private String latitud;
    private String longitud;

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String telefono, String correo, String clave,
                   String identificacion, String facultad, String fNacimiento, String direccion,
                   String latitud, String longitud) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.identificacion = identificacion;
        this.facultad = facultad;
        this.fNacimiento = fNacimiento;
        this.direccion = direccion;
        this.latitud=latitud;
        this.longitud=longitud;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(String fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getDireccion() {  return direccion;   }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getLatitud() {    return latitud;  }

    public void setLatitud(String latitud) {     this.latitud = latitud; }

    public String getLongitud() {    return longitud;  }

    public void setLongitud(String longitud) {     this.longitud = longitud;  }
}
