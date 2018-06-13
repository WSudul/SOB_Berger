package berger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.nio.ByteBuffer;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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

    public BergerCode(String string) {
        this(string.getBytes());
    }

    public BergerCode(ByteBuffer buffer){
        this(buffer.array());
    }

    public BergerCode(byte[] value){
        Initialize(value);
    }

    /*Copy ctor which creates deep copy of passed instance */
    public BergerCode(BergerCode bergerCode) {
        this.codeWord = new CodeWord(bergerCode.codeWord);
        this.checkBits = new CheckBits(bergerCode.checkBits);
    }


    public BitContainerInterface getCodeWord() {
        return codeWord;
    }

    public BitContainerInterface getCheckBits() {
        return checkBits;
    }

    public boolean isErrorDetected() {
        List<Boolean> codeWordList = codeWord.toList();
        long expectedOnesCount = decodeCheckBits(checkBits);
        long onesCount = 0;

        for (Boolean bit : codeWordList) {
            if (bit)
                ++onesCount;
        }
        return (expectedOnesCount != onesCount);
    }

    private long decodeCheckBits(CheckBits checkBits) {
        List<Boolean> checkBitsList = checkBits.toList();
        long highestValue = 1 << (checkBitsList.size() - 1);

        long result = 0;
        for (Boolean bit : checkBitsList) {
            if (!bit)
                result += highestValue;

            highestValue = highestValue >> 1;
        }
        return result;
    }

    private void Initialize(byte[] value) {
        this.codeWord = new CodeWord(value);
        this.checkBits = new CheckBits(codeWord.toList());
    }

    private CodeWord codeWord;
    private CheckBits checkBits;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BergerCode{");
        sb.append("codeWord= ").append(codeWord);
        sb.append(",checkBits= ").append(checkBits);
        sb.append("}");
        return sb.toString();
    }
}
