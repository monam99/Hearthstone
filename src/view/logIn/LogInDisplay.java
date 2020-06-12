package view.logIn;

import controller.LogIn;
import model.dataModel.User;
import utils.Config;
import utils.PanelLoader;
import view.MainFrame;
import view.graphicUtils.LoadBtnAddress;
import view.graphicUtils.MBtnImageListener;
import view.graphicUtils.MButton;
import view.graphicUtils.MTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LogInDisplay extends JPanel implements LoadBtnAddress, PanelLoader {

    private String backgroundAddress;
    private JTextField username;
    private JTextField password;
    private int BTN_WIDTH, BTN_HEIGHT,
            T_FIELD_WIDTH, T_FIELD_HEIGHT,
            START_X, START_Y, GAP;
    private MButton signUpBtn, logInBtn;
    private String[] logInBtnImageAddress, signUpBtnImageAddress;
    private Config properties;
    private LogIn logIn;

    public LogInDisplay(ArrayList<User> allUsers) {
        properties = new Config();
        loadProperties(properties, "LOG_IN");
        initParams();
        initJTextField();
        setLayout(null);
        add(username);
        add(password);
        loadBtnImageAddress();
        initBtn();
        initBtnListener();
        logIn = new LogIn(allUsers);
        setTimer();
    }

    private void setTimer() {
        Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    private void giveError() {
        JOptionPane.showMessageDialog(this,
                "Password doesn't match username!",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void giveLengthError() {
        JOptionPane.showMessageDialog(this,
                "username and password should be more than one character.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void giveSpaceError() {
        JOptionPane.showMessageDialog(this,
                "username and password should not contain space.",
                "ERROR",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void initBtnListener() {
        setLogInListener();
        setSignUpListener();
    }

    private void setSignUpListener() {
        MBtnImageListener btnListener2 = new MBtnImageListener(signUpBtn) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, signUpBtn)) {
                    if (username.getText().length() == 0 || password.getText().length() == 0) {
                        giveLengthError();
                    } else if (username.getText().contains(" ") || password.getText().contains(" ")) {
                        giveSpaceError();
                    } else {
                        LogInFrame.getLogInFrame().setVisible(false);
                        logIn.signUp(username.getText(), password.getText());
                        MainFrame.cl.show(MainFrame.mainPanel, "MAIN_MENU");
                    }
                }
            }
        };
        addMouseListener(btnListener2);
    }

    private void setLogInListener() {
        MBtnImageListener btnListener1 = new MBtnImageListener(logInBtn) {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, logInBtn)) {
                    if (logIn.checkPassword(username.getText(), password.getText())) {
                        LogInFrame.getLogInFrame().setVisible(false);
                        MainFrame.cl.show(MainFrame.mainPanel, "MAIN_MENU");
                    } else
                        giveError();
                }
            }
        };
        addMouseListener(btnListener1);
    }

    private void initBtn() {
        logInBtn = new MButton(logInBtnImageAddress, START_X + BTN_WIDTH / 2 + GAP,
                START_Y + 2 * T_FIELD_HEIGHT + 2 * GAP);
        signUpBtn = new MButton(signUpBtnImageAddress, START_X - BTN_WIDTH / 2,
                START_Y + 2 * T_FIELD_HEIGHT + 2 * GAP);
    }

    private void initJTextField() {
        username = new MTextField("username");
        username.setBounds(START_X, START_Y, T_FIELD_WIDTH, T_FIELD_HEIGHT);
        password = new MTextField("password");
        password.setBounds(START_X, START_Y + T_FIELD_HEIGHT + GAP,
                T_FIELD_WIDTH, T_FIELD_HEIGHT);
    }

    private void initParams() {
        BTN_WIDTH = properties.getNumber("BTN_WIDTH");
        BTN_HEIGHT = properties.getNumber("BTN_HEIGHT");
        T_FIELD_WIDTH = properties.getNumber("T_FIELD_WIDTH");
        T_FIELD_HEIGHT = properties.getNumber("T_FIELD_HEIGHT");
        START_X = properties.getNumber("START_X");
        START_Y = properties.getNumber("START_Y");
        GAP = properties.getNumber("GAP");
        backgroundAddress = properties.getProperty("backgroundAddress");
    }

    private void loadBtnImageAddress() {
        logInBtnImageAddress = getImageAddresses("logInBtn", properties);
        signUpBtnImageAddress = getImageAddresses("signInBtn", properties);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image background = ImageIO.read(new File(backgroundAddress));
            g.drawImage(background, 0, 0, null);
        } catch (IOException e) {
        }
        logInBtn.paintMBtn(g);
        signUpBtn.paintMBtn(g);
    }
}
