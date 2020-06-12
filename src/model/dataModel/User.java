package model.dataModel;

import model.dataModel.card.Card;
import model.dataModel.card.Deck;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String username;
    private String password;
    private ArrayList<Deck> decks ;
    private ArrayList<Card> collection ;
    private Deck mainDeck;
    private int wallet;

    public User(String username , String password) {
        this.username = username;
        this.password = password;
        wallet =50;
        collection = new ArrayList<>();
        decks = new ArrayList<>();
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Card> getCollection() {
        return collection;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
}
