package com.example.aplikasikrs.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aplikasikrs.Admin.Adapter.MahasiswaAdapter;
import com.example.aplikasikrs.Admin.Model.Mahasiswa;
import com.example.aplikasikrs.Network.DefaultResult;
import com.example.aplikasikrs.Network.GetDataService;
import com.example.aplikasikrs.Network.RetrofitClientInstance;
import com.example.aplikasikrs.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewDaftarMhs extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MahasiswaAdapter mhsAdapter;
    private ArrayList<Mahasiswa> mhsList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_daftar_mhs);
        this.setTitle("SI KRS - Hai Admin");
        //tambahData();

        recyclerView = findViewById(R.id.rvMhs);
        //mhsAdapter = new MahasiswaAdapter(mhsList);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ArrayList<Mahasiswa>> call = service.getMhsAll("72170119");
        call.enqueue(new Callback<ArrayList<Mahasiswa>>() {
            @Override
            public void onResponse(Call<ArrayList<Mahasiswa>> call, Response<ArrayList<Mahasiswa>> response) {
                progressDialog.dismiss();

                mhsList = response.body();
                mhsAdapter = new MahasiswaAdapter(response.body());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecyclerViewDaftarMhs.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mhsAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Mahasiswa>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RecyclerViewDaftarMhs.this,"Error",Toast.LENGTH_SHORT);
            }
        });

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucreate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            Intent intent = new Intent(RecyclerViewDaftarMhs.this,CreateMhsActivity.class);
            startActivity(intent);
        }
        return  true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Mahasiswa mhs = mhsList.get(item.getGroupId());
        if (item.getTitle()== "Ubah Data Mahasiswa"){
            Intent intent = new Intent(RecyclerViewDaftarMhs.this, CreateMhsActivity.class);
            intent.putExtra("id_mhs",mhs.getId()); //(key, value) -> ketika manggil Dosen harus sama
            intent.putExtra("nama_mhs",mhs.getNama());
            intent.putExtra("nim",mhs.getNim());
            intent.putExtra("alamat_mhs",mhs.getAlamatMhs());
            intent.putExtra("email_mhs",mhs.getEmailMhs());
            intent.putExtra("foto_mhs",mhs.getFoto());
            intent.putExtra("is_update",true);
            startActivity(intent);

        }else if(item.getTitle() == "Hapus Data Mahasiswa"){
            //Toast.makeText(RecyclerViewDaftarDosen.this,"Hapus",Toast.LENGTH_LONG).show();

            progressDialog = new ProgressDialog(RecyclerViewDaftarMhs.this);
            progressDialog.show();

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<DefaultResult> call = service.delete_mhs(
                    mhs.getId(), "72170119");
            call.enqueue(new Callback<DefaultResult>() {
                @Override
                public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                    progressDialog.dismiss();
                    Toast.makeText(RecyclerViewDaftarMhs.this,"Hapus Berhasil",Toast.LENGTH_SHORT).show();
                    recreate();
                }

                @Override
                public void onFailure(Call<DefaultResult> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RecyclerViewDaftarMhs.this,"Hapus Gagal",Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onContextItemSelected(item);
    }


}
