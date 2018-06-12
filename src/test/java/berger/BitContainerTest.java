package berger;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BitContainerTest {


    private final int containerSize = 3;
    private final int containerSize_2 = 10;
    private final int badIndex = 9999;
    private final int invalidSize = -42;

    @Test
    public void length() throws Exception {

        BitContainer bitContainer = new BitContainerTestable(containerSize_2);
        assertEquals(containerSize_2, bitContainer.length());

    }

    @Test
    public void lengthWillReturnZeroWhenGivenInvalidSize() throws Exception {

        BitContainer bitContainer = new BitContainerTestable(invalidSize);
        assertEquals(0, bitContainer.length());

    }


    @Test
    public void toListWillReturnZerosForEmptyContainer() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        List<Boolean> list = bitContainer.toList();
        assertEquals(containerSize, list.size());

        List<Boolean> expected_list = Arrays.asList(false, false, false);
        assertEquals(expected_list, list);

    }

    @Test
    public void flipBitZeroToOne() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);

        assertTrue(bitContainer.flipBit(0));
        List<Boolean> expected_list = Arrays.asList(true, false, false);

        List<Boolean> list = bitContainer.toList();
        assertEquals(expected_list, list);

    }

    public void flipBitFromOneToZero() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        assertTrue(bitContainer.flipBit(0));
        assertTrue(bitContainer.flipBit(1));
        assertTrue(bitContainer.flipBit(2));


        assertTrue(bitContainer.flipBit(0));
        List<Boolean> expected_list = Arrays.asList(false, true, true);

        List<Boolean> list = bitContainer.toList();
        assertEquals(expected_list, list);

    }

    @Test
    public void flipBitShouldFailWhenGivenWrongIndex() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        assertFalse(bitContainer.flipBit(badIndex));
    }

    @Test
    public void setBit() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);

        assertTrue(bitContainer.setBit(1));
        List<Boolean> expected_list = Arrays.asList(false, true, false);

        List<Boolean> list = bitContainer.toList();
        assertEquals(expected_list, list);

    }

    @Test
    public void setBitShouldFailWhenGivenWrongIndex() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        assertFalse(bitContainer.setBit(badIndex));
    }

    @Test
    public void setBitShouldChangeOnlyOnce() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);

        assertTrue(bitContainer.setBit(1));
        assertTrue(bitContainer.setBit(1));

        List<Boolean> expected_list = Arrays.asList(false, true, false);

        List<Boolean> list = bitContainer.toList();
        assertEquals(expected_list, list);

    }

    @Test
    public void clearBit() throws Exception {

        BitContainer bitContainer = new BitContainerTestable(containerSize);
        assertTrue(bitContainer.setBit(0));
        assertTrue(bitContainer.setBit(1));
        assertTrue(bitContainer.setBit(2));

        assertTrue(bitContainer.clearBit(1));

        List<Boolean> expected_list = Arrays.asList(true, false, true);
        List<Boolean> list = bitContainer.toList();
        assertEquals(expected_list, list);

    }

    @Test
    public void clearBitShouldFailWhenGivenWrongIndex() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        assertFalse(bitContainer.clearBit(badIndex));
    }

    @Test
    public void getBit() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        assertTrue(bitContainer.setBit(0));
        assertTrue(bitContainer.setBit(2));

        assertTrue(bitContainer.getBit(0));
        assertFalse(bitContainer.getBit(1));
        assertTrue(bitContainer.getBit(2));
    }

    @Test
    public void getBitReturnsNullOnInvalidIndex() throws Exception {
        BitContainer bitContainer = new BitContainerTestable(containerSize);
        int kErrorIndex = 99999;
        assertNull(bitContainer.getBit(kErrorIndex));

    }

    class BitContainerTestable extends BitContainer {
        BitContainerTestable(int size) {
            super(size);
        }
    }

}