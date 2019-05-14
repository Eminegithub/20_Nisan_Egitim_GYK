package com.serhatleventyavas.gezgin;

import java.util.ArrayList;

public class CityModel {

    private String cityName;
    private String cityDesc;
    private String imageUrl;

    public CityModel(String cityName, String cityDesc, String imageUrl) {
        this.cityName = cityName;
        this.cityDesc = cityDesc;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
