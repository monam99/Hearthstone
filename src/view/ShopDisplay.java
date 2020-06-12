package view;

import controller.Shop;
import model.dataModel.card.Card;
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
import java.util.ArrayList;
import static view.MainFrame.cl;
import static view.MainFrame.mainPanel;

public class ShopDisplay extends JPanel implements PanelLoader, BtnCorLoader {

    private Config properties;
    private int[] EXIT = new int[4],
            MENU = new int[4],
            SWITCH = new int[4],
            NEXT = new int[4],
            PREVIOUS = new int[4];
    private int CARDS_START_X, CARDS_START_Y, CARDS_GAP,
            COLUMN, ROW, CARD_WIDTH, CARD_HEIGHT;
    private String[] backgroundAddress = new String[2];
    private Shop shop;

    public ShopDisplay() {
        properties = new Config();
        loadProperties(properties, "SHOP");
        initParam();
        shop = new Shop();
        setListeners();
    }

    private void setListeners() {
        setMainListener();
        setVectorListener();
        setDealingListener();
    }

    private void setDealingListener() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e,CARDS_START_X,CARDS_START_Y,
                        CARDS_START_X + 3*CARD_WIDTH + 2* CARDS_GAP ,
                        CARDS_START_Y + 2*CARD_HEIGHT + CARDS_GAP))
                    for (int i = 0; i < 3; i++)
                        if (e.getX() >= CARDS_START_X + i*(CARD_WIDTH + CARDS_GAP) &&
                                e.getX() <= CARDS_START_X + i*(CARD_WIDTH + CARDS_GAP) + CARD_WIDTH){
                         if (e.getY() >= CARDS_START_Y && e.getY() <= CARDS_START_Y + CARD_HEIGHT){
                             int index = shop.getCurrentPanel()[shop.getState()] * (COLUMN * ROW) + i;
                             checkBuyingCondition(index);
                             shop.DealCard(index);
                         }
                         if (e.getY() >= CARDS_START_Y + CARD_HEIGHT + CARDS_GAP && e.getY() <= CARDS_START_Y + 2*CARD_HEIGHT + CARDS_GAP){
                             int index = shop.getCurrentPanel()[shop.getState()] *(COLUMN*ROW) + i + 3;
                             checkBuyingCondition(index);
                             shop.DealCard(index);
                         }
                         update();
                        }
            }
        });
    }

    private void checkBuyingCondition(int index) {
        if (shop.getState() == 0)
            if (!shop.canBuyCard(shop.getCardsToDeal()[0].get(index),GameData.getData().getCurrentUser().getWallet()))
                JOptionPane.showMessageDialog(this,
                        "you don't have enough coin to buy this card.",
                        "ERROR",
                        JOptionPane.PLAIN_MESSAGE);
    }

    private void setVectorListener() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, NEXT[0], NEXT[1], NEXT[2], NEXT[3]))
                    if (shop.getCurrentPanel()[shop.getState()] < shop.getMaxPanels()[shop.getState()]) {
                        shop.setCurrentPanelIndex(shop.getState(), shop.getCurrentPanel()[shop.getState()] + 1);
                        repaint();
                    }
                if (checkCondition(e, PREVIOUS[0], PREVIOUS[1], PREVIOUS[2], PREVIOUS[3]))
                    if (shop.getCurrentPanel()[shop.getState()] > 0) {
                        shop.setCurrentPanelIndex(shop.getState(), shop.getCurrentPanel()[shop.getState()] - 1);
                        repaint();
                    }
            }
        });
    }

    private void setMainListener() {
        addMouseListener(new InvisibleBtnListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkCondition(e, EXIT[0], EXIT[1], EXIT[2], EXIT[3])) {
                    GameData.getData().saveUsers();
                    System.exit(0);
                }
                if (checkCondition(e, MENU[0], MENU[1], MENU[2], MENU[3]))
                    cl.show(mainPanel, "MAIN_MENU");
                if (checkCondition(e, SWITCH[0], SWITCH[1], SWITCH[2], SWITCH[3])) {
                    shop.setState(shop.getState() == 0 ? 1 : 0);
                    repaint();
                }
            }
        });
    }

    private void initParam() {
        backgroundAddress[0] = properties.getProperty("background1");
        backgroundAddress[1] = properties.getProperty("background2");
        initBtnCor();
        CARDS_START_X = properties.getNumber("CARDS_START_X");
        CARDS_START_Y = properties.getNumber("CARDS_START_Y");
        CARDS_GAP = properties.getNumber("CARDS_GAP");
        COLUMN = properties.getNumber("COLUMN");
        ROW = properties.getNumber("ROW");
        CARD_WIDTH = properties.getNumber("CARD_WIDTH");
        CARD_HEIGHT = properties.getNumber("CARD_HEIGHT");
    }

    private void initBtnCor() {
        setCor(properties, "EXIT", EXIT);
        setCor(properties, "MENU", MENU);
        setCor(properties, "SWITCH", SWITCH);
        setCor(properties, "NEXT", NEXT);
        setCor(properties, "PREVIOUS", PREVIOUS);
    }

    private void update(){
        shop.update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image background = ImageIO.read(new File(backgroundAddress[shop.getState()]));
            g.drawImage(background, 0, 0, null);
        } catch (Exception e) {
        }
        g.setColor(Color.WHITE);
        paintCardCollection(shop.getCardsToDeal()[shop.getState()], g, shop.getCurrentPanel()[shop.getState()]);
        g.drawString(""+ GameData.getData().getCurrentUser().getWallet(),770,592);
    }

    private void paintCardCollection(ArrayList<Card> collection, Graphics g, int currentPanel) {
        int cardInPanel = ROW * COLUMN, temp = cardInPanel * currentPanel, gap = 0;
        int priceHGap = 15, priceWGap = 20;
        for (int i = temp; i < temp + 3; i++) {
            if (collection.size() > i) {
                collection.get(i).paint(g, CARDS_START_X + gap * CARDS_GAP + gap * CARD_WIDTH, CARDS_START_Y);
                g.drawString("Price : " + collection.get(i).getPrice(),
                        CARDS_START_X + gap * CARDS_GAP + gap * CARD_WIDTH + priceWGap ,
                        CARDS_START_Y + CARD_HEIGHT + priceHGap);
                gap++;
            } else
                break;
        }
        gap = 0;
        for (int i = temp + 3; i < temp + 6; i++) {
            if (collection.size() > i) {
                collection.get(i).paint(g, CARDS_START_X + gap * CARDS_GAP + gap * CARD_WIDTH,
                        CARDS_START_Y + CARDS_GAP + CARD_HEIGHT);
                g.drawString("Price : " + collection.get(i).getPrice(),
                        CARDS_START_X + gap * CARDS_GAP + gap * CARD_WIDTH + priceWGap ,
                        CARDS_START_Y + CARDS_GAP + 2*CARD_HEIGHT + priceHGap);
                gap++;
            } else
                break;
        }
    }
}
