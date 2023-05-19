public class SREG {
    boolean[] arr;

    public SREG() {
        arr = new boolean[] { false, false, false, false, false, false, false, false };// 0 0 0 c v n s z
    }

    public boolean getC() {
        return arr[3];
    }

    public boolean getV() {
        return arr[4];
    }

    public boolean getN() {
        return arr[5];
    }

    public boolean getS() {
        return arr[6];
    }

    public boolean getZ() {
        return arr[7];
    }

    public void setC(boolean value) {
        arr[3] = value;
    }

    public void setV(boolean value) {
        arr[4] = value;
    }

    public void setN(boolean value) {
        arr[5] = value;
    }

    public void setS(boolean value) {
        arr[6] = value;
    }

    public void setZ(boolean value) {
        arr[7] = value;
    }

    public void updateSREG(byte data1, byte data2, byte result, char operator) {
        switch (operator) {
            case '+':
                int temp1 = data1 & 0xFF;
                int temp2 = data2 & 0xFF;
                temp1 = temp1 + temp2;
                setC((temp1 & 0x100) != 0);

                updateV(data1, data2, result);
                setN(result < 0);
                setS(getN() ^ getV());
                setZ(result == 0);
                break;
            case '-':
                updateV(data1, data2, result);
                setN(result < 0);
                setS(getN() ^ getV());
                setZ(result == 0);
                break;
            // case '*':
            // setN(result < 0);
            // setZ(result == 0);
            // break;
            // case '&':
            // setN(result < 0);
            // setZ(result == 0);
            // break;
            // case '|':
            // setN(result < 0);
            // setZ(result == 0);
            // break;
            // case '<':
            // setN(result < 0);
            // setZ(result == 0);
            // break;
            default: // '>'
                setN(result < 0);
                setZ(result == 0);
                break;
        }
        toString(arr);
    }

    public void toString(boolean[] arr) {
        for (Boolean b : arr) {
            System.out.print(b + " ");
        }
        System.out.println();
    }

    private void updateV(byte data1, byte data2, byte result) {
        if (data1 > 0 && data2 > 0) {
            setV(result < 0);
        } else if (data1 < 0 && data2 < 0) {
            setV(result > 0);
        }
    }

}
