package com.movilidaduniquindio;

import com.google.android.gms.maps.model.LatLng;

public class Servicio {

    private String numDoc;
    private String idVehiculo;
    private LatLng latLngInicio;
    private LatLng latLngFin;
    private String fecha;
    private String hora;
    private String conductor;
    private int puestos;    
    private String observacion;

    public Servicio(String numDoc, LatLng latLngIni, LatLng latLngFin,String fecha, String hora,
                    String conductor, int puestos, String observacion, String idVehiculo) {
        this.numDoc = numDoc;
        this.idVehiculo=idVehiculo;
        this.latLngInicio=latLngIni;
        this.latLngFin=latLngFin;
        this.fecha = fecha;
        this.hora = hora;
        this.conductor = conductor;
        this.puestos = puestos;
        this.observacion = observacion;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public LatLng getLatLngInicio() {
        return latLngInicio;
    }

    public void setLatLngInicio(LatLng latLngInicio) {
        this.latLngInicio = latLngInicio;
    }

    public LatLng getLatLngFin() {
        return latLngFin;
    }

    public void setLatLngFin(LatLng latLngFin) {
        this.latLngFin = latLngFin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public int getPuestos() {
        return puestos;
    }

    public void setPuestos(int puestos) {
        this.puestos = puestos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}
