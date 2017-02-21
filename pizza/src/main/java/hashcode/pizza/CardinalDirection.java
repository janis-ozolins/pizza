package hashcode.pizza;

/**
 * author @jozolins
 */
public enum CardinalDirection {
    NORTH(1),
    EAST(2),
    SOUTH(3),
    WEST(4);

    int i;

    CardinalDirection(int i) {
        this.i = i;
    }

    public static CardinalDirection get(int i)
    {
        switch (i)
        {
            case 1:return NORTH;
            case 2: return EAST;
            case 3: return SOUTH;
            case 4: return WEST;
            default: throw new IllegalStateException("Unknown cardinal direction: " + i);
        }
    }
}
