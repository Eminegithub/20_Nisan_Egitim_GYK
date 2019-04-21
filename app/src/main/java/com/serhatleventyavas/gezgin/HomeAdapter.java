package com.serhatleventyavas.gezgin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {

    ArrayList<CityModel> list;

    public HomeAdapter() {
        list = new ArrayList<CityModel>();
    }

    public void setList(ArrayList<CityModel> list) {
        if (this.list == null)
            this.list = new ArrayList<CityModel>();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    // Listenin boyutunu döndürüyor.
    @Override
    public int getCount() {
        return list.size();
    }

    // Listenin verilen posizyondaki veriyi döndürüyor.
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    // listenin verilen posizyondaki verinin id'sini döndürebilirsiniz
    // yada direk position döndürebilirsiniz.
    @Override
    public long getItemId(int position) {
        return position;
    }

    // View inflate edip veri parse ediyordu.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);

        ImageView imgThumb = convertView.findViewById(R.id.item_city_imgThumb);
        TextView txtName = convertView.findViewById(R.id.item_city_txtName);
        TextView txtDesc = convertView.findViewById(R.id.item_city_txtDesc);

        CityModel cityModel = list.get(position);

        txtName.setText(cityModel.getCityName());
        txtDesc.setText(cityModel.getCityDesc());
        imgThumb.setImageDrawable(convertView.getResources().getDrawable(cityModel.getDrawableId()));

        return convertView;
    }
}
