import java.util.Random;
/**
 * Write a description of class Weather here.
 *
 * @authors Haidar Alawie, Mansour Jalaly
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
    private int x;
    private static final Random rand = Randomizer.getRandom();
    private String weather; 
    public static final String [] weatherList= {"Fog", "Rain", "Sunny", "Snow"};
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        // initialise instance variables
        weather = randomWeather();
    }
    
    /**
     * 
     */
    public String getWeather(){
     return weather;   
    }
    
    /**
     * 
     */
    public String randomWeather(){
        int weatherIndex = rand.nextInt(weatherList.length);
        String randomWeather = weatherList[weatherIndex];
        return randomWeather;
    }
}
