package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class ConfigLoader {
    private static ConfigLoader loader;
    private String resource = "resources/addresses.properties";
    private Properties addresses;
    private Config frameConfig;
    private Config panelConfigsAddresses;


    private ConfigLoader() {
        addresses = new Properties();
        panelConfigsAddresses = new Config();
        frameConfig = new Config();
        try {
            addresses.load(new FileReader(new File(resource)));
        } catch (IOException e) {}
        loadProperties();
    }

    private void loadProperties() {
        Set<Entry<Object, Object>> entries = addresses.entrySet();
        for (Entry entry : entries) {
            String key = (String) entry.getKey();
            String address = (String) entry.getValue();
            Config config = new Config();
            try {
                FileReader fr = new FileReader(address);
                config.load(fr);
            } catch (Exception e) {}
            if (key.toLowerCase().equals("panel_configs"))
                panelConfigsAddresses = config;
            if (key.toLowerCase().equals("frame_config"))
                frameConfig = config;
        }
    }

    public static ConfigLoader getLoader() {
        if (loader == null) {
            loader = new ConfigLoader();
        }
        return loader;
    }

    public Config getPanelConfigsAddresses() {
        return panelConfigsAddresses;
    }

    public Config getFrameConfig() {
        return frameConfig;
    }
}
