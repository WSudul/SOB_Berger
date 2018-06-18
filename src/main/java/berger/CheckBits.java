package berger;

import java.util.BitSet;
import java.util.List;

class CheckBits extends BitContainer {

    public CheckBits(List<Boolean> codeWordList) {
        super.size_ = 0;
        calculateCode(codeWordList);

    }

    public CheckBits(CheckBits checkBits) {
        super(checkBits);
    }

    private void calculateCode(List<Boolean> codeWordList) {
        double log = Logarithm.log2(codeWordList.size() + 1);
        size_ = (int) Math.ceil(log);
        bitContainer_.clear();

        Long countOfOnes = countOnes(codeWordList);
        if (countOfOnes != null && size_ != 0) {
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
            System.out.println("EXCEPTION CAUGHT: " + ex.getMessage());
            /* null value ecnountered */
            return null;
        }
    }

    private BitSet convertLongToBitSet(long value) {
        BitSet bitset = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bitset.set(size_ - 1 - index);
            }
            ++index;
            value = value >>> 1;
        }
        bitset.flip(0, size_);

        return bitset;
    }
}
