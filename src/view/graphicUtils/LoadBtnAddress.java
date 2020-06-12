package view.graphicUtils;

import utils.Config;

public interface LoadBtnAddress {
    default String[] getImageAddresses(String name , Config properties){
        String[] res = new String[2];
        for (int i = 0; i < 2; i++)
            res[i] = properties.getProperty(name + i + "ImageAddress");
        return res;
    }
}
