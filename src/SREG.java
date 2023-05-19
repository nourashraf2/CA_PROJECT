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



}
