package com.example.avto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddCarActivity extends AppCompatActivity {

    private EditText editTextManufacturer;
    private EditText editTextModel;
    private EditText editTextPrice;
    private EditText editTextYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);


        editTextManufacturer = findViewById(R.id.editTextManufacturer);
        editTextModel = findViewById(R.id.editTextModel);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextYear = findViewById(R.id.editTextYear);


        Button buttonAddCar = findViewById(R.id.buttonAddCar);
        buttonAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCarButtonClick();
            }
        });
    }

    private void onAddCarButtonClick() {
        String manufacturer = editTextManufacturer.getText().toString();
        String model = editTextModel.getText().toString();
        String priceString = editTextPrice.getText().toString();
        String yearString = editTextYear.getText().toString();

        if (!manufacturer.isEmpty() && !model.isEmpty() && !priceString.isEmpty() && !yearString.isEmpty()) {
            double price = Double.parseDouble(priceString);
            int year = Integer.parseInt(yearString);

            //создание нового автомобиля
            Car newCar = new Car(manufacturer, model, price, year);

            //добавление нового автомобиля в список
            CarUtils.addCarToList(newCar);

            //закрытие активности после добавления автомобиля
            finish();
        }
    }
}
