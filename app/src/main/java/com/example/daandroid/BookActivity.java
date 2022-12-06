package com.example.daandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daandroid.adapters.SachAdapter;
import com.example.daandroid.adapters.TheLoaiAdapter;
import com.example.daandroid.models.Sach;
import com.example.daandroid.models.TheLoai;
import com.example.daandroid.utls.DatabaseHandler;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    ListView lvSach;
    Sach sach = null;
    ArrayList<Sach> dssach;
    ArrayList<TheLoai> dstl;
    ArrayAdapter<Sach> adapter ;
    DatabaseHandler databaseHandler =new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        addControls();
        addEvents();
    }

    private void addControls() {
        lvSach = findViewById(R.id.lvSach);
        dssach = new ArrayList<>();
        dstl = new ArrayList<>();
        try {
            Cursor cursor1 = databaseHandler.getReadableDatabase().rawQuery("select * From theloai",null);
            while (cursor1.moveToNext()) {
                TheLoai a = new TheLoai();
                a.setMaLoai(cursor1.getInt(0));
                a.setTenTheLoai(cursor1.getString(1));
                dstl.add(a);
            }
            Cursor cursor = databaseHandler.getReadableDatabase().rawQuery("select * From sach",null);
            while (cursor.moveToNext()) {
                Sach a = new Sach();
                a.setMaSach(cursor.getInt(0));
                a.setTenSach(cursor.getString(1));
                a.setMoTa(cursor.getString(2));
                a.setGia(cursor.getInt(3));
                a.setHinh(cursor.getBlob(4));
                TheLoai theLoai =timTheLoai(dstl,cursor.getInt(5));
                a.setTheLoai(theLoai);
                dssach.add(a);
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage(String.valueOf(a.getTenSach()));
            }

        }catch (Exception ex)
        {
            Toast.makeText(this,"Lỗi", Toast.LENGTH_LONG);
        }
        adapter = new SachAdapter(this, R.layout.item_book, dssach);
        lvSach.setAdapter(adapter);
    }

    private void addEvents() {
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyXoa(i);
                return true;
            }
        });
        lvSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLySua(i);
            }
        });

    }
    private TheLoai timTheLoai(ArrayList<TheLoai> dstl, int maTheLoai) {
        TheLoai a = new TheLoai();
        for (TheLoai tl: dstl) {
            if (tl.getMaLoai() == maTheLoai){
                return tl;
            }
        }
        return a;
    }


    private void xuLyXoa(int index) {
        sach= dssach.get(index);

        if (databaseHandler.getReadableDatabase().delete("sach", "id = ?", new String[]{sach.getMaSach() + ""}) != -1) {
            Toast.makeText(this, "Xóa thành công"  + sach.getTenSach(), Toast.LENGTH_SHORT);
        }
        databaseHandler.close();
        Toast.makeText(BookActivity.this, "Xóa thành công " + sach.getTenSach(), Toast.LENGTH_SHORT).show();

        dssach.remove(sach);
        adapter.notifyDataSetChanged();
    }

    private void xuLySua(int i) {
        sach=dssach.get(i);
        Intent intent = new Intent(BookActivity.this, AddBookActivity.class);
        intent.putExtra("CHON", sach);
        resultLauncher.launch(intent);

    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().hasExtra("THEM")) {
                            sach = (Sach) result.getData().getSerializableExtra("THEM");
                            dssach.add(sach);
                            adapter.notifyDataSetChanged();
                        } else if (result.getData().hasExtra("SUA")) {
                            Sach tl = (Sach) result.getData().getSerializableExtra("SUA");
                            sach.setMaSach(tl.getMaSach());
                            sach.setTenSach(tl.getTenSach());
                            sach.setMoTa(tl.getMoTa());
                            sach.setGia(tl.getGia());
                            sach.setHinh(tl.getHinh());
                            sach.setTheLoai(tl.getTheLoai());
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            });
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_themDM:
                Intent intent = new Intent(BookActivity.this,AddBookActivity.class);
                resultLauncher.launch(intent);
                break;
            case R.id.item_About:
                Intent intent2 = new Intent(BookActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.item_Thoat:
                //Khoi tao lai Activity main
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);

                // Tao su kien ket thuc app
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}