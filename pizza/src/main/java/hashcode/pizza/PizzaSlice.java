package hashcode.pizza;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * author @jozolins
 */
public class PizzaSlice {
    final UUID id;
    final int r1;
    final int c1;
    final int r2;
    final int c2;
    final int tomatoes;
    final int mushrooms;
    final int life;

    final boolean valid;

    public PizzaSlice(UUID id,
                      int r1,
                      int c1,
                      int r2,
                      int c2,
                      int tomatoes,
                      int mushrooms,
                      int life,
                      boolean valid) {
        this.id = id;
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.life = life;
        this.tomatoes = tomatoes;
        this.mushrooms = mushrooms;
        this.valid = valid;
    }

    public Integer size()
    {
        return (c2 - c1 + 1) * (r2 - r1 + 1);
    }

    public boolean isValid() {
        return valid;
    }

    public List<Point> getRow(CardinalDirection direction) {
        switch (direction)
        {
            case NORTH:
                return IntStream.range(c1, c2 + 1)
                        .mapToObj(i -> new Point(r1 - 1, i))
                        .collect(Collectors.toList());
            case EAST:
                return IntStream.range(r1, r2 + 1)
                        .mapToObj(i -> new Point(i, c2 + 1))
                        .collect(Collectors.toList());
            case SOUTH:
                return IntStream.range(c1, c2 + 1)
                        .mapToObj(i -> new Point(r2 + 1, i))
                        .collect(Collectors.toList());
            case WEST:
                return IntStream.range(r1, r2 + 1)
                        .mapToObj(i -> new Point(i, c1 - 1))
                        .collect(Collectors.toList());
            default: throw new IllegalStateException("This should no be possible");
        }
    }

    public PizzaSlice stuck() {
        return new PizzaSlice(id, r1, c1, r2, c2, tomatoes, mushrooms, life - 1, valid);
    }

    @Override
    public String toString() {
        return r1 + " " + c1 + " " + r2 + " " + c2;
    }
}
