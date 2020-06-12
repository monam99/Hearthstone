package view;

import model.gameData.GameData;
import utils.ConfigLoader;
import view.logIn.LogInFrame;

public class Main {

    public static void main(String[] args) {
        ConfigLoader loader =  ConfigLoader.getLoader();
        GameData gameData = GameData.getData();
        LogInFrame logInFrame = LogInFrame.getLogInFrame();
    }
}
