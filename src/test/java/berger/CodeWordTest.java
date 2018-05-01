package berger;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;

public class CodeWordTest {

    @Test
    public void length_empty_array() throws Exception {
        final byte[] multiple_bytes = {};
        final int expected_length = 0;

        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());
    }

    @Test
    public void length_single_element() throws Exception {
        final byte[] multiple_bytes = {0x2A}; // {42} - 0b101010
        final int expected_length = 6; //there are only 6 bits needed to represent this

        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());
    }


    @Test
    public void length_single_element_max_value() throws Exception {
        final byte[] multiple_bytes = {0x7F}; // {127} - 0b1111111
        final int expected_length = 7;

        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());
    }

    @Test
    public void length_multiple_elements() throws Exception {
        final byte[] multiple_bytes = {0x7F, 0x40, 0x05}; // {127,64,5}
        final int expected_length = 19; //8+8+3
        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());
    }

    @Test
    public void getBitSet() throws Exception {
    }

}