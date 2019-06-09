package com.movilidaduniquindio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ReciclerVewAdaptador extends RecyclerView.Adapter<ReciclerVewAdaptador.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private String numDoc;
        private String idVehiculo;
        private LatLng latLngInicio;
        private LatLng latLngFin;
        private TextView fecha;
        private TextView hora;
        private TextView conductor;
        private TextView puestos;
        private TextView observacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fecha=(TextView)itemView.findViewById(R.id.tvFecha);
            hora=(TextView)itemView.findViewById(R.id.tvHora);
            conductor=(TextView)itemView.findViewById(R.id.tvConductor);
            observacion=(TextView)itemView.findViewById(R.id.tvDescripcion);
            puestos=(TextView)itemView.findViewById(R.id.tvPuestos);
        }
    }

    private List<Servicio> servicioList;

    public ReciclerVewAdaptador(List<Servicio> servicioList) {
        this.servicioList = servicioList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_servicio,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.conductor.setText(servicioList.get(i).getConductor());
        viewHolder.hora.setText(servicioList.get(i).getHora());
        viewHolder.fecha.setText(servicioList.get(i).getFecha());
        viewHolder.observacion.setText(servicioList.get(i).getObservacion());
        viewHolder.puestos.setText(Integer.toString(servicioList.get(i).getPuestos()));
    }

    @Override
    public int getItemCount() {
        return servicioList.size();
    }
}
