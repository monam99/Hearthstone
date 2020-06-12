package model.dataModel.hero;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.Serializable;

public class HeroPower implements Serializable {

    private String name;
    private String desc;
    private String address;
    private int manaCost;

    public void paint(Graphics g, int x, int y) {
        try {
            Image heroPower = ImageIO.read(new File(address));
            g.drawImage(heroPower, x, y, null);
        }catch (Exception e){}
    }

}
