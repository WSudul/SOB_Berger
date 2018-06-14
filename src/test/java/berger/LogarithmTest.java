package berger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static berger.Logarithm.log2;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LogarithmTest {

    private double input;
    private double expected;

    public LogarithmTest(double input, double expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1.0, 0.00},
                {2.0, 1.00},
                {4.0, 2.00},
                {8.0, 3.00},
                {10.0, 3.322},
                {16.0, 4.00},
                {32.0, 5.00},
                {40.0, 5.322},
                {48.0, 5.5849},
                {56.0, 5.807},
                {64.0, 6.00},
                {128.0, 7.00}}
        );
    }

    @Parameterized.Parameters


    @Test
    public void LogarithmBase2ShouldReturnValidValue() throws Exception {
        final double kEpsilon = 0.001;
        assertEquals(expected, log2(input), kEpsilon);
    }

}