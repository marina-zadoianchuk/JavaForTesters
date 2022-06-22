package lesson7;

import com.fasterxml.jackson.databind.ObjectMapper;
import lesson7.entity.Weather;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class AccuweatherModel implements WeatherModel {
    //http://dataservice.accuweather.com/forecasts/v1/daily/1day/349727
    private static final String PROTOKOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String FIVE_DAYS = "5day";

    // Мой API_KEY = "A17O4On71vceDLGIkJmh74y3otL2FgAy";
    //При отладке программы закончилось кол-во попыток для его использвания
    private static final String API_KEY = "pXJd8MokcZCdrd2MsoGl2DBZAyCa0zvv";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String METRIC_QUERY_PARAM = "metric";
    private static final String METRIC = "true";
    private static final String LANG_QUERY_PARAM = "language";
    private static final String LANG = "ru";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private DataBaseRepository dataBaseRepository = new DataBaseRepository();

    public void getWeather(String selectedCity, Period period) throws IOException {
        switch (period) {
            case NOW:
                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(ONE_DAY)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(METRIC_QUERY_PARAM, METRIC)
                        //  .addQueryParameter(LANG_QUERY_PARAM, LANG)
                        .build();

                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response oneDayForecastResponse = okHttpClient.newCall(request).execute();
                String weatherResponseOneDay = oneDayForecastResponse.body().string();
                //System.out.println(weatherResponseOneDay);

                //TODO: сделать человекочитаемый вывод погоды. Выбрать параметры для вывода на свое усмотрение
                //Например: Погода в городе Москва - 5 градусов по цельсию Expect showers late Monday night
                //dataBaseRepository.saveWeatherToDataBase(new Weather()) - тут после парсинга добавляем данные в БД

                String city = selectedCity;
                String date = objectMapper.readTree(weatherResponseOneDay).at("/DailyForecasts").get(0).at("/Date").asText();
                String dateShort = date.substring(0, date.length() - 15);
                String minTemperature = objectMapper.readTree(weatherResponseOneDay).at("/DailyForecasts").get(0).at("/Temperature/Minimum/Value").asText();
                String maxTemperature = objectMapper.readTree(weatherResponseOneDay).at("/DailyForecasts").get(0).at("/Temperature/Maximum/Value").asText();
                String unit = objectMapper.readTree(weatherResponseOneDay).at("/DailyForecasts").get(0).at("/Temperature/Minimum/Unit").asText();
                System.out.println("Погода в городе " + city + " на " + dateShort + " от " + minTemperature + " до " + maxTemperature + " градусов " + unit);


               break;
            case FIVE_DAYS:
                //TODO*: реализовать вывод погоды на 5 дней
                HttpUrl httpUrlFiveDays = new HttpUrl.Builder()
                        .scheme(PROTOKOL)
                        .host(BASE_HOST)
                        .addPathSegment(FORECASTS)
                        .addPathSegment(VERSION)
                        .addPathSegment(DAILY)
                        .addPathSegment(FIVE_DAYS)
                        .addPathSegment(detectCityKey(selectedCity))
                        .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                        .addQueryParameter(METRIC_QUERY_PARAM, METRIC)
                        //  .addQueryParameter(LANG_QUERY_PARAM, LANG)
                        .build();

                Request requestFiveDays = new Request.Builder()
                        .url(httpUrlFiveDays)
                        .build();

                Response FiveDaysForecastResponse = okHttpClient.newCall(requestFiveDays).execute();
                String weatherResponseFiveDays = FiveDaysForecastResponse.body().string();
                //   System.out.println(weatherResponseFiveDays);

                for (int i = 0; i < 5; i++) {
                    String city5 = selectedCity;
                    String date5 = objectMapper.readTree(weatherResponseFiveDays).at("/DailyForecasts").get(i).at("/Date").asText();
                    String dateShort5 = date5.substring(0, date5.length() - 15);
                    String minTemperature5 = objectMapper.readTree(weatherResponseFiveDays).at("/DailyForecasts").get(i).at("/Temperature/Minimum/Value").asText();
                    String maxTemperature5 = objectMapper.readTree(weatherResponseFiveDays).at("/DailyForecasts").get(i).at("/Temperature/Maximum/Value").asText();
                    String unit5 = objectMapper.readTree(weatherResponseFiveDays).at("/DailyForecasts").get(i).at("/Temperature/Minimum/Unit").asText();
                    System.out.println("Погода в городе " + city5 + " на " + dateShort5 + " от " + minTemperature5 + " до " + maxTemperature5 + " градусов " + unit5);
                }
                    break;
        }
    }

    @Override
    public List<Weather> getSavedToDBWeather() {
        return dataBaseRepository.getSavedToDBWeather();
    }

    private String detectCityKey(String selectCity) throws IOException {
        //http://dataservice.accuweather.com/locations/v1/cities/autocomplete
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOKOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", selectCity)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseString = response.body().string();

        String cityKey = objectMapper.readTree(responseString).get(0).at("/Key").asText();
        return cityKey;
    }
}