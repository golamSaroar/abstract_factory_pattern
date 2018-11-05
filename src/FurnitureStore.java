import creator.FurnitureFactory;
import product.Chair;
import product.Table;

public abstract class FurnitureStore {

    public void orderFurniture(){
        FurnitureFactory furnitureFactory = selectFactory();

        Chair chair = furnitureFactory.createChair();
        chair.deliver();

        Table table = furnitureFactory.createTable();
        table.deliver();
    }

    abstract FurnitureFactory selectFactory();
}
