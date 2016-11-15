package io.github.manankalra.retroweathertest.remote;


import io.github.manankalra.retroweathertest.data.model.Weather;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface WeatherAPI {

    String BASE_URL = "https://query.yahooapis.com/v1/public/";

    @GET("yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20"
            + "(select%20woeid%20from%20geo.places(1)%20where%20text%3D\"dehradun%2C%20IN\")"
            + "%20and%20u%20%3D%20%27c%27&format=json&env=store%3A%2F%2Fdatatables"
            + ".org%2Falltableswithkeys")
    Call<Weather> getWeather();

    //verifies if any instance if this class is already present
    class Factory {
        private static WeatherAPI service;

        public static WeatherAPI getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();
                service = retrofit.create(WeatherAPI.class);
                return service;
            } else {
                return service;
            }
        }
    }


}
