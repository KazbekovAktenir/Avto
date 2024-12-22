package com.example.avto;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {

    private String manufacturer;
    private String model;
    private double price;
    private int year;
    private String imageUrl;

    public Car(String manufacturer, String model, double price, int year) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }
    public int getYear() {
        return year;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    //реализация Parcelable

    protected Car(Parcel in) {
        manufacturer = in.readString();
        model = in.readString();
        price = in.readDouble();
        year = in.readInt();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(manufacturer);
        dest.writeString(model);
        dest.writeDouble(price);
        dest.writeInt(year);
    }
}
