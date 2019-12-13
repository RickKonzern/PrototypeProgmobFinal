package com.example.aplikasikrs.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
//rick
import com.example.aplikasikrs.Admin.CreateMhsActivity;
import com.example.aplikasikrs.Admin.Model.Mahasiswa;
import com.example.aplikasikrs.MainActivity;
import com.example.aplikasikrs.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private ArrayList<Mahasiswa> dataList;
    private Context context;
    public MahasiswaAdapter (ArrayList<Mahasiswa> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_view_mahasiswa,parent,false); //
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //gunanya utk memasukkan data
        holder.txtNim.setText(dataList.get(position).getNim());
        holder.txtNamaMhs.setText(dataList.get(position).getNama());
        holder.txtEmailMhs.setText(dataList.get(position).getEmailMhs());
        holder.txtAlamatMhs.setText(dataList.get(position).getAlamatMhs());
        holder.imgFoto.getLayoutParams().width = 200;
        holder.imgFoto.getLayoutParams().height = 200;
        if(dataList.get(position).getFoto() != null){
            Picasso.with(this.context)
                    .load("https://kpsi.fti.ukdw.ac.id/progmob/"+dataList.get(position).getFoto())
                    .into(holder.imgFoto);
        }
    }

    @Override
    public int getItemCount() { //berguna untuk menghitung jumlah data yang ada
        return (dataList != null)? dataList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener { //utk menghubungkan dari txt
        private TextView txtNim, txtNamaMhs, txtEmailMhs, txtAlamatMhs;
        private ImageView imgFoto;
        private CardView cv;

        public ViewHolder(View view){
            super(view);
            txtNim = view.findViewById(R.id.txtNimMhs);
            txtNamaMhs = view.findViewById(R.id.txtNamaMhs);
            txtEmailMhs = view.findViewById(R.id.txtEmailMhs);
            txtAlamatMhs = view.findViewById(R.id.txtAlamatMhs);
            imgFoto = view.findViewById(R.id.imgFotoMhs);
            //cv = view.findViewById(R.id.cvMhs);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Pilih Aksi");
            menu.add(this.getAdapterPosition(), v.getId(),0,"Ubah Data Mahasiswa");
            menu.add(this.getAdapterPosition(), v.getId(),0,"Hapus Data Mahasiswa");
        }
    }
}
