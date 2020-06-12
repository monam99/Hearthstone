package model.dataModel.card;

import model.dataModel.hero.Hero;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {

    private String name;
    private Hero hero;
    private ArrayList<Card> cards;
    private int timesBeenWinner;
    private int timeBeenPlayed;
    private String imageAddress = "resources/images/deck1.png";

    public Deck(String name) {
        cards = new ArrayList<>();
        this.name = name;
    }

    public Deck(String name, int timesBeenWinner, int timeBeenPlayed) {
        cards = new ArrayList<>();
        this.name = name;
        this.timesBeenWinner = timesBeenWinner;
        this.timeBeenPlayed = timeBeenPlayed;
    }

    public int getWPP() {
        if (timeBeenPlayed != 0)
            return timesBeenWinner / timeBeenPlayed;
        return 0;
    }

    public int getTimesBeenWinner() {
        return timesBeenWinner;
    }

    public int getTimeBeenPlayed() {
        return timeBeenPlayed;
    }

    public int getDecksCardsAverage() {
        int sum = 0, num = cards.size();
        for (Card card :
                cards) {
            sum += card.getManaCost();
        }
        return num == 0 ? 0 : sum / num;
    }

    public void paint1(Graphics g, int x, int y) {
        Image image = null;
        try {
            image = ImageIO.read(new File(imageAddress));
        } catch (IOException e) {
        }
        g.drawImage(image, x, y, null);
        drawDesc(g, x, y);
    }

    private void drawDesc(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        int START_DESC_X = x + 180, START_DESC_Y = y + 35, ROW_GAP = 25;
        g.drawString(name, x + 50, y + 167);
        if (hero != null)
            g.drawString("Hero : " + hero.toString(), START_DESC_X, START_DESC_Y);
        g.drawString("Win chance : " + getWPP(), START_DESC_X, START_DESC_Y + ROW_GAP);
        g.drawString("victories : " + timesBeenWinner, START_DESC_X, START_DESC_Y + 2 * ROW_GAP);
        g.drawString("Matches : " + timeBeenPlayed, START_DESC_X, START_DESC_Y + 3 * ROW_GAP);
        g.drawString("Average cost : " + getDecksCardsAverage(), START_DESC_X, START_DESC_Y + 4 * ROW_GAP);
        g.drawString("Selected card : ", START_DESC_X, START_DESC_Y + 5 * ROW_GAP);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getCardNumber(String name) {
        int res = 0;
        for (Card card :
                cards)
            if (card.getName().toLowerCase().equals(name.toLowerCase()))
                res++;
        return res;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setName(String name) {
        this.name = name;
    }
}
