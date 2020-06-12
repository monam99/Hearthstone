package utils;

import java.io.File;
import java.io.FileReader;

public interface PanelLoader {
    default void loadProperties(Config properties , String panel) {
        File f = new File(ConfigLoader.getLoader().getPanelConfigsAddresses().getProperty(panel));
        try {
            FileReader fr = new FileReader(f);
            properties.load(fr);
        } catch (Exception e) {
        }
    }
}
