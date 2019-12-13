package com.example.aplikasikrs.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasikrs.Admin.Model.Dosen;
import com.example.aplikasikrs.Network.DefaultResult;
import com.example.aplikasikrs.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response; //menghasilkan data JSON
import com.example.aplikasikrs.Network.GetDataService;
import com.example.aplikasikrs.Network.RetrofitClientInstance;


public class EditMhsActivity extends AppCompatActivity {

    EditText edtId, edtNama, edtNim, edtAlamat, edtEmail;
    Intent mIntent = getIntent();
    GetDataService service;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mhs);
        this.setTitle("SI KRS - Hai Admin");

        Button btnEdit = (Button)findViewById(R.id.btnEditDosen);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditMhsActivity.this);

                builder.setMessage("Anda yakin untuk menyimpan?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(EditMhsActivity.this, "Batal Update", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestUpdateDosen();
                            }
                        });

                AlertDialog dialog = builder.create(); dialog.show();
            }
        });

    }

    private void requestUpdateDosen(){
        edtNama = (EditText)findViewById(R.id.edtNamaMhs);
        edtNim = (EditText)findViewById(R.id.edtNimMhs);
        edtAlamat = (EditText)findViewById(R.id.edtAlamatMhs);
        edtEmail = (EditText)findViewById(R.id.edtEmailMhs);

        edtId.setText(mIntent.getStringExtra("id"));
        edtId.setTag(edtId.getKeyListener());
        edtId.setKeyListener(null);
        edtNama.setText(mIntent.getStringExtra("nama"));
        edtNim.setText(mIntent.getStringExtra("nim"));
        edtAlamat.setText(mIntent.getStringExtra("alamat"));
        edtEmail.setText(mIntent.getStringExtra("email"));

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        progressDialog =  ProgressDialog.show(this, null, "Loading...", true, false);

        Call<DefaultResult> call =  service.update_mhs(edtId.getText().toString(),edtNama.getText().toString(),edtNim.getText().toString(),
                edtAlamat.getText().toString(),edtEmail.getText().toString(),"https://pbs.twimg.com/profile_images/988598148091269121/TWFZoPHC_400x400.jpg",
                "72170119");
        call.enqueue(new Callback<DefaultResult>() {
            @Override
            public void onResponse(Call<DefaultResult> call, Response<DefaultResult> response) {
                progressDialog.dismiss();
                Toast.makeText(EditMhsActivity.this,"Update Berhasil",Toast.LENGTH_LONG).show();
                Intent refresh = new Intent(EditMhsActivity.this, RecyclerViewDaftarMhs.class);
                startActivity(refresh);
                finish();

            }

            @Override
            public void onFailure(Call<DefaultResult> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditMhsActivity.this,"Error",Toast.LENGTH_SHORT);
            }
        });
    }
}
