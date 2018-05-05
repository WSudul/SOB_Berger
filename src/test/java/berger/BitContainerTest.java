package berger;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BitContainerTest {

    @Test
    public void length() throws Exception {
        final int containerSize = 10;
        BitContainer bitContainer = new BitCointainerTestable(containerSize);
        assertEquals(containerSize, bitContainer.length());

    }

    @Test
    public void flipBit() throws Exception {
        final int containerSize = 3;
        BitContainer bitContainer = new BitCointainerTestable(containerSize);

        assertTrue(bitContainer.flipBit(0));
        List<Boolean> expected_list = Arrays.asList(true, false, false);

        List<Boolean> list = bitContainer.toList();
        assertEquals(expected_list, list);

    }

    @Test
    public void setBit() throws Exception {
    }

    @Test
    public void clearBit() throws Exception {
    }

    @Test
    public void toList() throws Exception {
    }

    class BitCointainerTestable extends BitContainer {
        BitCointainerTestable(int size) {
            super(size);
        }
    }

}