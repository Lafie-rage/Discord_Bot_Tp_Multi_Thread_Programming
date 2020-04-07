/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BotDidiDUT;

// JDK
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Color;
// JSON simple
import org.json.simple.*;
import org.json.simple.parser.*;
// JDA
import net.dv8tion.jda.api.EmbedBuilder;

public class Weather {
  private String city;
  private double temperature;
  private double feel;
  private double windSpeed;
  private long humidity;

  public Weather(String city, String jsonStr) {
    this.city = city;
    JSONParser parser = new JSONParser();
    try {
      JSONObject jsonObj = (JSONObject)parser.parse(jsonStr);
      JSONObject jsonMainWeather = (JSONObject)jsonObj.get("main");
      this.temperature = (double)jsonMainWeather.get("temp");
      this.feel = (double)jsonMainWeather.get("feels_like");
      this.humidity = (long)jsonMainWeather.get("humidity");
      JSONObject jsonWindWeather = (JSONObject)jsonObj.get("wind");
      this.windSpeed = (double)jsonWindWeather.get("speed");
    }
    catch(ParseException e) {
      e.printStackTrace();
    }
  }

  public EmbedBuilder getEmbed() {
    EmbedBuilder eb = new EmbedBuilder();
    eb.setTitle("Weather of " + city, null);
    eb.setColor(Color.red);
    eb.setAuthor("Lafie-rage", "https://github.com/Lafie-rage/");
    // Date
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    String dateStr = formatter.format(date);
    eb.addField("Date", dateStr, false);
    // Temperature
    eb.addField("Temperature", temperature+"°C", false);
    // Felt temperature
    eb.addField("Felt temperature", feel+"°C", false);
    // Wind speed
    eb.addField("Wind speed", windSpeed+"m/s", false);
    // Humidity
    eb.addField("Humidity percentage", humidity+"%", false);
    return eb;
  }
}
