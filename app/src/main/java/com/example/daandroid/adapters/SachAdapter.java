package com.example.daandroid.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.daandroid.R;
import com.example.daandroid.models.Sach;

import java.util.List;

public class SachAdapter  extends ArrayAdapter<Sach> {
    Activity context;
    int resource;
    List<Sach> object;
    public SachAdapter(@NonNull Activity context, int resource, @NonNull List<Sach> objects) {
        super(context, resource, objects);
        this.context=context;
        this.object=objects;
        this.resource=resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item;
        LayoutInflater inflater=this.context.getLayoutInflater();
        item=inflater.inflate(R.layout.item_book,null);
        TextView tvTenSach=item.findViewById(R.id.tvTieuDeSach);
        TextView tvGia=item.findViewById(R.id.tvGia);
        ImageView ivHinh = item.findViewById(R.id.ivHinh);
        Sach sach= this.object.get(position);
        tvTenSach.setText(sach.getTenSach());
        tvGia.setText(sach.getGia()+"");
        Bitmap hinhanh= BitmapFactory.decodeByteArray(sach.getHinh(),0,sach.getHinh().length);
        ivHinh.setImageBitmap(hinhanh);
        return item;
    }
}
