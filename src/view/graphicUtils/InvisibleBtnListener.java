package view.graphicUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class InvisibleBtnListener implements MouseListener {

    protected boolean checkCondition(MouseEvent e, int startX, int startY, int finalX, int finalY){
        return e.getY() >= startY && e.getY() <= finalY && e.getX() >= startX && e.getX() <= finalX;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
