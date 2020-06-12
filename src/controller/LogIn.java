package controller;

import model.dataModel.User;
import model.gameData.GameData;
import view.MainFrame;

import java.util.List;

public class LogIn {

    private List<User> allUsers;

    public LogIn(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public void signUp(String username, String password) {
        User user = new User(username, password);
        allUsers.add(user);
        user.getCollection().addAll(GameData.getData().getDefaultCards());
        GameData.getData().setCurrentUser(user);
        MainFrame.getMainFrame();

    }

    public boolean checkPassword(String username , String password) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)){
                if (user.getPassword().equals(password)) {
                    GameData.getData().setCurrentUser(user);
                    MainFrame.getMainFrame();
                    return true;
                }
            }
        }
        return false;
    }

}
