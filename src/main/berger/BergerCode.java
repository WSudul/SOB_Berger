package berger;

import java.nio.ByteBuffer;

public class BergerCode {


    public BergerCode(int value){
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);
        Initialize(buffer.array());
    }

    public BergerCode(long value){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        Initialize(buffer.array());

    }

    public BergerCode(ByteBuffer buffer){
        this(buffer.array());
    }

    public BergerCode(byte[] value){
        Initialize(value);
    }

    private void Initialize(byte[] value){
        this.codeWord=new CodeWord(value);
        this.checkBits = new CheckBits(codeWord.toList());
    }



    private CodeWord codeWord;
    private CheckBits checkBits;
}
