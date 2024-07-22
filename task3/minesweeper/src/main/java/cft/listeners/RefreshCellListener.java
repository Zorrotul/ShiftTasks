package cft.listeners;

import cft.common.CellContent;
import cft.common.CellState;

public interface RefreshCellListener {
    void onRefreshCell(int x, int y, CellState cellState, CellContent content);
}
