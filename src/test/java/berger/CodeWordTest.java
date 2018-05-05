package berger;

import org.junit.Test;

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
        final byte[] multiple_bytes = {0x2A}; // {42} - 0b00101010
        final int expected_length = 8;
        // There are only 6 bits needed to represent this.
        // The container should keep also trailing zeros
        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());

    }


    @Test
    public void length_single_element_max_value() throws Exception {
        final byte[] multiple_bytes = {0x7F}; // {127} - 0b1111111
        final int expected_length = 8;

        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());
    }

    @Test
    public void length_multiple_elements() throws Exception {
        final byte[] multiple_bytes = {0x7F, 0x40, 0x05}; // {127,64,5}
        final int expected_length = 24; //8+8+8
        CodeWord codeWord = new CodeWord(multiple_bytes);
        assertEquals(expected_length, codeWord.length());
    }


}