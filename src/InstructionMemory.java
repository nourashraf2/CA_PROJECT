import java.io.*;

public class InstructionMemory {
    private Word16[] block;

    public InstructionMemory() throws CpuException {
        this.block = new Word16[1024];
        // for(int i=0;i<1024;i++) {
        // block[i]=0;
        //
        // }
    }

    public void displayMemory() {
        int counter = 0;
        for (Word16 a : this.block) {
            if (a != null) {
                System.out.println("Block " + counter + " " + a.toString());
                counter++;
            } else {
                System.out.println("Block " + counter + " " + "null");
                counter++;
            }
        }
    }

    public void loadMemory(String filepath) throws IOException, CpuException {
        File file = new File(filepath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        int counter = 0;
        while ((line = bufferedReader.readLine()) != null) {

            String[] instruction = line.split(" ");
            String binaryInstruction = "";
            switch (instruction[0]) {
                case ("ADD"):
                    binaryInstruction = "0000" + registerEncode(instruction[1]) + registerEncode(instruction[2]);
                    break;
                case ("SUB"):
                    binaryInstruction = "0001" + registerEncode(instruction[1]) + registerEncode(instruction[2]);
                    break;
                case ("MUL"):
                    binaryInstruction = "0010" + registerEncode(instruction[1]) + registerEncode(instruction[2]);
                    break;
                case ("LDI"):
                    binaryInstruction = "0011" + registerEncode(instruction[1]) + intEncode(instruction[2]);
                    break;
                case ("BEQZ"):
                    binaryInstruction = "0100" + registerEncode(instruction[1]) + intEncode(instruction[2]);
                    break;
                case ("AND"):
                    binaryInstruction = "0101" + registerEncode(instruction[1]) + registerEncode(instruction[2]);
                    break;
                case ("OR"):
                    binaryInstruction = "0110" + registerEncode(instruction[1]) + registerEncode(instruction[2]);
                    break;
                case ("JR"):
                    binaryInstruction = "0111" + registerEncode(instruction[1]) + registerEncode(instruction[2]);
                    break;
                case ("SLC"):
                    binaryInstruction = "1000" + registerEncode(instruction[1]) + intEncode(instruction[2]);
                    break;
                case ("SRC"):
                    binaryInstruction = "1001" + registerEncode(instruction[1]) + intEncode(instruction[2]);
                    break;
                case ("LB"):
                    binaryInstruction = "1010" + registerEncode(instruction[1]) + intEncode(instruction[2], "");
                    break;
                case ("SB"):
                    binaryInstruction = "1011" + registerEncode(instruction[1]) + intEncode(instruction[2], "");
                    break;
                default:
                    throw new CpuException();

            }
            // System.out.println(binaryInstruction);

            block[counter] = new Word16(binaryInstruction);
            counter++;

        }

    }

    public static String intEncode(String str, String nonNegative) throws CpuException { // takes str number "2" convert
                                                                                         // it into binary
                                                                                         // number used for immediate
                                                                                         // values
        int num = Integer.parseInt(str);
        if (num >= 0 && num <= 63) {
            String binaryString = Integer.toBinaryString(num);
            int numBits = 6; // number of bits to represent the binary number
            String paddedBinaryString = String.format("%" + numBits + "s", binaryString).replace(' ', '0');
            return paddedBinaryString;
        } else {
            throw new CpuException("Invalid number number should be between 0 and 63 inclusive");

        }

    }

    public static String intEncode(String str) throws CpuException { // takes str number "2" convert it into binary
                                                                     // number used for immediate values
        int num = Integer.parseInt(str);
        if (num < 0 && num >= -32) {
            return Integer.toBinaryString(num).substring(26, 32);
        } else if (num >= 0 && num <= 31) {
            String binaryString = Integer.toBinaryString(num);
            int numBits = 6; // number of bits to represent the binary number
            String paddedBinaryString = String.format("%" + numBits + "s", binaryString).replace(' ', '0');
            return paddedBinaryString;
        } else {
            throw new CpuException("Invalid number number should be between -32 and 31 inclusive");

        }

    }

    public static String registerEncode(String str) { // takes register R1 and convert it into a binary 000001

        if (str.length() == 3) {
            String temp = str.charAt(1) + "" + str.charAt(2);
            int registerNum = Integer.parseInt(temp);
            return toBitsUnsigned(registerNum);
        } else {
            String temp = str.charAt(1) + "";
            int registerNum = Integer.parseInt(temp);
            return toBitsUnsigned(registerNum);

        }
    }

    public static String toBitsUnsigned(int num) { // takes an int a convert it into binary
        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            sb.append((num >>> i) & 1);
        }
        return sb.toString().substring(26, 32); //
    }

    public static void main(String[] args) throws CpuException, IOException {
        InstructionMemory instructionMemory = new InstructionMemory();
        instructionMemory.loadMemory("instructions.txt");
        instructionMemory.displayMemory();

    }

    public Word16[] getBlock() {
        return block;
    }
}
