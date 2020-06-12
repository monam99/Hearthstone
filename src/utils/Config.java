package utils;


import java.util.Properties;

public class Config extends Properties {
    public int getNumber(String key){
        return Integer.parseInt(getProperty(key));
    }

    public boolean getBoolean(String key){
        return Boolean.parseBoolean(getProperty(key));
    }

}
