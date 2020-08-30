package com.example.rxjava2example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.rxjava2example.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import io.reactivex.disposables.Disposable;

import com.example.rxjava2example.TemperatureManager.Temperature;

public class MainActivity extends AppCompatActivity {

    private final TemperatureManager manager = new TemperatureManager();
    private ActivityMainBinding binding;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.currentTemperatureView.setText("받아온 온도가 없습니다.");

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            //Create Fake data.
            int nextTemperature = (new Random()).nextInt(15) + 10;

            manager.setTemperature(new Temperature(nextTemperature));
        }, 0L, 3, TimeUnit.SECONDS);
        disposable = manager.updateEvent().subscribe(this::updateView);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        disposable.dispose();;
    }

    @SuppressLint("DefaultLocale")
    private void updateView(Temperature temperature){
        binding.currentTemperatureView.setText(
                String.format("현재 온도는 %d도 입니다.", temperature.getDegree()));
    }
}

