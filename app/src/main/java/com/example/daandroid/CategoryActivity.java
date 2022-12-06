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

import com.example.daandroid.adapters.TheLoaiAdapter;
import com.example.daandroid.models.Sach;
import com.example.daandroid.models.TheLoai;
import com.example.daandroid.utls.DatabaseHandler;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ListView lvDanhMuc;
TheLoai theloai = null;
ArrayList<TheLoai>dstl;
ArrayAdapter<TheLoai> adapter ;
DatabaseHandler databaseHandler =new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        addControls();

        addEvents();
    }

    private void addControls() {
        lvDanhMuc = findViewById(R.id.lvDanhmuc);
        dstl = new ArrayList<>();
        try {
            Cursor cursor = databaseHandler.getReadableDatabase().rawQuery("select * From theloai",null);
            while (cursor.moveToNext()) {
                TheLoai a = new TheLoai();
                a.setMaLoai(cursor.getInt(0));
                a.setTenTheLoai(cursor.getString(1));
                dstl.add(a);
            }
        }catch (Exception ex)
        {
            Toast.makeText(this,"Loi", Toast.LENGTH_LONG);
        }
        adapter = new TheLoaiAdapter(this, R.layout.item_category, dstl);
        lvDanhMuc.setAdapter(adapter);
    }

    private void addEvents() {
        lvDanhMuc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               xuLyXoa(i);
                return true;
            }
        });
        lvDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLySua(i);
            }
        });
    }

    private void xuLySua(int i) {
        theloai=dstl.get(i);
        Intent intent= new Intent(CategoryActivity.this, AddCategoryActivity.class);
        intent.putExtra("CHON",theloai);
        resultLauncher.launch(intent);
    }

    private void xuLyXoa(int index) {
            theloai= dstl.get(index);

            if (databaseHandler.getReadableDatabase().delete("theloai", "id = ?", new String[]{theloai.getMaLoai() + ""}) != -1) {
                Toast.makeText(this, "Xóa thành công"  + theloai.getTenTheLoai(), Toast.LENGTH_SHORT);
            }
            databaseHandler.close();
           Toast.makeText(CategoryActivity.this, "Xóa thành công " + theloai.getTenTheLoai(), Toast.LENGTH_SHORT).show();

            dstl.remove(theloai);
            adapter.notifyDataSetChanged();

    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().hasExtra("THEM")) {
                            theloai = (TheLoai) result.getData().getSerializableExtra("THEM");
                            dstl.add(theloai);
                            adapter.notifyDataSetChanged();
                        } else if (result.getData().hasExtra("SUA")) {
                            TheLoai tl = (TheLoai) result.getData().getSerializableExtra("SUA");
                            theloai.setMaLoai(tl.getMaLoai());
                            theloai.setTenTheLoai(tl.getTenTheLoai());
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
                        Intent intent = new Intent(CategoryActivity.this,AddCategoryActivity.class);
                        resultLauncher.launch(intent);
                break;
            case R.id.item_About:
                Intent intent2 = new Intent(CategoryActivity.this,AboutActivity.class);
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