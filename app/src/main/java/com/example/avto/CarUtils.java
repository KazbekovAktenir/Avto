package com.example.avto;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarUtils {

    private static List<Car> carList;

    public static List<Car> loadCarsFromJson(Context context) {
        List<Car> carList = null;
        InputStream inputStream = null;

        try {
            // Получение InputStream из файла cars.json в ресурсах
            inputStream = context.getResources().openRawResource(R.raw.cars);

            // Чтение данных из InputStream и преобразование в строку
            String json = readJsonFromStream(inputStream);

            // Использование Gson для преобразования JSON в список объектов Car
            Type carListType = new TypeToken<List<Car>>() {}.getType();
            carList = new Gson().fromJson(json, carListType);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Закрытие InputStream после использования
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return carList;
    }

    private static String readJsonFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            stringBuilder.append(new String(buffer, 0, bytesRead));
        }

        return stringBuilder.toString();
    }

    public static void addCarToList(Car newCar) {
        if (carList == null) {
            carList = new ArrayList<>();
        }
        carList.add(newCar);
    }

    public static List<String> getModelsByManufacturer(String manufacturer) {
        List<String> models = new ArrayList<>();
        if (carList != null) {
            for (Car car : carList) {
                if (car.getManufacturer().equalsIgnoreCase(manufacturer)) {
                    models.add(car.getModel());
                }
            }
        }
        return models;
    }

    public static List<String> getManufacturersFromCars() {
        List<String> manufacturers = new ArrayList<>();
        if (carList != null) {
            for (Car car : carList) {
                manufacturers.add(car.getManufacturer());
            }
        }
        return manufacturers;
    }
}
