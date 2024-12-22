package com.example.avto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditCarActivity extends AppCompatActivity {

    private EditText editManufacturer;
    private EditText editModel;
    private EditText editYear;
    private EditText editPrice;

    private Button saveButton;

    private Car carToEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        //инициализация полей для редактирования
        editManufacturer = findViewById(R.id.editManufacturer);
        editModel = findViewById(R.id.editModel);
        editPrice = findViewById(R.id.editPrice);
        editYear = findViewById(R.id.editYear);
        saveButton = findViewById(R.id.saveButton);

        //получение данных автомобиля для редактирования из Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("car_to_edit")) {
            carToEdit = intent.getParcelableExtra("car_to_edit");
            if (carToEdit != null) {
                //установка данных в поля для редактирования
                editManufacturer.setText(carToEdit.getManufacturer());
                editModel.setText(carToEdit.getModel());
                editPrice.setText(String.valueOf(carToEdit.getPrice()));
                editYear.setText(String.valueOf(carToEdit.getYear()));
            }
        }

        //обработка нажатия кнопки сохранения
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEditedCar();
            }
        });
    }

    private void saveEditedCar() {
        //получение отредактированных данных
        String editedManufacturer = editManufacturer.getText().toString();
        String editedModel = editModel.getText().toString();
        double editedPrice = Double.parseDouble(editPrice.getText().toString()); // Предполагается, что цена является числом
        int editedYear = Integer.parseInt(editYear.getText().toString());

        //создание объекта с отредактированными данными
        Car editedCar = new Car(editedManufacturer, editedModel, editedPrice, editedYear);

        //создание Intent для отправки отредактированных данных обратно в MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("edited_car", editedCar);
        setResult(RESULT_OK, resultIntent);

        //завершение активности
        finish();
    }
}
