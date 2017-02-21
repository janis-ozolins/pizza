package hashcode.pizza;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * author @jozolins
 */
public class PizzaFileTest {

    @Test
    public void simpleFileShouldBeLoadedCorretly()
    {
        PizzaFile pizzaFile = new PizzaFile("src/main/resources/simple");
        PizzaConfiguration config = pizzaFile.loadConfig();
        Assert.assertTrue("Simple configuration file configuration has max 6 slices!", config.maxSlice == 6);
    }
}