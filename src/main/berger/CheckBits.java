package berger;

import java.util.BitSet;

public class CheckBits extends BitContainer {

    public CheckBits(BitSet data) {
        super.bitContainer_ = new BitSet();
        super.size_ = 0;
        calculateCode(data);

    }

    private void calculateCode(BitSet data) {
        double log = Logarithm.log2(data.length());
        size_ = (int) Math.ceil(log);
        int cardinality = data.cardinality();

        bitContainer_.clear();
        for (int i = 0; i < size_; ++i) {
            boolean value = ((cardinality & (1 << i)) == 1);
            bitContainer_.set(i, value);
        }
    }
}
