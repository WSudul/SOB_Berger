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
    public void toListShouldReturnValidList_8BitInput() throws Exception {
        List<Boolean> inputList = Arrays.asList(
                true, true, false, false,
                false, true, true, false);
        CheckBits checkBits = new CheckBits(inputList);

        final int expectedSize = 4;
        List<Boolean> expected_list = Arrays.asList(
                true, false, true, true); //binary 4 complemented 1011

        assertEquals(expectedSize, checkBits.length());
        assertEquals(expected_list, checkBits.toList());
    }

    @Test
    public void toListShouldReturnValidListForLargeInput_7BitInput() throws Exception {
        List<Boolean> inputList = Arrays.asList(
                true, false, false,
                false, true, true, false);

        CheckBits checkBits = new CheckBits(inputList);

        final int expectedSize = 3;
        List<Boolean> expected_list = Arrays.asList(
                true, false, false); //binary 3 complemented 100

        assertEquals(expectedSize, checkBits.length());
        assertEquals(expected_list, checkBits.toList());
    }

    @Test
    public void toListShouldReturnValidListForLargeInput_32BitInput() throws Exception {
        List<Boolean> inputList_32bits = Arrays.asList(
                true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, false,
                false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false
        ); //15 bits set to true


        CheckBits checkBits = new CheckBits(inputList_32bits);

        final int expectedSize = 6;
        List<Boolean> expected_list = Arrays.asList(
                true, true, false, false, false, false); //binary 15 complemented 110000

        assertEquals(expectedSize, checkBits.length());
        assertEquals(expected_list, checkBits.toList());
    }

}