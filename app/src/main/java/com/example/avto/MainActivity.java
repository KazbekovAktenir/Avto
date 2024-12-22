package com.example.avto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_CAR_REQUEST_CODE = 1;

    private List<Car> carList;
    private CarAdapter carAdapter;
    private Spinner manufacturerSpinner;
    private Button sortButton;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получение списка автомобилей из файла JSON
        carList = CarUtils.loadCarsFromJson(this);

        if (carList != null) {
            for (Car car : carList) {
                System.out.println("Car: " + car.getManufacturer() + " " + car.getModel() + " $" + car.getPrice());
            }
        }

        // Настройка RecyclerView и адаптера
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carAdapter = new CarAdapter(carList);
        recyclerView.setAdapter(carAdapter);

        // Установка обработчика кликов
        carAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Получение выбранного автомобиля из списка
                Car selectedCar = carList.get(position);

                // Создание Intent для запуска EditCarActivity
                Intent editCarIntent = new Intent(MainActivity.this, EditCarActivity.class);
                editCarIntent.putExtra("car_to_edit", selectedCar);

                // Запуск EditCarActivity с ожиданием результата
                startActivityForResult(editCarIntent, EDIT_CAR_REQUEST_CODE);
            }
        });

        // Установка обработчика кликов для кнопки "Sort by Price"
        sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortCarsByPrice();
            }
        });

        // Установка обработчика кликов для кнопки фильтрации
        filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByManufacturer();
            }
        });

        // Настройка Spinner для выбора марки автомобиля
        manufacturerSpinner = findViewById(R.id.manufacturerSpinner);
        List<String> manufacturers = CarUtils.getManufacturersFromCars();
        // Задание списка марок в адаптер Spinner (предполагается, что вы создаете адаптер для Spinner)
        // manufacturerSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, manufacturers));
    }

    // Сортировка списка автомобилей по цене
    private void sortCarsByPrice() {
        if (carList != null) {
            Collections.sort(carList, new Comparator<Car>() {
                @Override
                public int compare(Car car1, Car car2) {
                    return Double.compare(car1.getPrice(), car2.getPrice());
                }
            });
            carAdapter.notifyDataSetChanged();
        }
    }

    // Фильтрация автомобилей по марке
    private void filterByManufacturer() {
        String selectedManufacturer = manufacturerSpinner.getSelectedItem().toString();
        List<Car> filteredCars = new ArrayList<>();

        for (Car car : carList) {
            if (car.getManufacturer().equalsIgnoreCase(selectedManufacturer)) {
                filteredCars.add(car);
            }
        }

        // Обновление списка автомобилей в адаптере
        carAdapter.updateCarList(filteredCars);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_CAR_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Car editedCar = data.getParcelableExtra("edited_car");

            // Обновление списка с отредактированным автомобилем
            if (editedCar != null) {
                int editedCarPosition = carList.indexOf(editedCar);
                carList.set(editedCarPosition, editedCar);
                carAdapter.notifyDataSetChanged();
            }
        }
    }
}
