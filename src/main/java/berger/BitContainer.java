package berger;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@JsonSerialize(using = BitContainerSerializer.class)
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

    public BitContainer(BitContainer container) {
        this.bitContainer_ = new BitSet();
        this.size_ = container.size_;

        BitSet bitSetToCopy = container.bitContainer_;
        for (int i = bitSetToCopy.nextSetBit(0); i != -1; i = bitSetToCopy.nextSetBit(i + 1)) {
            this.bitContainer_.set(i);
        }
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
    public Boolean getBit(int index) {
        if (isIndexValid(index)) {
            return bitContainer_.get(index);
        } else
            return null;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BitContainer{");
        sb.append("size= ").append(size_);
        sb.append(" ,bitContainer= ");
        sb.append("[");
        toList().forEach((e) -> {
            sb.append(e ? "1" : "0");
        });
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

}
