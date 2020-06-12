package view.graphicUtils;

import utils.Config;

public interface BtnCorLoader {
    String[] point = {"_START_X","_START_Y","_FINAL_X","_FINAL_Y"};

    default void setCor(Config properties,String btn,int[] cor){
        for (int i = 0; i < 4; i++)
            cor[i] = properties.getNumber(btn + point[i]);
    }
}
