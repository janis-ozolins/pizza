package hashcode.pizza;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author @jozolins
 */
public class PizzaCutterRun {

    @Test
    public void simple() throws Exception {
        System.out.println(new PizzaCutter("src/main/resources/simple").toString());
    }

    @Test
    public void small() throws Exception {
        System.out.println(new PizzaCutter("src/main/resources/small.in").toString());
    }

    @Test
    public void medium() throws Exception {
        System.out.println(new PizzaCutter("src/main/resources/medium.in").toString());
    }

    @Test
    public void big() throws Exception {
        System.out.println(new PizzaCutter("src/main/resources/big.in").toString());
    }
}