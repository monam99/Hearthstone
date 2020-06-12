package view;

import controller.Collection;
import model.dataModel.card.Card;
import model.dataModel.card.Deck;
import model.gameData.GameData;
import utils.Config;
import utils.PanelLoader;
import view.graphicUtils.BtnCorLoader;
import view.graphicUtils.InvisibleBtnListener;
import view.graphicUtils.MTextField;
import view.graphicUtils.PanelCounter;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static view.MainFrame.cl;
import static view.MainFrame.mainPanel;

public class CollectionDisplay extends JPanel implements PanelLoader, BtnCorLoader, PanelCounter {

    private Config properties;
    private String[] backgrounds = new String[7],
            vectorBtn = new String[4];
    private int[] EXIT = new int[4],
            MENU = new int[4],
            BACK = new int[4],
            NEXT = new int[4],
            PREVIOUS = new int[4],
            UP = new int[4],
            DOWN = new int[4],
            MANA = new int[4],
            SEARCH = new int[4],
            MANA_PLUS = new int[4],
            NEW_DECK = new int[4];
    private int COLLECTION_START_X, COLLECTION_START_Y, CARDS_GAP, DELETE_DECK_START_X,
            CARD_WIDTH, CARD_HEIGHT, DECK_START_X, DECK_START_Y, DECK_GAP;
    private Collection collection;
    private MTextField search, newDeck;


    public CollectionDisplay() {
        properties = new Config();
        collection = new Collection();
        loadProperties(properties, "COLLECTION");
        setLayout(null);
        initParam();
        initTextField();
        update();
        setListener();
    }

    ///////LISTENERS////////////////////////////////////////////////////////////////////////////////////////////////////

    private void setListener() {
        setMainListener();
        setVectorListener();
    }

    private void setMainListener() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, MENU[0], MENU[1], MENU[2], MENU[3])) {
                    collection.reset();
                    cl.show(mainPanel, "MAIN_MENU");
                }
                if (checkCondition(e, EXIT[0], EXIT[1], EXIT[2], EXIT[3])) {
                    GameData.getData().saveUsers();
                    System.exit(0);
                }
                if (checkCondition(e, BACK[0], BACK[1], BACK[2], BACK[3])) {
                    collection.reset();
                    update();
                }
                if (checkCondition(e, MANA[0], MANA[1], MANA[2], MANA[3])) {
                    collection.reset();
                    collection.setCurrentPanel(0);
                    update();
                }
                if (checkCondition(e, MANA_PLUS[0], MANA_PLUS[1], MANA_PLUS[2], MANA_PLUS[3])) {
                    collection.setMana(collection.getMana() + 1);
                    collection.setCurrentPanel(0);
                    update();
                }
                if (checkCondition(e, SEARCH[0], SEARCH[1], SEARCH[2], SEARCH[3])) {
                    collection.setSearch(search.getText());
                    update();
                }
                if (checkCondition(e, NEW_DECK[0], NEW_DECK[1], NEW_DECK[2], NEW_DECK[3])) {
                    if (collection.showAllDecks()) {
                        if (collection.isValidDeckName(newDeck.getText())) {
                            Deck deck = new Deck(newDeck.getText());
                            collection.getUser().getDecks().add(0, deck);
                            collection.getDeck().add(0, 0);
                        } else
                            invalidDeckNameError(newDeck.getText());
                    } else
                        collection.getUser().getDecks().get(collection.getDeckIndex()).setName(newDeck.getText());
                    update();
                }
                int x = e.getX(), y = e.getY();
                setCardClassListener(x, y);
                setManaListener(x, y);
                setYourCardListener(x, y);
                setDecksListener(x, y);
                setDeckCardAddingListener(x, y);
            }
        });
    }

    private void setDeckCardAddingListener(int x, int y) {
        if (!collection.showAllDecks())
            if (x >= COLLECTION_START_X && x <= COLLECTION_START_X + 3 * CARD_WIDTH + 2 * CARDS_GAP && y >= COLLECTION_START_Y &&
                    y <= COLLECTION_START_Y + 2 * CARD_HEIGHT + CARDS_GAP)
                for (int i = 0; i < 3; i++) {
                    if (x >= COLLECTION_START_X + i * (CARD_WIDTH + CARDS_GAP) &&
                            x <= COLLECTION_START_X + i * (CARD_WIDTH + CARDS_GAP) + CARD_WIDTH) {
                        if (y >= COLLECTION_START_Y && y <= COLLECTION_START_Y + CARD_HEIGHT) {
                            int index = collection.getCurrentPanel() * 6 + i;
                            if (collection.getCurrentClass() != 6) {
                                addCard(index);
                            } else {
                                addHero(index);
                            }
                        }
                        if (y >= COLLECTION_START_Y + CARD_HEIGHT + CARDS_GAP && y <= COLLECTION_START_Y + 2 * CARD_HEIGHT + CARDS_GAP) {
                            int index = collection.getCurrentPanel() * (6) + i + 3;
                            if (collection.getCurrentClass() != 6) {
                                addCard(index);
                            } else {
                                addHero(index);
                            }
                        }
                    }
                }
    }

    private void addHero(int index) {
        if (collection.canAddHero(index) == 1) {
            collection.addHero(index);
        } else if (collection.canAddHero(index) == -1)
            canNotAddHeroError();
        update();
    }

    private void addCard(int index) {
        int con = collection.canAddCard(index);
        if (con == 1) {
            collection.addCard(index);
        } else if (con == -1) {
            canNotAddCardError1();
        } else if (con == -2) {
            canNotAddCardError2();
        } else if (con == -3) {
            canNotAddCardError3();
        }
        update();
    }

    private void setDecksListener(int x, int y) {
        for (int i = 0; i < 22; i++) {
            if (y >= DECK_START_Y + i * (DECK_GAP) - 5 && y <= DECK_START_Y + (i + 1) * (DECK_GAP) - 5) {
                int index = y / DECK_GAP + collection.getDecksCurrentPanel() * 22;
                if (x >= DECK_START_X && x <= DECK_START_X + 100) {
                    if (collection.showAllDecks()) {
                        collection.getDeck().set(index - 3, 1);
                        collection.setYourCards(1);
                        collection.setDecksCurrentPanel(0);
                        collection.setDecksMaxPanel(0);
                        collection.setDeckCurrentPanel(0);
                        collection.setDeckMaxPanel(0);
                    } else {
                        ArrayList<Card> deck = collection.getUser().getDecks().get(collection.getDeckIndex()).getCards();
                        if (index > 4 && deck.size() > index - 5)
                            deck.remove(index + collection.getDeckCurrentPanel() * 20 - 5);
                    }
                }
                if (x >= DELETE_DECK_START_X && x <= DELETE_DECK_START_X + 10) {
                    collection.getUser().getDecks().remove(index - 3);
                }
            }
            update();
        }
    }

    private void setVectorListener() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, PREVIOUS[0], PREVIOUS[1], PREVIOUS[2], PREVIOUS[3]))
                    if (collection.getCurrentPanel() > 0) {
                        collection.setCurrentPanel(collection.getCurrentPanel() - 1);
                        update();
                    }
                if (checkCondition(e, NEXT[0], NEXT[1], NEXT[2], NEXT[3])) {
                    if (collection.getCurrentPanel() < collection.getMaxPanels()) {
                        collection.setCurrentPanel(collection.getCurrentPanel() + 1);
                        update();
                    }
                }
                if (checkCondition(e, UP[0], UP[1], UP[2], UP[3]))
                    if (collection.showAllDecks()) {
                        if (collection.getDecksCurrentPanel() > 0) {
                            collection.setDecksCurrentPanel(collection.getDecksCurrentPanel() - 1);
                            update();
                        }
                    } else {
                        if (collection.getDeckCurrentPanel() > 0) {
                            collection.setDeckCurrentPanel(collection.getDeckCurrentPanel() - 1);
                            update();
                        }
                    }
                if (checkCondition(e, DOWN[0], DOWN[1], DOWN[2], DOWN[3]))
                    if (collection.showAllDecks()) {
                        if (collection.getDecksCurrentPanel() < collection.getDecksMaxPanel()) {
                            collection.setDecksCurrentPanel(collection.getDecksCurrentPanel() + 1);
                            update();
                        }
                    } else {
                        if (collection.getDeckCurrentPanel() < collection.getDeckMaxPanel()) {
                            collection.setDeckCurrentPanel(collection.getDeckCurrentPanel() + 1);
                            update();
                        }
                    }
            }
        });
    }

    private void setCardClassListener(int x, int y) {
        if (y >= 14 && y <= 32)
            if (x >= 30 && x <= 320)
                if (x / 50 != 5) {
                    collection.setCurrentClass((x / 50) == 6 ? 5 : (x / 50));
                    collection.setCurrentPanel(0);
                    update();
                }
    }

    private void setManaListener(int x, int y) {
        if (y >= 542 && y <= 557)
            if (x >= 110 && x <= 350) {
                collection.setMana(x / 28 - 4);
                collection.setCurrentPanel(0);
            }
        update();
    }

    private void setYourCardListener(int x, int y) {
        if (y >= 12 && y <= 32) {
            if (x >= 556 && x <= 595) {
                collection.setYourCards(collection.getYourCards() == 1 ? 2 : 1);
                collection.setCurrentPanel(0);
            }
            if (x >= 595 && x <= 634) {
                collection.setCurrentClass(6);
                collection.setCurrentPanel(0);
            }
        }
        update();
    }


    ///////ERROR////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void invalidDeckNameError(String deckName) {
        JOptionPane.showMessageDialog(this,
                "you already have a deck named" + deckName,
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void canNotAddHeroError() {
        JOptionPane.showMessageDialog(this,
                "This hero doesn't match some of your deck's cards",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void canNotAddCardError3() {
        JOptionPane.showMessageDialog(this,
                "This card doesn't match to your hero.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void canNotAddCardError2() {
        JOptionPane.showMessageDialog(this,
                "You can't have more than 30 cards in your deck.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void canNotAddCardError1() {
        JOptionPane.showMessageDialog(this,
                "you can't have more than 2 same card in your deck.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    ///////UPDATE///////////////////////////////////////////////////////////////////////////////////////////////////////

    private void update() {
        collection.update();
        repaint();
    }

    ///////INITIALIZING/////////////////////////////////////////////////////////////////////////////////////////////////

    private void initParam() {
        for (int i = 0; i < 7; i++)
            backgrounds[i] = properties.getProperty("background" + i);
        DECK_START_X = properties.getNumber("DECK_START_X");
        DECK_START_Y = properties.getNumber("DECK_START_Y");
        DECK_GAP = properties.getNumber("DECK_GAP");
        COLLECTION_START_X = properties.getNumber("COLLECTION_START_X");
        COLLECTION_START_Y = properties.getNumber("COLLECTION_START_Y");
        CARDS_GAP = properties.getNumber("CARDS_GAP");
        CARD_WIDTH = properties.getNumber("CARD_WIDTH");
        CARD_HEIGHT = properties.getNumber("CARD_HEIGHT");
        DELETE_DECK_START_X = properties.getNumber("DELETE_DECK_START_X");
        initBtn();
        vectorBtn[0] = properties.getProperty("previousBtnAddress");
        vectorBtn[1] = properties.getProperty("nextBtnAddress");
        vectorBtn[2] = properties.getProperty("upBtnAddress");
        vectorBtn[3] = properties.getProperty("downBtnAddress");
    }

    private void initBtn() {
        setCor(properties, "EXIT", EXIT);
        setCor(properties, "MENU", MENU);
        setCor(properties, "BACK", BACK);
        setCor(properties, "NEXT", NEXT);
        setCor(properties, "PREVIOUS", PREVIOUS);
        setCor(properties, "MANA", MANA);
        setCor(properties, "SEARCH", SEARCH);
        setCor(properties, "MANA_PLUS", MANA_PLUS);
        setCor(properties, "NEW_DECK", NEW_DECK);
        setCor(properties, "UP", UP);
        setCor(properties, "DOWN", DOWN);
    }

    private void initTextField() {
        search = new MTextField("Search here");
        search.setBounds(430, 542, 110, 15);
        add(search);
        newDeck = new MTextField("Deck's name");
        newDeck.setBounds(716, 505, 120, 20);
        add(newDeck);
    }

    ///////PAINTING/////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image background = null;
        try {
            background = ImageIO.read(new File(backgrounds[collection.getCurrentClass()]));
        } catch (IOException e) {
        }
        g.drawImage(background, 0, 0, null);
        paintVectorBtn(g);
        if (collection.getCurrentClass() != 6) {
            paintCards(g);
        } else {
            paintHeroes(g);
        }
        paintDeckPanel(g);
        paintDeckBtns(g);
        requestFocus();
    }

    private void paintDeckBtns(Graphics g) {
        if (collection.showAllDecks())
            g.drawString("New Deck.", NEW_DECK[0], NEW_DECK[1] + DECK_GAP / 2);
        else
            g.drawString("Edit Name.", NEW_DECK[0], NEW_DECK[1] + DECK_GAP / 2);
    }

    private void paintHeroes(Graphics g) {
        for (int i = 0; i < 3; i++) {
            collection.getHeroes().get(i).paint(g, COLLECTION_START_X + i * CARDS_GAP + i * CARD_WIDTH, COLLECTION_START_Y);
        }
        for (int i = 3; i < 5; i++) {
            collection.getHeroes().get(i).paint(g, COLLECTION_START_X + (i - 3) * CARDS_GAP + (i - 3) * CARD_WIDTH,
                    COLLECTION_START_Y + CARDS_GAP + CARD_HEIGHT);
        }
    }

    private void paintDeckPanel(Graphics g) {
        g.setColor(Color.WHITE);
        if (collection.showAllDecks()) {
            paintDecks(g);
        } else {
            paintSingleDeck(g);
        }
    }

    private void paintSingleDeck(Graphics g) {
        int index = collection.getDeckIndex(), gap = 0;
        Deck deck = collection.getUser().getDecks().get(index);
        g.setColor(Color.GREEN);
        g.drawString(deck.getName(), DECK_START_X, DECK_START_Y);
        g.setColor(Color.RED);
        if (deck.getHero() != null)
            g.drawString(deck.getHero().toString(), DECK_START_X, DECK_START_Y + DECK_GAP);
        g.setColor(Color.WHITE);
        paintCardsInDeck(g, deck);
        g.drawString(deck.getCards().size() + "/" + "30",DECK_START_X + 5*DECK_GAP,DECK_START_Y);
    }

    private void paintCardsInDeck(Graphics g, Deck deck) {
        int deckCurrentPanel = collection.getDeckCurrentPanel();
        int cardInPanel = 20, temp = cardInPanel * deckCurrentPanel, gap = 0;
        ArrayList<Card> cards = deck.getCards();
        for (int i = temp; i < temp + cardInPanel; i++) {
            if (cards.size() > i) {
                g.drawString(cards.get(i).getName(), DECK_START_X + DECK_GAP, DECK_START_Y + (gap + 2) * DECK_GAP);
                gap++;
            } else
                break;
        }
    }

    private void paintDecks(Graphics g) {
        ArrayList<Deck> decks = collection.getUser().getDecks();
        int deckCurrentPanel = collection.getDecksCurrentPanel();
        int deckInPanel = 22, temp = deckInPanel * deckCurrentPanel, gap = 0;
        for (int i = temp; i < temp + deckInPanel; i++)
            if (decks.size() > i) {
                g.drawString(decks.get(i).getName(), DECK_START_X, DECK_START_Y + gap * DECK_GAP);
                g.setColor(Color.RED);
                g.drawString("x", DELETE_DECK_START_X, DECK_START_Y + gap * DECK_GAP);
                g.setColor(Color.WHITE);
                gap++;
            }
    }

    private void paintVectorBtn(Graphics g) {
        Image[] vBtn = new Image[4];
        try {
            for (int i = 0; i < 4; i++)
                vBtn[i] = ImageIO.read(new File(vectorBtn[i]));
        } catch (IOException e) {
        }
        if (collection.getCurrentPanel() != 0)
            g.drawImage(vBtn[0], PREVIOUS[0], PREVIOUS[1], null);
        if (collection.getCurrentPanel() != collection.getMaxPanels())
            g.drawImage(vBtn[1], NEXT[0], NEXT[1], null);
        if (collection.getDecksCurrentPanel() != 0 || collection.getDeckCurrentPanel() != 0)
            g.drawImage(vBtn[2], UP[0], UP[1], null);
        if (collection.getDecksCurrentPanel() != collection.getDecksMaxPanel() ||
                collection.getDeckCurrentPanel() != collection.getDeckMaxPanel())
            g.drawImage(vBtn[3], DOWN[0], DOWN[1], null);

    }

    private void paintCards(Graphics g) {
        ArrayList<Card> cardCollection = collection.getCurrentCollection();
        int currentPanel = collection.getCurrentPanel();
        int cardInPanel = 6, temp = cardInPanel * currentPanel, gap = 0;
        for (int i = temp; i < temp + 3; i++) {
            if (cardCollection.size() > i) {
                cardCollection.get(i).paint(g, COLLECTION_START_X + gap * CARDS_GAP + gap * CARD_WIDTH, COLLECTION_START_Y);
                gap++;
            } else
                break;
        }
        gap = 0;
        for (int i = temp + 3; i < temp + 6; i++) {
            if (cardCollection.size() > i) {
                cardCollection.get(i).paint(g, COLLECTION_START_X + gap * CARDS_GAP + gap * CARD_WIDTH,
                        COLLECTION_START_Y + CARDS_GAP + CARD_HEIGHT);
                gap++;
            } else
                break;
        }

    }
}
