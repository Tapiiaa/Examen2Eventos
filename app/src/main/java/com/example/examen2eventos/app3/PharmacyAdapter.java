package com.example.examen2eventos.app3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen2eventos.R;

import java.util.List;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {

    private final Context context;
    private final List<Pharmacy> pharmacyList;

    public PharmacyAdapter(Context context, List<Pharmacy> pharmacyList) {
        this.context = context;
        this.pharmacyList = pharmacyList;
    }

    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pharmacy, parent, false);
        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacyList.get(position);
        holder.txtName.setText(pharmacy.getName());
        holder.txtLatitude.setText(String.valueOf(pharmacy.getLatitude()));
        holder.txtLongitude.setText(String.valueOf(pharmacy.getLongitude()));

        // Configurar click en cada ítem para abrir detalles
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PharmacyDetailActivity.class);
            intent.putExtra("name", pharmacy.getName());
            intent.putExtra("latitude", pharmacy.getLatitude());
            intent.putExtra("longitude", pharmacy.getLongitude());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

    public static class PharmacyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtLatitude, txtLongitude;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtLatitude = itemView.findViewById(R.id.txt_latitude);
            txtLongitude = itemView.findViewById(R.id.txt_longitude);
        }
    }
}
