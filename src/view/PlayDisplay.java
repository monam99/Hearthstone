package view;

import controller.Play;
import model.dataModel.card.Minion;
import model.gameData.GameData;
import utils.Config;
import utils.PanelLoader;
import view.graphicUtils.BtnCorLoader;
import view.graphicUtils.InvisibleBtnListener;
import view.graphicUtils.MTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static view.MainFrame.cl;
import static view.MainFrame.mainPanel;

public class PlayDisplay extends JPanel implements PanelLoader, BtnCorLoader {
    private Config properties;
    private String[] backgrounds = new String[2];
    private String mana;
    private Play play;
    private int[] MENU0 = new int[4],
            MENU1 = new int[4],
            PLAY = new int[4],
            EXIT = new int[4],
            END_TURN = new int[4];
    private MTextField deck;
    private int HAND_START_X, HAND_START_Y, HAND_GAP_X, HAND_GAP_Y, MANA_START_X, MANA_START_Y, MANA_GAP,
            HAND_CARD_WIDTH, HAND_CARD_HEIGHT, HERO_START_X, HERO_START_Y, HERO_POWER_START_X,
            HERO_POWER_START_Y, SUMMON_START_X, SUMMON_START_Y, SUMMON_GAP_X, SUMMON_GAP_Y;


    public PlayDisplay() {
        properties = new Config();
        play = new Play();
        loadProperties(properties, "PLAY");
        setLayout(null);
        initParam();
        setTextField();
        setListener();
    }

    private void setListener() {
        setMainListener0();
        setPlayingCardListener();
    }

    private void setPlayingCardListener() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, HAND_START_X, HAND_START_Y,
                        HAND_START_X + 12 * HAND_CARD_WIDTH,
                        HAND_START_Y + HAND_CARD_HEIGHT + HAND_GAP_Y))
                    for (int i = 0; i < 12; i++) {
                        if (e.getX() >= HAND_START_X + i * (HAND_GAP_X) &&
                                e.getX() <= HAND_START_X + i * (HAND_GAP_X) + HAND_CARD_WIDTH) {
                            if (e.getY() >= HAND_START_Y && e.getY() <= HAND_START_Y + HAND_CARD_HEIGHT)
                                play.play(i);
                            if (e.getY() >= HAND_START_Y + HAND_CARD_HEIGHT + HAND_GAP_Y &&
                                    e.getY() <= HAND_START_Y + HAND_CARD_HEIGHT + HAND_GAP_Y)
                                play.play(i + 12);
                            update();
                        }
                    }
            }
        });
    }

    private void setMainListener0() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (play.getState() == 0) {
                    if (checkCondition(e, MENU0[0], MENU0[1], MENU0[2], MENU0[3]))
                        cl.show(mainPanel, "MAIN_MENU");
                    if (checkCondition(e, PLAY[0], PLAY[1], PLAY[2], PLAY[3])) {
                        String deckName = deck.getText();
                        if (deckName.length() > 0) {
                            int con = play.deckIsValid(deckName);
                            if (con == 1) {
                                play.setDeck(deckName);
                                remove(deck);
                                play.startGame();
                                update();
                            } else if (con == 2) {
                                invalidDeckHeroError();
                            } else if (con == 3) {
                                invalidDeckSizeError();
                            } else if (con == 0)
                                invalidDeckNameError(deckName);
                        } else
                            didNotChooseDeckError();
                    }
                } else {
                    if (checkCondition(e, MENU1[0], MENU1[1], MENU1[2], MENU1[3]))
                        cl.show(mainPanel, "MAIN_MENU");
                    if (checkCondition(e, END_TURN[0], END_TURN[1], END_TURN[2], END_TURN[3])) {
                        play.endTurn();
                        update();
                    }
                    if (checkCondition(e, EXIT[0], EXIT[1], EXIT[2], EXIT[3])) {
                        GameData.getData().saveUsers();
                        System.exit(0);
                    }
                }
            }
        });
    }

    private void update() {
        repaint();
    }

    private void invalidDeckNameError(String deckName) {
        JOptionPane.showMessageDialog(this,
                "There is no deck named " + deckName,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void invalidDeckHeroError() {
        JOptionPane.showMessageDialog(this,
                "First go to collection and choose your deck's hero.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void invalidDeckSizeError() {
        JOptionPane.showMessageDialog(this,
                "This deck doesn't contain any card.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void didNotChooseDeckError() {
        JOptionPane.showMessageDialog(this,
                "You haven't chosen your deck.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void setTextField() {
        deck = new MTextField("Deck's Name");
        deck.setBounds(PLAY[0] + 10, PLAY[3] + 15, 235, 30);
        add(deck);
    }

    private void initParam() {
        for (int i = 0; i < 2; i++)
            backgrounds[i] = properties.getProperty("background" + i);
        mana = properties.getProperty("mana");
        initHandCor();
        initManaCor();
        initHandCard();
        initHeroStuff();
        initSummoned();
        initBtn();
    }

    private void initSummoned() {
        SUMMON_START_X = properties.getNumber("SUMMON_START_X");
        SUMMON_START_Y = properties.getNumber("SUMMON_START_Y");
        SUMMON_GAP_X = properties.getNumber("SUMMON_GAP_X");
        SUMMON_GAP_Y = properties.getNumber("SUMMON_GAP_Y");
    }

    private void initHeroStuff() {
        HERO_START_X = properties.getNumber("HERO_START_X");
        HERO_START_Y = properties.getNumber("HERO_START_Y");
        HERO_POWER_START_X = properties.getNumber("HERO_POWER_START_X");
        HERO_POWER_START_Y = properties.getNumber("HERO_POWER_START_Y");
    }

    private void initHandCard() {
        HAND_CARD_WIDTH = properties.getNumber("HAND_CARD_WIDTH");
        HAND_CARD_HEIGHT = properties.getNumber("HAND_CARD_HEIGHT");
    }

    private void initManaCor() {
        MANA_START_X = properties.getNumber("MANA_START_X");
        MANA_START_Y = properties.getNumber("MANA_START_Y");
        MANA_GAP = properties.getNumber("MANA_GAP");
    }

    private void initHandCor() {
        HAND_START_X = properties.getNumber("HAND_START_X");
        HAND_START_Y = properties.getNumber("HAND_START_Y");
        HAND_GAP_X = properties.getNumber("HAND_GAP_X");
        HAND_GAP_Y = properties.getNumber("HAND_GAP_Y");
    }

    private void initBtn() {
        setCor(properties, "PLAY", PLAY);
        setCor(properties, "MENU0", MENU0);
        setCor(properties, "MENU1", MENU1);
        setCor(properties, "EXIT", EXIT);
        setCor(properties, "END_TURN", END_TURN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image background = null;
        try {
            background = ImageIO.read(new File(backgrounds[play.getState()]));
        } catch (IOException e) {
        }
        g.drawImage(background, 0, 0, null);
        if (play.getState() == 1) {
            play.getHero().paint(g, HERO_START_X, HERO_START_Y);
            play.getHero().getHeroPower().paint(g, HERO_POWER_START_X, HERO_POWER_START_Y);
        }
        paintWeapon(g);
        paintHand(g);
        paintMana(g);
        paintPlayGround(g);
        if (play.getState() == 1)
            paintRemainedCards(g);
    }

    private void paintWeapon(Graphics g) {
        for (int i = 0; i < 3; i++)
            if (play.getWeapon().size() > i)
                play.getWeapon().get(i).paintScaled(g, 5, (HERO_START_X - 10) - i * 35, HERO_START_Y - 10);
        for (int i = 0; i < 3; i++)
            if (play.getWeapon().size() > i + 3)
                play.getWeapon().get(i + 3).paintScaled(g, 5, (HERO_START_X - 10) - i * 35, HERO_START_Y + 35);
    }

    private void paintRemainedCards(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        g.drawString(play.getDeck().size() + " cards remained.", 400, 268);
    }

    private void paintPlayGround(Graphics g) {
        ArrayList<Minion> summoned = play.getSummonedCard();
        for (int i = 0; i < 10; i++)
            if (summoned.size() > i)
                summoned.get(i).paintScaled(g, 2, SUMMON_START_X + i * SUMMON_GAP_X, SUMMON_START_Y);
        for (int i = 0; i < 10; i++)
            if (summoned.size() > i + 10)
                summoned.get(i + 10).paintScaled(g, 2, SUMMON_START_X + i * SUMMON_GAP_X, SUMMON_START_Y + SUMMON_GAP_Y);
        for (int i = 0; i < 10; i++)
            if (summoned.size() > i + 20)
                summoned.get(i + 20).paintScaled(g, 2, SUMMON_START_X + i * SUMMON_GAP_X, SUMMON_START_Y - SUMMON_GAP_Y);
    }

    private void paintMana(Graphics g) {
        g.setColor(Color.WHITE);
        if (play.getState() == 1)
            g.drawString(play.getMana() + "/" + play.getMaxMana(), 616, 558);
        Image manaImage = null;
        try {
            manaImage = ImageIO.read(new File(mana));
        } catch (IOException e) {
        }
        for (int i = 0; i < play.getMaxMana(); i++)
            g.drawImage(manaImage, MANA_START_X + i * MANA_GAP, MANA_START_Y - 5, null);
    }

    private void paintHand(Graphics g) {
        for (int i = 0; i < 12; i++)
            if (play.getHand().size() > i)
                play.getHand().get(i).paintScaled(g, 5, HAND_START_X + i * HAND_GAP_X, HAND_START_Y);
    }
}
