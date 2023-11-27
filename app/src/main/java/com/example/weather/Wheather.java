package com.example.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// Ваш класс Person остается без изменений
class Wheather {

    // сама погода
    @SerializedName("weather")
    private List<com.example.weather.Wheather.Info> weatherList;
    public List<com.example.weather.Wheather.Info> getWeatherList() {
        return weatherList;
    }
    public static class Info {
        @SerializedName("description")
        private String description;
        @SerializedName("main")
        private String main;
        @SerializedName("icon")
        private String icon;
        public String getDescription() {
            return description;
        }
        public String getMain() {
            return main;
        }
        public String getIcon(){return  icon;}

    }


    // температура, влажность, давление
    @SerializedName("main")
    private com.example.weather.Wheather.mainInfo mainList;
    public com.example.weather.Wheather.mainInfo getMainList() {
        return mainList;
    }
    public static class mainInfo {
        @SerializedName("temp")
        private double temp;
        public double getTemp() {
            return temp;
        }

        @SerializedName("feels_like")
        private double feelsLike;
        public double getFeelsLike() {
            return feelsLike;
        }

        //влажность
        @SerializedName("humidity")
        private double humidity;
        public double getHumidity() {
            return humidity;
        }

        @SerializedName("grnd_level")
        private double level;
        public  double getLevel(){
            return level;
        }

    }

    // видимость
    @SerializedName("visibility")
    private double visibility;
    public double getVisibility(){
        return visibility;
    }

    //город
    @SerializedName("name")
    private String nameCity;
    public String getNameCity(){
        return nameCity;
    }


    //cкорость ветра
    @SerializedName("wind")
    private com.example.weather.Wheather.windInfo windList;
    public com.example.weather.Wheather.windInfo getWindList() {
        return windList;
    }
    public static class windInfo {
        @SerializedName("speed")
        private double speedWind;
        public double getWind() {
            return speedWind;
        }

    }

    //Облачность
    @SerializedName("clouds")
    private com.example.weather.Wheather.cloudsInfo cloudsList;
    public com.example.weather.Wheather.cloudsInfo getcloudsInfo() {
        return cloudsList;
    }
    public static class cloudsInfo {
        @SerializedName("all")
        private double all;
        public double getCloud() {
            return all;
        }

    }



    //страна, рассвет и закат.
    @SerializedName("sys")
    private com.example.weather.Wheather.getSysList sysList;
    public com.example.weather.Wheather.getSysList getSysInfo() {
        return sysList;
    }
    public static class getSysList {
        @SerializedName("country")
        private String country;
        public String getCountry() {
            return country;
        }

        @SerializedName("sunrise")
        private long sunrise;
        public long getSunrise(){ return sunrise; }

        @SerializedName("sunset")
        private long sunset;
        public long getSunset(){ return  sunset; }

    }


}