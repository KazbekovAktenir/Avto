package com.example.avto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private OnItemClickListener onItemClickListener; // интерфейс для обработки кликов

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // метод для установки слушателя
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    // Добавляем метод updateCarList
    public void updateCarList(List<Car> newCarList) {
        this.carList = newCarList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car currentCar = carList.get(position);

        // заполняем данные в элементах интерфейса
        holder.textManufacturer.setText(currentCar.getManufacturer());
        holder.textModel.setText(currentCar.getModel());
        holder.textPrice.setText(String.valueOf(currentCar.getPrice()));
        holder.textYear.setText(String.valueOf(currentCar.getYear()));

        //загрузка изображения с помощью Glide (если у вас есть URL)
        Glide.with(holder.itemView.getContext())
                .load(currentCar.getImageUrl()) // URL изображения
                .placeholder(R.drawable.placeholder) //заглушка, если изображение не загрузилось
                .into(holder.imageView); //вставка изображения в ImageView
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    // вложенный класс для представления элементов списка
    public class CarViewHolder extends RecyclerView.ViewHolder {

        public TextView textManufacturer;
        public TextView textModel;
        public TextView textPrice;
        public TextView textYear;
        public ImageView imageView; // Добавляем ImageView для картинки

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            textManufacturer = itemView.findViewById(R.id.textManufacturer);
            textModel = itemView.findViewById(R.id.textModel);
            textPrice = itemView.findViewById(R.id.textPrice);
            textYear = itemView.findViewById(R.id.textYear);
            imageView = itemView.findViewById(R.id.imageView); // Инициализируем ImageView

            // устанавливаем обработчик кликов для элемента списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
