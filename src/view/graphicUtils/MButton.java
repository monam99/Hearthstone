package view.graphicUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class MButton {

    private String[] addresses;
    private Image btnImage;
    private int state;
    private int XCor, YCor;

    public MButton(String[] addresses, int XCor, int YCor) {
        this.addresses = addresses;
        this.XCor = XCor;
        this.YCor = YCor;
        this.state = 0;
    }

    public void paintMBtn(Graphics g){
        loadImage();
        g.drawImage(btnImage,XCor,YCor,null);
    }

    private void loadImage(){
        try {
            if (state == 0)
                btnImage = ImageIO.read(new File(addresses[0]));
            if (state == 1)
                btnImage = ImageIO.read(new File(addresses[1]));
            if (state == 2)
                btnImage = ImageIO.read(new File(addresses[2]));
        }catch (Exception e){}
    }

    public int getWidth() {
        loadImage();
        return btnImage.getWidth(null);
    }

    public int getHeight(){
        loadImage();
        return btnImage.getHeight(null);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getXCor() {
        return XCor;
    }

    public int getYCor() {
        return YCor;
    }
}
