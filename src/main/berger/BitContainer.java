package berger;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

abstract class BitContainer implements BitContainerInterface {

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

    @Override
    public List<Boolean> toList() {
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < size_; ++i) {
            list.add(bitContainer_.get(i));
        }
        return list;
    }

    private boolean isIndexValid(int index) {
        return (index >= 0 && index < size_);
    }

}
