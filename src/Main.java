public class Main {

    public static void main(String[] args) {

        FurnitureStore hatil = new HatilFurnitureStore();
        hatil.orderFurniture();

        FurnitureStore otobi = new OtobiFurnitureStore();
        otobi.orderFurniture();
    }
}
