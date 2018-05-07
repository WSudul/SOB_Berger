package berger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BergerCodeTest {

    private final int value1 = 47;
    private final int binaryRepresentationSize1 = 32;
    private final int checkBitsSize1 = 6;
    private BergerCode bergerCode;

    @Before
    public void setUp() throws Exception {
        bergerCode = new BergerCode(value1);
    }

    @Test
    public void getCodeWord() throws Exception {
        CodeWord codeWord = bergerCode.getCodeWord();
        assertNotNull(codeWord);
        assertEquals(binaryRepresentationSize1, codeWord.length());
    }

    @Test
    public void getCheckBits() throws Exception {
        CheckBits checkBits = bergerCode.getCheckBits();
        assertNotNull(checkBits);
        assertEquals(checkBitsSize1, checkBits.length());
    }


    @Test
    public void willConstructObjectWithArgumentTypeLong() throws Exception {
        BergerCode bergerCode_long;
        final long value = 47;
        final int binaryRepresentationSize = 64;
        final int checkBitsSize = 7;

        bergerCode_long = new BergerCode(value);

        assertEquals(binaryRepresentationSize, bergerCode_long.getCodeWord().length());
        assertEquals(checkBitsSize, bergerCode_long.getCheckBits().length());
    }

    @Test
    public void willConstructObjectWithArgumentTypeByteArray() throws Exception {
        BergerCode bergerCode_byteArray;
        final byte[] value = {0x20, 0x21, 0x2A};
        final int binaryRepresentationSize = 24;
        final int checkBitsSize = 5;

        bergerCode_byteArray = new BergerCode(value);

        assertEquals(binaryRepresentationSize, bergerCode_byteArray.getCodeWord().length());
        assertEquals(checkBitsSize, bergerCode_byteArray.getCheckBits().length());
    }

    @Test
    public void isErrorDetectedShouldReturnFalseForUnmodifiedObject() throws Exception {
        assertFalse(bergerCode.isErrorDetected());
    }

    @Test
    public void isErrorDetectedShouldReturnTrue_BidirectionalError() throws Exception {
        bergerCode.getCodeWord().setBit(0);
        bergerCode.getCodeWord().clearBit(1);

        assertTrue(bergerCode.isErrorDetected());
    }

    @Test
    public void isErrorDetectedShouldReturnTrue_ModifiedCheckBits() throws Exception {
        bergerCode.getCheckBits().flipBit(0);

        assertTrue(bergerCode.isErrorDetected());
    }

    @Test
    public void isErrorDetectedShouldReturnTrue_UnidirectionalErrors() throws Exception {
        bergerCode.getCodeWord().setBit(0);
        bergerCode.getCodeWord().setBit(1);
        bergerCode.getCodeWord().setBit(2);
        bergerCode.getCodeWord().setBit(3);
        bergerCode.getCodeWord().setBit(4);

        assertTrue(bergerCode.isErrorDetected());
    }


}