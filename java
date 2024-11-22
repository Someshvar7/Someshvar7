
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import org.json.JSONObject;

public class WeatherForecastingSystem {
    private static final String API_KEY = "your_api_key"; // Replace with your actual API key
    private static final String BASE_URL = "http://api.weatherstack.com/current";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Weather Forecasting System");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel locationLabel = new JLabel("Location: ");
            JLabel tempLabel = new JLabel("Temperature: ");
            JLabel humidityLabel = new JLabel("Humidity: ");
            JLabel windSpeedLabel = new JLabel("Wind Speed: ");

            JPanel panel = new JPanel(new GridLayout(4, 1));
            panel.add(locationLabel);
            panel.add(tempLabel);
            panel.add(humidityLabel);
            panel.add(windSpeedLabel);

            frame.add(panel);
            frame.setVisible(true);

            String location = "Manachanallur"; // Change this to your desired location
            String urlString = BASE_URL + "?access_key=" + API_KEY + "&query=" + location;

            new Thread(() -> {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject obj = new JSONObject(response.toString());
                    JSONObject current = obj.getJSONObject("current");

                    String temperature = current.getString("temperature");
                    String humidity = current.getString("humidity");
                    String windSpeed = current.getString("wind_speed");

                    SwingUtilities.invokeLater(() -> {
                        locationLabel.setText("Location: " + location);
                        tempLabel.setText("Temperature: " + temperature + "°C");
                        humidityLabel.setText("Humidity: " + humidity + "%");
                        windSpeedLabel.setText("Wind Speed: " + windSpeed + " km/h");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}
