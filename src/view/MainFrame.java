package view;

import model.gameData.GameData;
import view.graphicUtils.MFrame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends MFrame {

    public static MainFrame mainFrame;
    private GameData data;
    public static JPanel mainPanel;
    public final static CardLayout cl = new CardLayout();


    public static MainFrame getMainFrame() {
        if (mainFrame == null)
            mainFrame = new MainFrame();
        return mainFrame;
    }

    private MainFrame() {
        super();
        this.data = GameData.getData();
        mainPanel = new JPanel();
        mainPanel.setLayout(cl);
        setPanels();
        cl.show(mainPanel, "MAIN_MENU");
        add(mainPanel);
        setVisible(true);
    }

    private void setPanels() {
        setMainMenu();
        setShop();
        setStatus();
        setCollection();
        setPlay();
    }

    private void setCollection() {
        CollectionDisplay collectionDisplay = new CollectionDisplay();
        collectionDisplay.setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.add(collectionDisplay, "COLLECTION");
    }

    private void setStatus() {
        StatusDisplay statusDisplay = new StatusDisplay();
        statusDisplay.setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.add(statusDisplay, "STATUS");
    }

    private void setShop() {
        ShopDisplay shopDisplay = new ShopDisplay();
        shopDisplay.setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.add(shopDisplay, "SHOP");
    }

    private void setPlay() {
        PlayDisplay playDisplay = new PlayDisplay();
        playDisplay.setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.add(playDisplay, "PLAY");
    }

    private void setMainMenu() {
        MainMenuDisplay mainMenuDisplay = new MainMenuDisplay();
        mainMenuDisplay.setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.add(mainMenuDisplay, "MAIN_MENU");
    }

}
