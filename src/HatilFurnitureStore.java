import creator.FurnitureFactory;
import creator.HatilFactory;

public class HatilFurnitureStore extends FurnitureStore {

    public FurnitureFactory selectFactory() {
        return new HatilFactory();
    }
}
