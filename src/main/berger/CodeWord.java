package berger;

import java.util.BitSet;

public class CodeWord
{
    public CodeWord( byte[] byte_array){

        this.bitSet=BitSet.valueOf(byte_array);
    }

    public int length(){
        return bitSet.length();
    }

    public BitSet getBitSet(){
        return this.bitSet;
    }


    private  BitSet convert(long value) {

        BitSet bitSet=BitSet.valueOf(new long[]{value});
        return bitSet;
    }

    private long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }


    private BitSet bitSet;

}
