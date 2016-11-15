package io.github.manankalra.retroweathertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.manankalra.retroweathertest.data.model.Query;
import io.github.manankalra.retroweathertest.data.model.Weather;
import io.github.manankalra.retroweathertest.remote.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.textView_city)
    TextView textView_city;
    @BindView(R.id.textView_temperature)
    TextView textView_temperature;
    @BindView(R.id.textView_lastupdated)
    TextView textView_lastupdated;
    @BindView(R.id.textView_conditions)
    TextView textView_conditions;
    @BindView(R.id.button_refresh)
    Button button_refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        doStuff();
    }

    public void doStuff() {
        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Query query = response.body().getQuery();
                textView_temperature.setText(
                        query.getResults().getChannel().getItem().getCondition().getTemp() + " °C");
                textView_city.setText(query.getResults().getChannel().getLocation().getCity());
                textView_conditions.setText(
                        query.getResults().getChannel().getItem().getCondition().getText());
                textView_lastupdated.setText(query.getResults().getChannel().getLastBuildDate());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Failure", t.getMessage());
            }
        });
    }

    @OnClick(R.id.button_refresh)
    public void refresh() {
        doStuff();
    }
}
