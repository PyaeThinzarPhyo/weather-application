package com.ptzp.weratherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ptzp.weratherapplication.remote.dto.ResponseWeather;
import com.ptzp.weratherapplication.remote.retrofit.APIService;
import com.ptzp.weratherapplication.remote.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView txtWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtWeatherData=findViewById(R.id.txtWeatherData);

        APIService service = RetrofitClientInstance.getRetrofitInstance().create(APIService.class);
        Call<ResponseWeather> call = service.getCurrentWeatherData("36af97e363msh144608437c10a03p1b13bejsn9a1f73745501",
                "metric \" or \"imperial",
                 "xml,html",
                  "Dawei,MM");
        call.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(@NonNull Call<ResponseWeather> call, @NonNull Response<ResponseWeather> response) {

                if (response.isSuccessful()) {
                    ResponseWeather responseWeather = response.body();
                    assert responseWeather != null;

                    Log.d("MainActivity", "weather main:" + responseWeather.weatherList.get(0).main);
                    Log.d("MainActivity", "weather description:" + responseWeather.weatherList.get(0).description);
                    Log.d("MainActivity", "weather temp:" + responseWeather.main.temp);
                    Log.d("MainActivity", "weather temp min:" + responseWeather.main.tempMin);
                    Log.d("MainActivity", "weather temp max:" + responseWeather.main.tempMax);

                    txtWeatherData.setText("weather main:" + responseWeather.weatherList.get(0).main + "\n"
                            + "weather description:" + responseWeather.weatherList.get(0).description + "\n"
                            + "weather temp:" + responseWeather.main.temp + "\n"
                            + "weather temp min:" + responseWeather.main.tempMin + "\n"
                            + "weather temp max:" + responseWeather.main.tempMax + "\n");

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWeather> call, @NonNull Throwable t) {
                Log.d("MainActivity", String.format("Error: %s", t.getMessage()));
            }

        });

    }



}
