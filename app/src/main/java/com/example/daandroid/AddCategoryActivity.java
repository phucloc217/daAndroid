package com.example.daandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daandroid.models.TheLoai;
import com.example.daandroid.utls.DatabaseHandler;

public class AddCategoryActivity extends AppCompatActivity {
    EditText etTen,etMa;
    Button btnThem;
    TheLoai tl = null;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        addControls();
        getDataFromIntent();
        addEvents();
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(tl==null)
                   xuLyThem();
               else
                   xuLySua();
            }
        });
    }

    private void addControls() {
        etTen = findViewById(R.id.etTen);
        btnThem = findViewById(R.id.btnThemDanhMuc);
        etMa = findViewById(R.id.etMaDM);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("CHON")) {
            tl = (TheLoai) intent.getSerializableExtra("CHON");
            etTen.setText(tl.getTenTheLoai());
            etMa.setText(tl.getMaLoai()+"");
            etMa.setEnabled(false);
        }
    }
    private void xuLyThem() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        TheLoai theLoai = new TheLoai(ma,ten);

        try {
            ContentValues values = new ContentValues();
            values.put("id",theLoai.getMaLoai()+"");
            values.put("tenloai",theLoai.getTenTheLoai());
            if(databaseHandler.getReadableDatabase().insert("theloai",null,values)!=-1)
            {
                Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT);
                intent.putExtra("THEM", theLoai);

            }
            databaseHandler.close();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }catch (Exception ex)
        {
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG);
        }

    }
    private void xuLySua() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        tl.setMaLoai(ma);
        tl.setTenTheLoai(ten);
        ContentValues values = new ContentValues();
        values.put("tenloai", ten);
        databaseHandler.getReadableDatabase().update("theloai",values,"id =?",new String[]{ma+""});
        intent.putExtra("SUA", tl);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}