/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BotDidiDUT;

import java.util.Random;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import java.net.URL;
import java.net.HttpURLConnection;

// JDA
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;

/**
 *
 * @author Corentin
 */
public class MyListener extends ListenerAdapter{
  private static final String BOT_PREFIX = "/24 ";
  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return;
    // We don't want to respond to other bot accounts, including ourself
    Message message = event.getMessage();
    String content = message.getContentRaw();
    MessageChannel channel = event.getChannel();
    // getContentRaw() is an atomic getter
    // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
    if(content.startsWith(BOT_PREFIX)) {
      content = content.substring(4);
      // PING
      if (content.equals("ping")) // ping
        channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
      // DICE
      else if (content.startsWith("dice")) {
        Random rnd = new Random();
        if (content.equals("dice")) { // dice
          System.out.println("Dice 1 a 6");
          int rndVal = 1+(int)(rnd.nextDouble() * 6);
          channel.sendMessage(Integer.toString(rndVal)).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        else { // dice NOMBRE
          try {
            int rndVal = 0;
            String maxVal = content.substring(5, content.length());
            System.out.println("Dice 1 a " + maxVal);
            rndVal = 1+(int)(rnd.nextDouble() * Integer.parseInt(maxVal));
            if(Integer.parseInt(maxVal) <= 0) {
                rndVal = 1+(int)(rnd.nextDouble() * 6);
            }
            channel.sendMessage(Integer.toString(rndVal)).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
          }
          catch (NumberFormatException e) {
            channel.sendMessage("The value provided is not an integer...").queue();
          }
        }
      }
      // CAT
      else if(content.startsWith("cat")) {
        if (content.equals("cat")) { // cat
          System.out.println("CAT");
          try {
            URL url = new URL("https://cataas.com/cat");
            BufferedImage image = ImageIO.read(url);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", bos);
            byte [] data = bos.toByteArray();
            channel.sendFile(data, "cat.jpg").queue();
          }
          catch(MalformedURLException e) {
            System.out.println("URL malformed...");
          }
          catch(IOException e) {
            System.out.println("IO exception thrown");
          }
        }
        else {
          String tag = "";
          String says = "";
          content = content.substring(4);
          System.out.println("Content : "+ content);
          if(!content. startsWith("says")) {
            if(content.indexOf(" ") == -1) {
              tag = content;
            }
            else {
              tag = content.substring(0, content.indexOf(" "));
              content = content.substring(content.indexOf(" ") + 1);
            }
            if(tag == "s") tag = "";
          }
          if(content.startsWith("says")) { // cat says
            content = content.substring(5);
            says = content;
          }
          if(tag != "") {
            if(says != "") { // cat TAG says SAYS
              System.out.println("CAT TAG SAYS");
              try {
                URL url = new URL("https://cataas.com/cat/"+tag+"/says/"+says);
                BufferedImage image = ImageIO.read(url);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", bos);
                byte [] data = bos.toByteArray();
                channel.sendFile(data, "cat.jpg").queue();
              }
              catch(MalformedURLException e) {
                System.out.println("URL malformed...");
              }
              catch(IOException e) {
                System.out.println("IO exception thrown");
              }
            }
            else { // cat TAG
              System.out.println("CAT TAG");
              try {
                URL url = new URL("https://cataas.com/cat/"+tag);
                BufferedImage image = ImageIO.read(url);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", bos);
                byte [] data = bos.toByteArray();
                channel.sendFile(data, "cat.jpg").queue();
              }
              catch(MalformedURLException e) {
                System.out.println("URL malformed...");
              }
              catch(IOException e) {
                System.out.println("IO exception thrown");
              }
            }
          }
          else if (says != "") { // cat says SAYS
            System.out.println("CAT SAYS");
            try {
              URL url = new URL("https://cataas.com/cat/says/"+says);
              BufferedImage image = ImageIO.read(url);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
              ImageIO.write(image, "jpg", bos);
              byte [] data = bos.toByteArray();
              channel.sendFile(data, "cat.jpg").queue();
            }
            catch(MalformedURLException e) {
              System.out.println("URL malformed...");
            }
            catch(IOException e) {
              System.out.println("IO exception thrown");
            }
          }
          else {
            System.out.println("CAT");
            try {
              URL url = new URL("https://cataas.com/cat");
              BufferedImage image = ImageIO.read(url);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
              ImageIO.write(image, "jpg", bos);
              byte [] data = bos.toByteArray();
              channel.sendFile(data, "cat.jpg").queue();
            }
            catch(MalformedURLException e) {
              System.out.println("URL malformed...");
            }
            catch(IOException e) {
              System.out.println("IO exception thrown");
            }
          }
        }
      }
      // WEATHER
      else if(content.startsWith("weather")){
        String city = "Calais";
        if(!content.equals("weather")) { // weather CITY
          city = content.substring(8);
        }
        Weather weather = new Weather(city, getWeather(city));
        channel.sendMessage(weather.getEmbed().build()).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
      }

    }
  }

  static String getWeather(String city) {
    String result = "";
    try {
      // création de l'URL
      String APIkey = "1d462aee79b4f06b92b77fc8f6431b88";
      String serv = "http://api.openweathermap.org/data/2.5/weather";
      String param = "q=Calais,fr&units=metric&appid=";
      if(city != "") param = "q=" + city + "&units=metric&appid=";
      System.out.println("Url : " + serv + "?" + param + APIkey);
      URL url = new URL(serv + "?" + param + APIkey);

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      if(conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      result = br.lines().collect(Collectors.joining());
      conn.disconnect();
    }
    catch(MalformedURLException e) {
      e.printStackTrace();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}
