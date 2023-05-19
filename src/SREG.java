public class SREG {
    boolean[] arr;

    public SREG() {
        arr= new boolean[]{false, false,false,false,false,false,false,false};//0 0 0 c v n s z
    }

    public void setC(boolean value){
        arr[3]=value;

    }
    public void setV(boolean value){
        arr[4]=value;

    }
    public void setN(boolean value){
        arr[5]=value;

    }
    public void setS(boolean value){
        arr[6]=value;

    }
    public void setZ(boolean value){
        arr[7]=value;

    }

    public void setV(byte data1, byte data2, byte result, char operator) {
    /*
     * If 2 numbers are added, and they both have the same sign (both positive or both
negative), then overflow occurs (V = 1) if and only if the result has the opposite
sign. Overflow never occurs when adding operands with different signs.

· If 2 numbers are subtracted, and their signs are different, then overflow occurs (V
= 1) if and only if the result has the same sign as the subtrahend.

     */
    }



}
