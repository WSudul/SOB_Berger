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

    @Test
    public void lengthShouldReturnValidValue() throws Exception {
        List<Boolean> inputList_8bits = Arrays.asList(new Boolean[8]);
        List<Boolean> inputList_15bits = Arrays.asList(new Boolean[15]);
        List<Boolean> inputList_16bits = Arrays.asList(new Boolean[16]);
        List<Boolean> inputList_31bits = Arrays.asList(new Boolean[31]);
        List<Boolean> inputList_32bits = Arrays.asList(new Boolean[32]);
        List<Boolean> inputList_63bits = Arrays.asList(new Boolean[63]);
        List<Boolean> inputList_64bits = Arrays.asList(new Boolean[64]);
        List<Boolean> inputList_127bits = Arrays.asList(new Boolean[127]);
        List<Boolean> inputList_128bits = Arrays.asList(new Boolean[128]);
        List<Boolean> inputList_255bits = Arrays.asList(new Boolean[255]);
        List<Boolean> inputList_256bits = Arrays.asList(new Boolean[256]);
        CheckBits checkBits;

        checkBits = new CheckBits(inputList_8bits);
        assertEquals(4, checkBits.length());
        checkBits = new CheckBits(inputList_15bits);
        assertEquals(4, checkBits.length());
        checkBits = new CheckBits(inputList_16bits);
        assertEquals(5, checkBits.length());
        checkBits = new CheckBits(inputList_31bits);
        assertEquals(5, checkBits.length());
        checkBits = new CheckBits(inputList_32bits);
        assertEquals(6, checkBits.length());
        checkBits = new CheckBits(inputList_63bits);
        assertEquals(6, checkBits.length());
        checkBits = new CheckBits(inputList_64bits);
        assertEquals(7, checkBits.length());
        checkBits = new CheckBits(inputList_127bits);
        assertEquals(7, checkBits.length());
        checkBits = new CheckBits(inputList_128bits);
        assertEquals(8, checkBits.length());
        checkBits = new CheckBits(inputList_255bits);
        assertEquals(8, checkBits.length());
        checkBits = new CheckBits(inputList_256bits);
        assertEquals(9, checkBits.length());


    }

}


