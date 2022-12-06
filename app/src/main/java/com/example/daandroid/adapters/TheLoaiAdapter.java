package com.example.daandroid.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.daandroid.R;
import com.example.daandroid.models.TheLoai;

import java.util.List;

public class TheLoaiAdapter extends ArrayAdapter<TheLoai> {
    Activity context;
    int resource;
    List<TheLoai> object;
    public TheLoaiAdapter(@NonNull Activity context, int resource, @NonNull List<TheLoai> objects) {
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
        item=inflater.inflate(R.layout.item_category,null);
        TextView tvMaLoai=item.findViewById(R.id.tvMaLoai);
        TextView tvTenLoai=item.findViewById(R.id.tvTenLoai);
        TheLoai theLoai= this.object.get(position);
        tvMaLoai.setText(theLoai.getMaLoai()+"");
        tvTenLoai.setText(theLoai.getTenTheLoai());
        return item;
    }
}
