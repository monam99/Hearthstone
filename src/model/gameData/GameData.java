package model.gameData;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import model.dataModel.User;
import model.dataModel.card.Card;
import model.dataModel.card.Minion;
import model.dataModel.card.Spell;
import model.dataModel.card.Weapon;
import model.dataModel.hero.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class GameData {

    //TODO MORE HERE WITH PROPERTIES
    public static GameData data;
    private ArrayList<Card> defaultCards;
    private ArrayList<Card> allCards;
    //TODO MORE
    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Hero> allHeroes;
    private User currentUser;

    public static GameData getData(){
        if (data == null)
            data = new GameData();
        return data;
    }

    private GameData(){
        allCards = new ArrayList<>();
        defaultCards = new ArrayList<>();
        allHeroes = new ArrayList<>();
        YaGson yaGson = new YaGson();
        loadCards(yaGson);
        loadHeroes(yaGson);
        loadUsers();

    }

    private void loadUsers() {
        try {
            FileInputStream fis = new FileInputStream("resources/data/allUsers.text");
            ObjectInputStream objIn = new ObjectInputStream(fis);
            allUsers.addAll((Collection<? extends User>) objIn.readObject());
        }catch(Exception e){}
    }

    private void loadHeroes(YaGson yaGson) {
        try {
            allHeroes.add(yaGson.fromJson(new FileReader(new File("resources/data/hero/mage.json")), Mage.class));
            allHeroes.add(yaGson.fromJson(new FileReader(new File("resources/data/hero/rogue.json")), Rogue.class));
            allHeroes.add(yaGson.fromJson(new FileReader(new File("resources/data/hero/warlock.json")), Warlock.class));
            allHeroes.add(yaGson.fromJson(new FileReader(new File("resources/data/hero/paladin.json")), Paladin.class));
            allHeroes.add(yaGson.fromJson(new FileReader(new File("resources/data/hero/hunter.json")), Hunter.class));
        } catch (FileNotFoundException e) {
        }
    }

    private void loadCards(YaGson yaGson) {
        Type minionType = new TypeToken<ArrayList<Minion>>(){}.getType();
        try {
            allCards.addAll(yaGson.fromJson(new FileReader(new File("resources/data/card/minion.json")),minionType));
        } catch (FileNotFoundException e) {}
        Type spellType = new TypeToken<ArrayList<Spell>>(){}.getType();
        try{
            allCards.addAll(yaGson.fromJson(new FileReader(new File("resources/data/card/spell.json")),spellType));
        }catch (FileNotFoundException e){}
        Type weaponType = new TypeToken<ArrayList<Weapon>>(){}.getType();
        try{
            allCards.addAll(yaGson.fromJson(new FileReader(new File("resources/data/card/weapon.json")),weaponType));
        }catch (FileNotFoundException e){}
        try{
            defaultCards.addAll(yaGson.fromJson(new FileReader(new File("resources/data/card/defaultPack/minion.json")),minionType));
            defaultCards.addAll(yaGson.fromJson(new FileReader(new File("resources/data/card/defaultPack/spell.json")),spellType));
        } catch (FileNotFoundException e){}

    }

    public void saveUsers(){
        try {
            FileOutputStream fos = new FileOutputStream("resources/data/allUsers.text");
            ObjectOutputStream objOut = new ObjectOutputStream(fos);
            objOut.writeObject(allUsers);
            objOut.close();
        }catch (Exception e){}
    }

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Card> getDefaultCards() {
        return defaultCards;
    }

    public ArrayList<Hero> getAllHeroes() {
        return allHeroes;
    }
}
