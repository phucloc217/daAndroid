package com.example.daandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.daandroid.models.Sach;
import com.example.daandroid.models.TheLoai;
import com.example.daandroid.utls.DatabaseHandler;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class AddBookActivity extends AppCompatActivity {
    EditText etMa, etTen,etGia, etMota;
    Button btnHinh, btnLuu;
    Spinner spinner;
    ImageView ivHinh;
    Sach sach = null;
    TheLoai theLoai = null;
    ArrayList<Sach> dsSach;
    ArrayList<TheLoai> dsTheLoai;
    int SELECT_PICTURE = 200;
    ArrayAdapter<TheLoai> adapter;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        addControls();
        xulyChonViTriSpinner();
        addEvents();
    }

    private void addControls() {
        etMa = findViewById(R.id.etMaSach);
        etTen = findViewById(R.id.etTenSach);
        etGia = findViewById(R.id.etGia);
        etMota = findViewById(R.id.etMota);
        btnHinh = findViewById(R.id.btnChonHinh);
        btnLuu = findViewById(R.id.btnLuuSach);
        spinner = findViewById(R.id.spiner);
        ivHinh = findViewById(R.id.anhDaiDien);
        dsSach = new ArrayList<>();
        dsTheLoai = new ArrayList<>();
        Cursor cursor1 = databaseHandler.getReadableDatabase().rawQuery("select * From theloai",null);
        while (cursor1.moveToNext()) {
            TheLoai a = new TheLoai();
            a.setMaLoai(cursor1.getInt(0));
            a.setTenTheLoai(cursor1.getString(1));
            dsTheLoai.add(a);
        }
        adapter = new ArrayAdapter(AddBookActivity.this, android.R.layout.simple_spinner_item, dsTheLoai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void addEvents() {
        btnHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyChonHinh();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyThem();
            }
        });
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("CHON")) {
            sach = (Sach) intent.getSerializableExtra("CHON");
            etTen.setText(sach.getTenSach());
            etMa.setText(sach.getMaSach()+"");
            etGia.setText(sach.getGia()+"");
            etMota.setText(sach.getMoTa());

            etMa.setEnabled(false);
        }
    }
    private byte[] getByteArrayImage(ImageView hinhanh) {
        BitmapDrawable drawable = (BitmapDrawable) hinhanh.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private void xulyChonHinh() {
        btnHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChoose();
            }
        });
    }
    private void imageChoose() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        launchSomeActivity.launch(intent);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                        try {
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            InputStream i = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(i);
                            ivHinh.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                }
            });
    private int getPosition(Spinner sp, String chuoi) {
        for (int i = 0; i < sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(chuoi)) {
                return i;
            }
        }
        return 0;
    }
    private void xulyChonViTriSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                theLoai = dsTheLoai.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void xulyThem() {
        Intent intent = getIntent();
        int ma = Integer.parseInt(etMa.getText().toString());
        String ten = etTen.getText().toString();
        int gia = Integer.parseInt(etGia.getText().toString());
        String mota=etMota.getText().toString();
        byte[] anh=getByteArrayImage(ivHinh);
        Sach s = new Sach(ma, ten,mota,gia, anh,theLoai);
        ContentValues values = new ContentValues();
        values.put("id",s.getMaSach());
        values.put("tensach",s.getTenSach());
        values.put("mota",s.getMoTa());
        values.put("gia",s.getGia()+"");
        values.put("hinh",s.getHinh());
        values.put("maloai",s.getTheLoai().getMaLoai()+"");

        if(databaseHandler.getReadableDatabase().insert("sach",null,values)!=-1)
        {
            Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT);
            intent.putExtra("THEM", s);

        }
        databaseHandler.close();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}