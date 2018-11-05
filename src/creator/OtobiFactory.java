package creator;

import product.Chair;
import product.OtobiChair;
import product.OtobiTable;
import product.Table;

public class OtobiFactory implements FurnitureFactory {

    public Chair createChair() {
        return new OtobiChair();
    }

    public Table createTable() {
        return new OtobiTable();
    }
}
