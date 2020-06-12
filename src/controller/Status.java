package controller;

import model.dataModel.card.Deck;
import model.gameData.GameData;

import java.util.ArrayList;
import java.util.Comparator;

public class Status {

    private ArrayList<Deck> decks;

    public Status(){
        ArrayList<Deck> temp = GameData.getData().getCurrentUser().getDecks();
        temp.sort(Comparator.comparing(Deck::getWPP).thenComparing(Deck::getTimesBeenWinner)
        .thenComparing(Deck::getTimeBeenPlayed).thenComparing(Deck::getDecksCardsAverage));
        this.decks = temp;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }
}
