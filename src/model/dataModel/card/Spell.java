package model.dataModel.card;

public class Spell extends Card  {

    private boolean isQuest;

    public Spell(String name, String desc, String address,
                 int manaCost, int price, Rarity rarity,
                 CardClass cardClass, boolean isQuest, String desc1) {
        super(name, desc, address, manaCost, price, rarity, cardClass);
        this.isQuest = isQuest;
    }

    public boolean isQuest() {
        return isQuest;
    }
}
