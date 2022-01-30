package Server.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private Properties properties;
    public ConfigurationLoader(){
        properties = new Properties();
    }
    public int getLobbyTimer() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        inputStream.close();
        return Integer.parseInt(properties.getProperty("LobbyWaitingTimer"));
    }
    public int getTurnTimer() throws IOException{
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        inputStream.close();
        return Integer.parseInt(properties.getProperty("TurnWaitingTimer"));
    }
}
