package controller;

import model.dataModel.card.*;
import model.dataModel.hero.Hero;
import model.gameData.GameData;

import java.util.ArrayList;
import java.util.Random;

public class Play {
    private int state;
    private Hero hero;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Minion> summonedCard;
    private ArrayList<Weapon> weapon;
    private Random random;
    private int maxMana, mana;


    public Play() {
        state = 0;
        hand = new ArrayList<>();
        summonedCard = new ArrayList<>();
        deck = new ArrayList<>();
        weapon = new ArrayList<>();
        random = new Random();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDeck(String deckName) {
        for (Deck deck : GameData.getData().getCurrentUser().getDecks())
            if (deck.getName().equals(deckName)) {
                for (Card card : deck.getCards()) {
                    this.deck.add(card);
                }
                this.hero = deck.getHero();
                break;
            }
    }

    public int deckIsValid(String deckName) {
        for (Deck deck :
                GameData.getData().getCurrentUser().getDecks())
            if (deck.getName().toLowerCase().equals(deckName.toLowerCase())){
                if (deck.getHero() != null && deck.getCards().size() > 0)
                    return 1;
                if (deck.getHero() == null)
                    return 2;
                if (deck.getCards().size() == 0)
                    return 3;
            }
        return 0;
    }

    public void startGame() {
        state = 1;
        maxMana = 1;
        mana = maxMana;
        int c = 0;
        if (haveQuest()) {
            while (haveQuest())
                drawQuest();
            c++;
        } else
            for (int i = 0; i < 3 - c; i++)
                drawACard();
    }

    private void drawQuest() {
        int c = 0;
        for (Card card :
                deck) {
            if (card.getClass().getSimpleName().toLowerCase().equals("spell")) {
                Spell s = (Spell) card;
                if (s.isQuest()) {
                    hand.add(s);
                    deck.remove(c);
                }
                c++;
            }
        }
    }

    private boolean haveQuest() {
        for (Card card : deck)
            if (card.getClass().getSimpleName().toLowerCase().equals("spell")) {
                Spell s = (Spell) card;
                if (s.isQuest())
                    return true;
            }
        return false;
    }

    public void endTurn() {
        if (maxMana < 10)
            maxMana++;
        mana = maxMana;
        drawACard();

    }

    private void drawACard() {
        if (deck.size() > 0) {
            int temp = random.nextInt(deck.size());
            Card card = deck.get(temp);
            if (hand.size() < 12)
                hand.add(card);
            deck.remove(temp);
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void play(int index) {
        if (hand.size() > index) {
            Card card = hand.get(index);
            if (card.getManaCost() <= mana) {
                String cardClass = card.getClass().getSimpleName().toLowerCase();
                switch (cardClass) {
                    case "spell":
                        break;
                    case "minion":
                        summonedCard.add((Minion) card);
                        break;
                    case "weapon":
                        weapon.add((Weapon) card);
                        break;
                }
                hand.remove(index);
                mana -= card.getManaCost();
            }
        }
    }

    public Hero getHero() {
        return hero;
    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Minion> getSummonedCard() {
        return summonedCard;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Weapon> getWeapon() {
        return weapon;
    }
}
