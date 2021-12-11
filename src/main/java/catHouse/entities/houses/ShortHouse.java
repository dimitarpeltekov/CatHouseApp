package catHouse.entities.houses;

public class ShortHouse extends BaseHouse{
    private final static int CAPACITY = 15;

    public ShortHouse(String name) {
        super(name, CAPACITY);
    }
}
