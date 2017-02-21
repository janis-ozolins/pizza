package hashcode.pizza;

/**
 * author @jozolins
 */
public class PizzaConfiguration {
    final String[][] content;
    final int rows;
    final int columns;
    final int maxSlice;
    final int minIngredient;

    public PizzaConfiguration(String[][] content, int maxSlice, int minIngredient) {
        this.content = content;
        this.maxSlice = maxSlice;
        this.minIngredient = minIngredient;
        this.columns = content[0].length;
        this.rows = content.length;
    }
}
