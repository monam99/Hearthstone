package model.dataModel.hero;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.Serializable;

public abstract class Hero implements Serializable {

    private HeroPower heroPower;
    private String specialPower;
    private int hp;
    private String address;

    public void paint(Graphics g, int x, int y) {
        try {
            Image hero = ImageIO.read(new File(address));
            g.drawImage(hero, x, y, null);
        }catch (Exception e){}
    }

    public HeroPower getHeroPower() {
        return heroPower;
    }
}
