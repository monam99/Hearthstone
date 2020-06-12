package view;

import controller.Status;
import model.dataModel.card.Deck;
import model.gameData.GameData;
import utils.Config;
import utils.PanelLoader;
import view.graphicUtils.BtnCorLoader;
import view.graphicUtils.InvisibleBtnListener;
import view.graphicUtils.PanelCounter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import static view.MainFrame.cl;
import static view.MainFrame.mainPanel;

public class StatusDisplay extends JPanel implements PanelLoader, BtnCorLoader, PanelCounter {
    private Config properties;
    private int[] EXIT = new int[4],
            MENU = new int[4],
            NEXT = new int[4],
            PREVIOUS = new int[4];
    private int maxPanels, currentPanel;
    private String backgroundAddress, nextBtnAddress, previousBtnAddress;
    private Status status;

    public StatusDisplay() {
        properties = new Config();
        loadProperties(properties, "STATUS");
        initParam();
        status = new Status();
        currentPanel = 0;
        maxPanels = getPanelsNumber(status.getDecks().size(), 1, 2);
        setListeners();
    }

    private void setListeners() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, PREVIOUS[0], PREVIOUS[1], PREVIOUS[2], PREVIOUS[3]))
                    if (currentPanel > 0) {
                        currentPanel--;
                        repaint();
                    }
                if (checkCondition(e, NEXT[0], NEXT[1], NEXT[2], NEXT[3]))
                    if (currentPanel < maxPanels) {
                        currentPanel++;
                        repaint();
                    }
                if (checkCondition(e, MENU[0], MENU[1], MENU[2], MENU[3]))
                    cl.show(mainPanel, "MAIN_MENU");
                if (checkCondition(e, EXIT[0], EXIT[1], EXIT[2], EXIT[3])) {
                    GameData.getData().saveUsers();
                    System.exit(0);
                }
            }
        });
    }

    private void initParam() {
        backgroundAddress = properties.getProperty("backgroundAddress");
        nextBtnAddress = properties.getProperty("nextBtnAddress");
        previousBtnAddress = properties.getProperty("previousBtnAddress");
        initBtnCor();
    }

    private void initBtnCor() {
        setCor(properties, "EXIT", EXIT);
        setCor(properties, "MENU", MENU);
        setCor(properties, "NEXT", NEXT);
        setCor(properties, "PREVIOUS", PREVIOUS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image background = ImageIO.read(new File(backgroundAddress));
            Image nextBtn = ImageIO.read(new File(nextBtnAddress));
            Image previousBtn = ImageIO.read(new File(previousBtnAddress));
            g.drawImage(background, 0, 0, null);
            if (currentPanel != maxPanels)
                g.drawImage(nextBtn, NEXT[0], NEXT[1], null);
            if (currentPanel != 0)
                g.drawImage(previousBtn, PREVIOUS[0], PREVIOUS[1], null);
        } catch (Exception e) {
        }
        paintDecks(status.getDecks(), g, currentPanel);
    }

    private void paintDecks(ArrayList<Deck> decks, Graphics g, int currentPanel) {
        int cardInPanel = 2, temp = cardInPanel * currentPanel, gap = 0;
        int DECK_START_X = 155, DECK_START_Y = 70, GAP = 230;
        for (int i = temp; i < temp + 2; i++)
            if (decks.size() > i) {
                decks.get(i).paint1(g, DECK_START_X, DECK_START_Y + gap * GAP);
                gap++;
            }
    }
}
