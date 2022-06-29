package lesson7;

import lesson7.entity.Weather;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sqlite.*;

public class DataBaseRepository {
    private static final String DB_PATH = "jdbc:sqlite:geekbrains.db";
    private String insertWeather = "insert into weather (city, dateShort, minTemperature, maxTemperature) values (?, ?, ?, ?)";
    private String getWeather = "select * from weather";


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean saveWeatherToDataBase(Weather weather) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            saveWeather.setString(1, weather.getCity());
            saveWeather.setString(2, weather.getDateShort());
            saveWeather.setString(3, weather.getMinTemperature());
            saveWeather.setString(4, weather.getMaxTemperature());
            return saveWeather.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new SQLException("Сохранение погоды в базу данных не выполнено!");
    }

    public void saveWeatherToDataBase(List<Weather> weatherList) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            for (Weather weather : weatherList) {
                saveWeather.setString(1, weather.getCity());
                saveWeather.setString(2, weather.getDateShort());
                saveWeather.setString(3, weather.getMinTemperature());
                saveWeather.setString(4, weather.getMaxTemperature());
                saveWeather.addBatch();
            }
            saveWeather.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //public List<Weather> getSavedToDBWeather() {
    //    try (Connection connection = DriverManager.getConnection(DB_PATH)) {
    //        //TODO: реализовать этот метод получения данных из таблицы погоды
    //    } catch (SQLException throwables) {
    //        throwables.printStackTrace();
    //    }
    //}

    public List<Weather> getSavedToDBWeather() {
        List<Weather> weathers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getWeather);
            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id"));
                System.out.println(" ");
                System.out.print(resultSet.getString("city"));
                System.out.println(" ");
                System.out.print(resultSet.getString("dateShort"));
                System.out.println(" ");
                System.out.print(resultSet.getString("minTemperature"));
                System.out.println(" ");
                System.out.print(resultSet.getString("maxTemperature"));
                System.out.println(" ");
                weathers.add(new Weather(resultSet.getString("city"),
                        resultSet.getString("dateShort"),
                        resultSet.getString("minTemperature"),
                        resultSet.getString("maxTemperature")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return weathers;
    }

    public static void main(String[] args) throws SQLException {
        DataBaseRepository dataBaseRepository = new DataBaseRepository();
        System.out.println(dataBaseRepository.getSavedToDBWeather());
    }
}
