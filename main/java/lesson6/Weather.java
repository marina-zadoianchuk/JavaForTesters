package lesson6;
//
import okhttp3.*;
import java.io.IOException;

public class Weather {


    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String apiKeyQueryParam = "apikey";
        String metricQueryParam = "metric";
        String apiKey = "A17O4On71vceDLGIkJmh74y3otL2FgAy";
        String metric = "true";
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment("294021")
                .addQueryParameter(apiKeyQueryParam, apiKey)
                .addQueryParameter(metricQueryParam, metric)
                .build();

        Request requestCreateWeather = new Request.Builder()
                .url(httpUrl)
                .build();

        Response createWeatherResponse = okHttpClient.newCall(requestCreateWeather).execute();

        System.out.println(createWeatherResponse.body().string());
    }
}
