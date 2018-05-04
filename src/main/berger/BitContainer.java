package berger;

import java.util.BitSet;

public abstract class BitContainer implements BitModifiableInterface {

    protected BitSet bitContainer_;
    protected int size_;

    public BitContainer() {
        this(0);
    }

    public BitContainer(int size) {
        size_ = size > 0 ? size : 0;
        bitContainer_ = new BitSet(size_);

    }

    @Override
    public int length() {
        return size_;
    }

    @Override
    public boolean flipBit(int index) {

        if (isIndexValid(index)) {
            bitContainer_.flip(index);
            return true;
        } else
            return false;
    }

    @Override
    public boolean setBit(int index) {
        if (isIndexValid(index)) {
            bitContainer_.set(index);
            return true;
        } else
            return false;
    }

    @Override
    public boolean clearBit(int index) {
        if (isIndexValid(index)) {
            bitContainer_.clear(index);
            return true;
        } else
            return false;
    }

    //#TODO remove this!
    public BitSet getBitSet() {
        return this.bitContainer_;
    }

    private boolean isIndexValid(int index) {
        return (index > 0 && index < bitContainer_.length());
    }

}
