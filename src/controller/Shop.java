package controller;

import model.dataModel.User;
import model.dataModel.card.Card;
import model.dataModel.card.CardClass;
import model.gameData.GameData;
import view.graphicUtils.PanelCounter;

import java.util.ArrayList;

public class Shop implements PanelCounter {

    private ArrayList<Card> allCards, userCollection;
    private ArrayList<Card>[] cardsToDeal = new ArrayList[2];
    private int[] currentPanel,
            maxPanels = new int[2];
    private int state;
    private User user;

    public Shop() {
        user = GameData.getData().getCurrentUser();
        state = 0;
        currentPanel = new int[]{0, 0};
        update();
    }

    public void update() {
        this.allCards = GameData.getData().getAllCards();
        this.userCollection = user.getCollection();
        cardsToDeal[0] = getCardsToBuy();
        cardsToDeal[1] = getCardsToSell();
        maxPanels[0] = getPanelsNumber(cardsToDeal[0].size(), 3, 2);
        maxPanels[1] = getPanelsNumber(cardsToDeal[1].size(), 3, 2);
    }

    public void DealCard(int index) {
        if (state == 0)
            buyCard(index);
        if (state == 1)
            sellCard(index);
    }

    private void sellCard(int index) {
        Card card = cardsToDeal[state].get(index);
        int counter = 0;
        for (Card c :
                user.getCollection()) {
            if (c.getName().toLowerCase().equals(card.getName().toLowerCase())) {
                user.getCollection().remove(counter);
                break;
            }
            counter++;
        }
        user.setWallet(user.getWallet() + card.getPrice());
    }

    private void buyCard(int index) {
        Card card = cardsToDeal[state].get(index);
        if (canBuyCard(card, user.getWallet())) {
            user.getCollection().add(card);
            user.setWallet(user.getWallet() - card.getPrice());
        }
    }

    public boolean canBuyCard(Card card, int wallet) {
        return wallet >= card.getPrice();
    }

    public ArrayList<Card> getCardsToSell() {
        ArrayList<Card> res = new ArrayList<>();
        for (Card card : userCollection)
            if (card.getCardClass() == CardClass.NEUTRAL)
                res.add(card);
        return res;
    }

    public ArrayList<Card> getCardsToBuy() {
        ArrayList<Card> res = new ArrayList<>();
        for (Card card : allCards)
            if (!card.isMemberOf(userCollection))
                res.add(card);
        return res;
    }

    public ArrayList<Card>[] getCardsToDeal() {
        return cardsToDeal;
    }

    public int[] getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanelIndex(int index, int num) {
        currentPanel[index] = num;
    }

    public int[] getMaxPanels() {
        return maxPanels;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
