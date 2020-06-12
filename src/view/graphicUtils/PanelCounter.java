package view.graphicUtils;

public interface PanelCounter {
    default int getPanelsNumber(int cardsNumber, int columns, int rows) {
        if (cardsNumber == 0)
            return 0;
        int cardInPanel = columns * rows, res = cardsNumber / cardInPanel;
        return cardsNumber % cardInPanel == 0 ? res - 1 : res;
    }
}
