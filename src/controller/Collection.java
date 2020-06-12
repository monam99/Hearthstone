package controller;

import model.dataModel.User;
import model.dataModel.card.Card;
import model.dataModel.card.Deck;
import model.dataModel.hero.Hero;
import model.gameData.GameData;
import view.graphicUtils.PanelCounter;

import java.util.ArrayList;

public class Collection implements PanelCounter {

    // mana = -1 for all manas, yourCards; 0 = allCards, 1= owned cards, 2= unowned cards
    private int mana, yourCards, currentClass, currentPanel, maxPanels,
            decksCurrentPanel, decksMaxPanel, deckCurrentPanel, deckMaxPanel;
    private ArrayList<Card>[] cardClassCollection;
    private ArrayList<Card> currentCollection;
    private ArrayList<Hero> heroes;
    private String search;
    private ArrayList<Integer> deck = new ArrayList<>();
    private User user;


    public Collection() {
        user = GameData.getData().getCurrentUser();
        heroes = GameData.getData().getAllHeroes();
        reset();
        initDeck();
        initCards();
    }

    private void initDeck() {
        for (int i = 0; i < user.getDecks().size(); i++)
            deck.add(0);
    }

    public void reset() {
        currentClass = 0;
        currentPanel = 0;
        resetDeck();
        decksCurrentPanel = 0;
        yourCards = 0;
        mana = -1;
        search = "";
    }

    public void resetDeck() {
        int index = deck.indexOf(1);
        if (index != -1)
            deck.set(index, 0);
    }

    public void initCards() {
        ArrayList<Card>[] cardClassCollection = new ArrayList[6];
        for (int i = 0; i < 6; i++)
            cardClassCollection[i] = new ArrayList<>();
        for (Card card :
                GameData.getData().getAllCards())
            switch (card.getCardClass()) {
                case MAGE:
                    cardClassCollection[0].add(card);
                    break;
                case PALADIN:
                    cardClassCollection[1].add(card);
                    break;
                case ROGUE:
                    cardClassCollection[2].add(card);
                    break;
                case HUNTER:
                    cardClassCollection[3].add(card);
                    break;
                case WARLOCK:
                    cardClassCollection[4].add(card);
                    break;
                case NEUTRAL:
                    cardClassCollection[5].add(card);
            }
        this.cardClassCollection = cardClassCollection;
    }

    public ArrayList<Card> getCardsUserDoesNotHave() {
        ArrayList<Card> res = new ArrayList<>();
        for (Card card : GameData.getData().getAllCards())
            if (!card.isMemberOf(user.getCollection()))
                res.add(card);
        return res;
    }

    public ArrayList<Card> getCurrentCollection() {
        ArrayList<Card> res = new ArrayList<>();
        if (yourCards == 0)
            getCurrentCollection0(res);
        if (yourCards == 1)
            getCurrentCollection1(res);
        if (yourCards == 2)
            getCardCollection2(res);
        return res;
    }


    private void getCardCollection2(ArrayList<Card> res) {
        for (Card card :
                cardClassCollection[currentClass])
            if (card.isMemberOf(getCardsUserDoesNotHave()))
                if (mana == -1) {
                    if (card.getName().toLowerCase().contains(search.toLowerCase()))
                        res.add(card);
                } else if (card.getManaCost() == mana)
                    if (card.getName().toLowerCase().contains(search.toLowerCase()))
                        res.add(card);
    }

    private void getCurrentCollection1(ArrayList<Card> res) {
        for (Card card :
                cardClassCollection[currentClass])
            if (card.isMemberOf(user.getCollection()))
                if (mana == -1) {
                    if (card.getName().toLowerCase().contains(search.toLowerCase()))
                        res.add(card);
                } else if (card.getManaCost() == mana)
                    if (card.getName().toLowerCase().contains(search.toLowerCase()))
                        res.add(card);
    }

    private void getCurrentCollection0(ArrayList<Card> res) {
        for (Card card :
                cardClassCollection[currentClass])
            if (mana == -1) {
                if (card.getName().toLowerCase().contains(search.toLowerCase()))
                    res.add(card);
            } else if (card.getManaCost() == mana)
                if (card.getName().toLowerCase().contains(search.toLowerCase()))
                    res.add(card);
    }

    public void update() {
        if (currentClass != 6) {
            currentCollection = getCurrentCollection();
            setPanelsNumber();
        } else {
            maxPanels = 0;
            currentPanel = 0;
        }
    }

    private void setPanelsNumber() {
        maxPanels = getPanelsNumber(currentCollection.size(), 3, 2);
        decksMaxPanel = getPanelsNumber(user.getDecks().size(), 1, 22);
        if (getDeckIndex() != -1)
            deckMaxPanel = getPanelsNumber(user.getDecks().get(getDeckIndex()).getCards().size(), 1, 20);
    }

    public int canAddCard(int index) {
        int con1, con2, con3;
        Deck deck = user.getDecks().get(getDeckIndex());
        if (currentCollection.size() > index) {
            con1 = deck.getCardNumber(currentCollection.get(index).getName()) < 2 ? 1 : -1;
            con2 = deck.getCards().size() == 30 ? -2 : 1;
            if (deck.getHero() != null) {
                String res = currentCollection.get(index).getCardClass().toString().toLowerCase();
                con3 = res.equals(deck.getHero().toString().toLowerCase()) || res.equals("neutral") ? 1 : -3;
            } else {
                con3 = 1;
            }
            if (con1 == 1 && con2 == 1 && con3 == 1) {
                return 1;
            } else {
                if (con1 != 1)
                    return con1;
                if (con2 != 1)
                    return con2;
                if (con3 != 1)
                    return con3;
            }
        }
        return 0;
    }

    public int canAddHero(int index) {
        if (heroes.size() > index) {
            for (Card card :
                    user.getDecks().get(getDeckIndex()).getCards()) {
                String cardClass = card.getCardClass().toString().toLowerCase();
                if (!cardClass.equals(heroes.get(index).toString().toLowerCase()) &&
                        !cardClass.equals("neutral"))
                    return -1;
            }
            return 1;
        }
        return 0;
    }

    public void addCard(int index) {
        Card card = currentCollection.get(index);
        user.getDecks().get(getDeckIndex()).getCards().add(card);
    }

    public void addHero(int index) {
        user.getDecks().get(getDeckIndex()).setHero(heroes.get(index));
    }

    public boolean isValidDeckName(String name) {
        for (Deck deck : user.getDecks())
            if (deck.getName().equals(name))
                return false;
        return true;
    }

    public boolean showAllDecks() {
        int res = 0;
        for (Integer i : deck)
            res += i;
        return res == 0 ? true : false;
    }

    public int getDeckIndex() {
        return deck.indexOf(1);
    }
    
    public int getMana() {
        return mana;
    }

    public int getYourCards() {
        return yourCards;
    }

    public int getCurrentClass() {
        return currentClass;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setYourCards(int yourCards) {
        this.yourCards = yourCards;
    }

    public void setCurrentClass(int currentClass) {
        this.currentClass = currentClass;
    }

    public void setCurrentPanel(int currentPanel) {
        this.currentPanel = currentPanel;
    }

    public int getCurrentPanel() {
        return currentPanel;
    }

    public int getMaxPanels() {
        return maxPanels;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getDecksCurrentPanel() {
        return decksCurrentPanel;
    }

    public void setDecksCurrentPanel(int decksCurrentPanel) {
        this.decksCurrentPanel = decksCurrentPanel;
    }

    public int getDecksMaxPanel() {
        return decksMaxPanel;
    }

    public ArrayList<Integer> getDeck() {
        return deck;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public int getDeckCurrentPanel() {
        return deckCurrentPanel;
    }

    public int getDeckMaxPanel() {
        return deckMaxPanel;
    }

    public void setDeckCurrentPanel(int deckCurrentPanel) {
        this.deckCurrentPanel = deckCurrentPanel;
    }

    public void setDeckMaxPanel(int deckMaxPanel) {
        this.deckMaxPanel = deckMaxPanel;
    }

    public void setDecksMaxPanel(int decksMaxPanel) {
        this.decksMaxPanel = decksMaxPanel;
    }
}
