public class SREG {
    int[] arr;

    public SREG() {
        arr = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };// 0 0 0 c v n s z
    }

    public int getC() {
        return arr[3];
    }

    public int getV() {
        return arr[4];
    }

    public int getN() {
        return arr[5];
    }

    public int getS() {
        return arr[6];
    }

    public int getZ() {
        return arr[7];
    }

    public void setC(boolean t) {
        if (t) {
            arr[3] = 1;
        } else
            arr[3] = 0;
    }

    public void setV(boolean t) {
        if (t) {
            arr[4] = 1;
        } else
            arr[4] = 0;
    }

    public void setN(boolean t) {
        if (t) {
            arr[5] = 1;
        } else
            arr[5] = 0;
    }

    public void setS(boolean t) {
        if (t) {
            arr[6] = 1;
        } else
            arr[6] = 0;
    }

    public void setZ(boolean t) {
        if (t) {
            arr[7] = 1;
        } else
            arr[7] = 0;
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
                int n = getN();
                int v = getV();
                setS((n == 1 && v == 0) || (n == 0 && v == 1));
                setZ(result == 0);
                break;
            case '-':
                updateV(data1, data2, result);
                setN(result < 0);
                n = getN();
                v = getV();
                setS((n == 1 && v == 0) || (n == 0 && v == 1));
                setZ(result == 0);
                break;
            default:
                setN(result < 0);
                setZ(result == 0);
                break;
        }
        toString(arr);
    }

    public static void toString(int[] arr) {
        int i = 0;
        System.out.print("SREG: ");
        for (int b : arr) {
            switch (i) {
                case 3:
                    System.out.print("c: ");
                    break;
                case 4:
                    System.out.print("v: ");
                    break;
                case 5:
                    System.out.print("n: ");
                    break;
                case 6:
                    System.out.print("s: ");
                    break;
                case 7:
                    System.out.print("z: ");
                    break;
                default:
                    System.out.print("_: ");
                    break;
            }
            System.out.print(b + " ");
            i++;
        }
        System.out.println("\n");
    }

    private void updateV(byte data1, byte data2, byte result) {
        if (data1 > 0 && data2 > 0) {
            setV(result < 0);
        } else if (data1 < 0 && data2 < 0) {
            setV(result > 0);
        }
    }

}
