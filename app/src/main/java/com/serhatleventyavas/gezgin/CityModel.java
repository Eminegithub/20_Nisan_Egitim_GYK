package com.serhatleventyavas.gezgin;

import java.util.ArrayList;

public class CityModel {

    private int id;
    private String cityName;
    private String cityDesc;
    private int drawableId;

    public CityModel(int id, String cityName, String cityDesc, int drawableId) {
        this.id = id;
        this.cityName = cityName;
        this.cityDesc = cityDesc;
        this.drawableId = drawableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityDesc() {
        return cityDesc;
    }

    public void setCityDesc(String cityDesc) {
        this.cityDesc = cityDesc;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public static ArrayList<CityModel> mockData() {
        ArrayList<CityModel> list = new ArrayList<>();
        list.add(new CityModel(
                1,
                "Kayseri",
                "Kayseri mantısıdır, sucuğudur felan...",
                R.drawable.foto1));

        list.add(new CityModel(
                2,
                "Mardin",
                "Güzel şehirdir",
                R.drawable.foto2));

        list.add(new CityModel(
                3,
                "İstanbul",
                "Seçimlerin 17 gün sürdüğü tarihe geçen tek şehirdir",
                R.drawable.foto3));
        list.add(new CityModel(
                4,
                "Ankara",
                "Türkiye Cumhuriyeti'nin başkenti olan şehir angaralının bebeleri ile meşhurdur",
                R.drawable.foto4));
        return list;
    }
}
