package hashcode.pizza;

import java.util.Collection;
import java.util.List;

/**
 * author @jozolins
 */
public class PizzaCutter {
    private final PizzaFile pizzaFile;
    private final Collection<PizzaSlice> calc;

    public PizzaCutter(String fileName) {
        this.pizzaFile = new PizzaFile(fileName);
        PizzaConfiguration config = pizzaFile.loadConfig();
        SliceCalculation calculation = new SliceCalculation(config);
        this.calc = calculation.calculate();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(calc.size()).append("\n");
        calc.forEach(slice -> str.append(slice.toString()).append("\n"));
        return str.toString();
    }

    public int size() {
        return calc.size();
    }
}
