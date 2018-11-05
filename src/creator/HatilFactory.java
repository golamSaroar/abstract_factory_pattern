package creator;

import product.Chair;
import product.HatilChair;
import product.HatilTable;
import product.Table;

public class HatilFactory implements FurnitureFactory {

    public Chair createChair() {
        return new HatilChair();
    }

    public Table createTable() {
        return new HatilTable();
    }
}
