package hashcode.pizza;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author @jozolins
 */
public class SliceCalculation {
    final PizzaConfiguration config;
    final UUID[][] taken;
    final Map<UUID, PizzaSlice> slices;
    final BoardData board;

    public SliceCalculation(PizzaConfiguration config) {
        this.config = config;
        this.taken = new UUID[config.rows][config.columns];
        this.slices = new HashMap<>();
        this.board = calculateIngredients();
    }

    public Collection<PizzaSlice> calculate() {
        String pref = getPref();

        Random rand = new Random(System.currentTimeMillis());
        for (int r = 0 ; r < config.rows ; r++ ) {
            for (int c = 0; c < config.columns; c++) {
                if(config.content[r][c].equals(pref))
                {
                    if(rand.nextInt(1) == 0) {
                        UUID uuid = UUID.randomUUID();
                        slices.put(uuid, new PizzaSlice(uuid,
                                r, c, r, c,
                                pref.equals("T") ? 1 : 0,
                                pref.equals("M") ? 1 : 0,
                                3 + rand.nextInt(8),
                                false));
                        taken[r][c] = uuid;
                    }
                }
            }
        }

        boolean change = true;
        while(change) {
            int changeCount = 0;
            Iterator<PizzaSlice> it = slices.values().iterator();
            while (it.hasNext()){
                PizzaSlice slice = it.next();
                if (!slice.isValid()) {
                    int i = rand.nextInt(4) + 1;
                    CardinalDirection direction = CardinalDirection.get(i);
                    List<Point> row = slice.getRow(direction);
                    if(row.size() == 0) continue;

                    boolean taken = false;
                    for (Point point : row) {
                        if (point.c < 0 || point.c > config.columns - 1 ||
                                point.r < 0 || point.r > config.rows - 1 ||
                                this.taken[point.r][point.c] != null) {
                            taken = true;
                        }
                    }

                    if (!taken && (slice.size() + row.size() <= config.maxSlice)) {
                        int tomatoes = 0;
                        int mushrooms = 0;
                        for (Point point : row) {
                            this.taken[point.r][point.c] = slice.id;
                            if (this.config.content[point.r][point.c].equals("T")) {
                                tomatoes++;
                            } else {
                                mushrooms++;
                            }
                        }

                        int newTomatoes = slice.tomatoes + tomatoes;
                        int newMushrooms = slice.mushrooms + mushrooms;

                        boolean newSliceValid = newMushrooms >= config.minIngredient && newTomatoes >= config.minIngredient;

                        int newC1 = slice.c1, newC2 = slice.c2, newR1 = slice.r1, newR2 = slice.r2;
                        switch (direction)
                        {
                            case NORTH:
                                newR1--;
                                break;
                            case EAST:
                                newC2++;
                                break;
                            case SOUTH:
                                newR2++;
                                break;
                            case WEST:
                                newC1--;
                                break;
                        }


                        PizzaSlice newSlice = new PizzaSlice(
                                slice.id,
                                newR1 <= newR2 ? newR1 : newR2,
                                newC1 <= newC2 ? newC1 : newC2,
                                newR1 <= newR2 ? newR2 : newR1,
                                newC1 <= newC2 ? newC2 : newC1,
                                newTomatoes,
                                newMushrooms,
                                10 + rand.nextInt(8),
                                newSliceValid);


                        slices.put(slice.id, newSlice);
                        changeCount++;
                    } else {
                        if(slice.life >= 0) {
                            changeCount++;
                        } else {
                            if(!slice.isValid())
                                for( int r = slice.r1 ; r <= slice.r2 ; r++ ) {
                                    for( int c = slice.c1 ; c <= slice.c2 ; c++ ) {
                                        this.taken[r][c] = null;
                                    }
                                }
                            it.remove();
                            continue;
                        }
                        PizzaSlice newSlice = slice.stuck();
                        slices.put(slice.id, newSlice);
                    }
                }
            }

            if(changeCount == 0) change = false;
        }

        change = true;
        while(change) {
            int changeCount = 0;
            for (PizzaSlice slice : slices.values()) {
                if(slice.isValid() && slice.life > 0) {
                    int i = rand.nextInt(4) + 1;
                    CardinalDirection direction = CardinalDirection.get(i);
                    List<Point> row = slice.getRow(direction);
                    if(row.size() == 0) throw new IllegalStateException(direction.toString() + " " + slice.toString());

                    boolean taken = false;
                    for (Point point : row) {
                        if (point.c < 0 || point.c > config.columns - 1 ||
                            point.r < 0 || point.r > config.rows - 1 ||
                            this.taken[point.r][point.c] != null) {
                            taken = true;
                        }
                    }

                    if (!taken && (slice.size() + row.size() <= config.maxSlice)) {
                        int tomatoes = 0;
                        int mushrooms = 0;
                        for (Point point : row) {
                            this.taken[point.r][point.c] = slice.id;
                            if (this.config.content[point.r][point.c].equals("T")) {
                                tomatoes++;
                            } else {
                                mushrooms++;
                            }
                        }

                        int newTomatoes = slice.tomatoes + tomatoes;
                        int newMushrooms = slice.mushrooms + mushrooms;

                        boolean newSliceValid = newMushrooms >= config.minIngredient && newTomatoes >= config.minIngredient;

                        int newC1 = slice.c1, newC2 = slice.c2, newR1 = slice.r1, newR2 = slice.r2;
                        switch (direction)
                        {
                            case NORTH:
                                newR1--;
                                break;
                            case EAST:
                                newC2++;
                                break;
                            case SOUTH:
                                newR2++;
                                break;
                            case WEST:
                                newC1--;
                                break;
                        }

                        PizzaSlice newSlice = new PizzaSlice(
                                slice.id,
                                newR1 <= newR2 ? newR1 : newR2,
                                newC1 <= newC2 ? newC1 : newC2,
                                newR1 <= newR2 ? newR2 : newR1,
                                newC1 <= newC2 ? newC2 : newC1,
                                newTomatoes,
                                newMushrooms,
                                10,
                                newSliceValid);

                        slices.put(slice.id, newSlice);

                        changeCount++;
                    } else {
                        if(slice.life > 0) {
                            changeCount++;
                        } else {
                            continue;
                        }
                        PizzaSlice newSlice = slice.stuck();
                        slices.put(slice.id, newSlice);
                    }
                }
            }

            if(changeCount == 0) change = false;
        }

        OptionalInt max = slices.values()
                .stream()
                .mapToInt(PizzaSlice::size)
                .max();

        return slices.values()
                .stream()
                .filter(PizzaSlice::isValid)
                .collect(Collectors.toList());
    }

    private String getPref() {
        if(board.totalMushrooms > board.totalTomatoes) {
            return "T";
        } else {
            return "M";
        }
    }

    public BoardData calculateIngredients() {
        int totalTomatoes = 0;
        int totalMushrooms = 0;

        for(int r = 0 ; r < config.rows - 1 ; r++ ) {
            for(int c = 0 ; c < config.columns - 1 ; c++ ) {
                switch (config.content[r][c]) {
                    case "T":
                        totalTomatoes += 1;
                        break;
                    case "M":
                        totalMushrooms += 1;
                        break;
                    default:
                        throw new IllegalStateException("Unknown pizza ingredient: " + config.content[r][c]);
                }
            }
        }

        return new BoardData(totalTomatoes, totalMushrooms);
    }
}
