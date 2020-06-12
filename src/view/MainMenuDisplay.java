package view;

import model.gameData.GameData;
import utils.Config;
import utils.PanelLoader;
import view.graphicUtils.BtnCorLoader;
import view.graphicUtils.InvisibleBtnListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static view.MainFrame.cl;
import static view.MainFrame.mainPanel;

public class MainMenuDisplay extends JPanel implements PanelLoader, BtnCorLoader {

    private Config properties;
    private String backgroundAddress;
    private int[] PLAY = new int[4],
            SHOP = new int[4],
            SETTING = new int[4],
            STATUS = new int[4],
            COLLECTION = new int[4],
            EXIT = new int[4];


    public MainMenuDisplay() {
        properties = new Config();
        loadProperties(properties, "MAIN_MENU");
        initParams();
        setListeners();

    }

    private void setListeners() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, PLAY[0], PLAY[1], PLAY[2], PLAY[3]))
                    cl.show(mainPanel, "PLAY");
                if (checkCondition(e, EXIT[0], EXIT[1], EXIT[2], EXIT[3])) {
                    GameData.getData().saveUsers();
                    System.exit(0);
                }
                if (checkCondition(e, SHOP[0], SHOP[1], SHOP[2], SHOP[3]))
                    cl.show(mainPanel, "SHOP");
                if (checkCondition(e, STATUS[0], STATUS[1], STATUS[2], STATUS[3]))
                    cl.show(mainPanel, "STATUS");
                if (checkCondition(e, COLLECTION[0], COLLECTION[1], COLLECTION[2], COLLECTION[3]))
                    cl.show(mainPanel, "COLLECTION");
                if (checkCondition(e, SETTING[0], SETTING[1], SETTING[2], SETTING[3]))
                    cl.show(mainPanel, "SETTING");
            }
        });
//        setPlayListener();
//        setShopListener();
//        setStatusListener();
//        setCollectionListener();
//        setExitListener();
//        setSettingListener();
    }

    private void initParams() {
        backgroundAddress = properties.getProperty("backgroundAddress");
        initBtnsCor();
    }

    private void initBtnsCor() {
        setCor(properties, "PLAY", PLAY);
        setCor(properties, "EXIT", EXIT);
        setCor(properties, "STATUS", STATUS);
        setCor(properties, "SETTING", SETTING);
        setCor(properties, "SHOP", SHOP);
        setCor(properties, "COLLECTION", COLLECTION);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image background = ImageIO.read(new File(backgroundAddress));
            g.drawImage(background, 0, 0, null);
        } catch (IOException e) {
        }
        requestFocus();
    }


}
