package com.example.weather;

public class Weather {
    double Temp, Pressure, Humidity, Temp_min, Temp_max, Speed, Sunrise, Sunset;

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getPressure() {
        return Pressure;
    }

    public void setPressure(double pressure) {
        Pressure = pressure;
    }

    public double getHumidity() {
        return Humidity;
    }

    public void setHumidity(double humidity) {
        Humidity = humidity;
    }

    public double getTemp_min() {
        return Temp_min;
    }

    public void setTemp_min(double temp_min) {
        Temp_min = temp_min;
    }

    public double getTemp_max() {
        return Temp_max;
    }

    public void setTemp_max(double temp_max) {
        Temp_max = temp_max;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public double getSunrise() {
        return Sunrise;
    }

    public void setSunrise(double sunrise) {
        Sunrise = sunrise;
    }

    public double getSunset() {
        return Sunset;
    }

    public void setSunset(double sunset) {
        Sunset = sunset;
    }
}