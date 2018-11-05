import creator.FurnitureFactory;
import creator.OtobiFactory;

public class OtobiFurnitureStore extends FurnitureStore {

    public FurnitureFactory selectFactory() {
        return new OtobiFactory();
    }
}
