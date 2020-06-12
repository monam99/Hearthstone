package model.dataModel.card;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Card implements Serializable {

    private String name;
    private String desc;
    private String address;
    private int manaCost;
    private int timesBeenUsed;
    private int price;
    private Rarity rarity;
    private CardClass cardClass;

    protected Card(String name, String desc, String address,
                int manaCost, int price, Rarity rarity,
                CardClass cardClass) {
        this.name = name;
        this.desc = desc;
        this.address = address;
        this.manaCost = manaCost;
        this.price = price;
        this.rarity = rarity;
        this.cardClass = cardClass;

    }

    public void paint(Graphics g , int x, int y){
        Image image = null;
        try {
            image = ImageIO.read(new File(address));
        }catch (Exception e){
        }
        g.drawImage(image, x, y, null);
    }

    public void paintScaled(Graphics g,int scale, int x, int y){
        Image image = null;
        try {
            image = ImageIO.read(new File(address));
        }catch (Exception e){
        }
        Image scaledImage = image.getScaledInstance(image.getWidth(null)/scale,
                image.getHeight(null)/scale,
                Image.SCALE_SMOOTH);
        g.drawImage(scaledImage,x,y,null);
    }

    public boolean isMemberOf(ArrayList<Card> collection){
        for (Card card : collection){
            if (card.getName().toLowerCase().equals(this.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public CardClass getCardClass() {
        return cardClass;
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return price;
    }
}

