package creator;

import product.Chair;
import product.Table;

public interface FurnitureFactory {
    Chair createChair();
    Table createTable();
}
