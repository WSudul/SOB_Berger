package berger;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CheckBitsTest {


    @Test
    public void lengthShouldReturnZeroForEmptyList() throws Exception {
        CheckBits checkBits = new CheckBits(Arrays.asList());

        assertEquals(0, checkBits.length());
    }

    @Test
    public void lengthShouldReturnValidValueForNonEmptyList() throws Exception {
        List<Boolean> inputList = Arrays.asList(
                true, true, false, false,
                false, true, true, false);
        CheckBits checkBits = new CheckBits(inputList);

        final int expectedLength = 4;
        assertEquals(expectedLength, checkBits.length());
    }


    @Test
    public void toListShouldReturnValidList() throws Exception {
        List<Boolean> inputList = Arrays.asList(
                true, true, false, false,
                false, true, true, false);
        CheckBits checkBits = new CheckBits(inputList);

        List<Boolean> expected_list = Arrays.asList(
                false, true, false, false); //binary 4 in 2s complement 0b0011

        assertEquals(expected_list, checkBits.toList());

    }

}