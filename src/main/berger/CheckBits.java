package berger;

import java.util.BitSet;
import java.util.List;

public class CheckBits extends BitContainer {

    public CheckBits(List<Boolean> codeWordList) {
        super.size_ = 0;
        calculateCode(codeWordList);

    }

    private void calculateCode(List<Boolean> codeWordList) {
        double log = Logarithm.log2(codeWordList.size());
        size_ = (int) Math.ceil(log);
        bitContainer_.clear();

        Long countOfOnes = countOnes(codeWordList);
        if (countOfOnes != null) {
            bitContainer_ = convertLongToBitSet(countOfOnes);
        }
    }

    private Long countOnes(List<Boolean> list) {
        try {
            long count = 0;
            for (Boolean value : list)
                if (value)
                    ++count;
            return count;
        } catch (NullPointerException ex) {
            System.out.println(ex);
            /* null value ecnountered */
            return null;
        }
    }


    private BitSet convertLongToBitSet(long value) {
        BitSet bitset = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bitset.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bitset;
    }

}
