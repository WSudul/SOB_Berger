package berger;

import java.util.BitSet;

public class CheckBits  {

    public CheckBits(BitSet bitset){
        calculateCode(bitset);

    }

    public BitSet getCode(){
        return this.code;
    }

    private void calculateCode(BitSet data){
        double log=Logarithm.log2(data.length());
        int code_length= (int) Math.ceil(log);
        int cardinality=data.cardinality();

        code.clear();
        for(int i=0; i<code_length;++i){
            code.set(i,cardinality & (1 << i));
        }
    }

    private BitSet code;

}
