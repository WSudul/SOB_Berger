package berger;

import java.util.BitSet;
import java.util.List;

class CodeWord extends BitContainer {


    public CodeWord(byte[] byte_array) {
        super(byte_array.length * Byte.SIZE);
        bitContainer_ = BitSet.valueOf(byte_array);
    }

    public CodeWord(List<Boolean> booleans) {
        super(booleans.size());
        int index = 0;
        for (Boolean booleanValue : booleans) {
            if (booleanValue)
                bitContainer_.set(index);
        }
    }


    public CodeWord(CodeWord codeWord) {
        super(codeWord);
    }


    private BitSet convert(long value) {

        BitSet bitSet = BitSet.valueOf(new long[]{value});
        return bitSet;
    }

    private long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }
    
}
