package berger;

/* interface for basic bit switching related operations on given container */
public interface BitModifiableInterface {
    /* return length of used container */
    int length();

    /* flip 0 to 1 or 1 to 0 on given (index+1) bit.
    Returns false is addressing invalid index*/
    boolean flipBit(int index);

    /* set  (index+1) bit as 1. Returns false is addressing invalid index*/
    boolean setBit(int index);

    /* set  (index+1) bit as 0. Returns false is addressing invalid index*/
    boolean clearBit(int index);
}
