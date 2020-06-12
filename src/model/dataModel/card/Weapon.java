package model.dataModel.card;

public class Weapon extends Card  {

    private int attack;
    private int durability;

    public Weapon(String name, String desc, String address,
                  int manaCost, int price, Rarity rarity,
                  CardClass cardClass, int attack, int durability) {
        super(name, desc, address, manaCost, price, rarity, cardClass);
        this.attack = attack;
        this.durability = durability;
    }
}
