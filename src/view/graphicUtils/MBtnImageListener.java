package view.graphicUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MBtnImageListener implements MouseListener {
    private MButton button;

    protected boolean checkCondition(MouseEvent e, MButton btn) {
        return e.getY() >= btn.getYCor() && e.getY() <= btn.getYCor() + btn.getWidth() &&
                e.getX() >= btn.getXCor() && e.getX() <= btn.getXCor() + btn.getHeight();
    }

    public MBtnImageListener(MButton btn) {
        this.button = btn;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (checkCondition(e, button))
            button.setState(1);
    }

    public void mouseReleased(MouseEvent e) {
        if (checkCondition(e, button))
            button.setState(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
