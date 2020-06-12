package view.graphicUtils;

import utils.Config;
import utils.ConfigLoader;

import javax.swing.*;

public abstract class MFrame extends JFrame {
    protected String title;
    protected int WIDTH, HEIGHT,
            defaultCloseOperation;
    protected boolean resizable;
    private Config properties = ConfigLoader.getLoader().getFrameConfig();

    protected MFrame(){
        initParams();
        init();
    }

    private void init() {
        setTitle(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(defaultCloseOperation);
        setResizable(resizable);
        setLocationRelativeTo(null);
    }

    private void initParams() {
        title = properties.getProperty("title");
        WIDTH = properties.getNumber("WIDTH");
        HEIGHT = properties.getNumber("HEIGHT");
        defaultCloseOperation = properties.getNumber("defaultCloseOperation");
        resizable = properties.getBoolean("resizable");
    }

}
