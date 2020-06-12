package model.dataModel.card;

public class Minion extends Card {

    private int health;
    private int attack;

    public Minion(String name, String desc, String address,
                  int manaCost, int price, Rarity rarity,
                  CardClass cardClass, int health, int attack) {
        super(name, desc, address, manaCost, price, rarity, cardClass);
        this.health = health;
        this.attack = attack;
    }

}
