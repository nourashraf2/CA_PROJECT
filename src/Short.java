public class Short {
   private short num;

    public Short(short num) {
        this.num = num;
    }

    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return num+"";
    }
}
