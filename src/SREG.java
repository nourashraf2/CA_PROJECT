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
        printSREG(arr);
    }

    public static void printSREG(int[] arr) {
        int i = 0;
        System.out.print("SREG: ");
        HomeScreen.textAreaRight.append("SREG: ");
        for (int b : arr) {
            switch (i) {
                case 3:
                    System.out.print("c: ");
                    HomeScreen.textAreaRight.append("c: ");
                    break;
                case 4:
                    System.out.print("v: ");
                    HomeScreen.textAreaRight.append("v: ");
                    break;
                case 5:
                    System.out.print("n: ");
                    HomeScreen.textAreaRight.append("n: ");
                    break;
                case 6:
                    System.out.print("s: ");
                    HomeScreen.textAreaRight.append("s: ");
                    break;
                case 7:
                    System.out.print("z: ");
                    HomeScreen.textAreaRight.append("z: ");
                    break;
                default:
                    System.out.print("_: ");
                    HomeScreen.textAreaRight.append("_: ");
                    break;
            }
            HomeScreen.textAreaRight.append(b + " ");
            System.out.print(b + " ");
            i++;
        }
        HomeScreen.textAreaRight.append("\n");
        System.out.println("\n");
    }

    public static void printToReg(int[] arr) {
        int i = 0;
        System.out.print("SREG: ");
        HomeScreen.textAreaReg.append("SREG: ");
        for (int b : arr) {
            switch (i) {
                case 3:
                    System.out.print("c: ");
                    HomeScreen.textAreaReg.append("c: ");
                    break;
                case 4:
                    System.out.print("v: ");
                    HomeScreen.textAreaReg.append("v: ");
                    break;
                case 5:
                    System.out.print("n: ");
                    HomeScreen.textAreaReg.append("n: ");
                    break;
                case 6:
                    System.out.print("s: ");
                    HomeScreen.textAreaReg.append("s: ");
                    break;
                case 7:
                    System.out.print("z: ");
                    HomeScreen.textAreaReg.append("z: ");
                    break;
                default:
                    System.out.print("_: ");
                    HomeScreen.textAreaReg.append("_: ");
                    break;
            }
            HomeScreen.textAreaReg.append(b + " ");
            System.out.print(b + " ");
            i++;
        }
        HomeScreen.textAreaReg.append("\n");
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
