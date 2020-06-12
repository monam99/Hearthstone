package view.logIn;

import model.gameData.GameData;
import view.graphicUtils.MFrame;

public class LogInFrame extends MFrame {

    public static LogInFrame logInFrame;
    private LogInDisplay logInDisplay;

    public static LogInFrame getLogInFrame(){
        if (logInFrame == null)
            logInFrame = new LogInFrame();
        return logInFrame;
    }

    private LogInFrame(){
        super();
        logInDisplay = new LogInDisplay(GameData.getData().getAllUsers());
        logInDisplay.setBounds(0, 0, WIDTH, HEIGHT);
        add(logInDisplay);
        setVisible(true);
    }





}
