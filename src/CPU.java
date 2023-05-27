import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.crypto.Data;

public class CPU {
   InstructionMemory instructionMemory;
   private byte[] dataMemory;
   private byte[] registerFile;
   private SREG sreg;
   private short pc;
   Vector<Object> fetched = null;
   Hashtable<String, Object> decoded = null;

   public CPU() throws CpuException {
      this.instructionMemory = new InstructionMemory();
      this.dataMemory = new byte[2048];
      registerFile = new byte[64];
      sreg = new SREG();
      pc = 0;

   }

   public Vector<Object> fetch() {
      HomeScreen.textAreaRight.append("fetch has no parameters as a function but uses the instance variable pc: " + pc + "\n");
      System.out.println("fetch has no parameters as a function but uses the instance variable pc: " + pc + "\n");
      Word16 wr = this.instructionMemory.getBlock()[pc];
      Vector<Object> toDecode = new Vector<>();
      toDecode.add(wr);
      toDecode.add(pc);
      pc++;
      return toDecode;
   }

   public Hashtable<String, Object> decode() { // 16 bits
      Hashtable<String, Object> hashtable = new Hashtable<>();

      HomeScreen.textAreaRight.append("Decode parameters: 1- instruction: " + fetched.get(0) + "\n");
      HomeScreen.textAreaRight.append("Decode parameters: 2- instruction address (old pc): " + fetched.get(1) + "\n");
      System.out.println("Decode parameters: 1- instruction: " + fetched.get(0));
      System.out.println("Decode parameters: 2- instruction address (old pc): " + fetched.get(1) + "\n");

      String instruction = ((Word16) fetched.get(0)).getWord16();

      String opcodeString = instruction.substring(0, 4);
      String r1String = instruction.substring(4, 10);
      String r2String = instruction.substring(10);

      int opcode = Integer.parseInt(opcodeString, 2);
      int r1Address = Integer.parseInt(r1String, 2);

      hashtable.put("opcode", opcode);
      hashtable.put("destination", r1Address);
      hashtable.put("r1", (byte) registerFile[r1Address]);

      if (opcode < 3 || (opcode >= 5 && opcode <= 7)) { // R type
         int r2Address = Integer.parseInt(r2String, 2);
         hashtable.put("r2", (byte) registerFile[r2Address]);
      } else { // I Type
         hashtable.put("r2", (byte) twosBinaryStringToInt(r2String, opcode));
      }
      hashtable.put("pc", fetched.get(1));
      return hashtable;
   }

   public int twosBinaryStringToInt(String binString, int opcode) { // 1100110
      int numOfBits = binString.length();
      if (opcode >= 10) {
         return Integer.parseInt(binString.substring(0), 2);
      }
      int result = Integer.parseInt(binString.substring(1), 2);
      if (binString.charAt(0) == '1') {
         result -= Math.pow(2, numOfBits - 1);
      }
      return result;
   }

   public int execute() throws CpuException {
      // Execute ALU operation
      //
      HomeScreen.textAreaRight.append("instruction currently executing: " + decoded.get("pc") + "\n");
      System.out.println("instruction currently executing: " + decoded.get("pc") + "\n");
      int opcode = (int) decoded.get("opcode");
      byte data1 = (byte) decoded.get("r1");
      byte data2 = (byte) decoded.get("r2");
      int destination = (int) decoded.get("destination");
      // boolean jump = false;
      byte result = 0; // 8 bits
      System.out.println("Execute parameters: 1- opcode: " + opcode);
      System.out.println("Execute parameters: 2- R1: " + data1);
      System.out.println("Execute parameters: 3- R2/Imm: " + data2);
      System.out.println("Execute parameters: 4- destination address: " + destination);
      System.out.println("Execute parameters: 5- instruction address (old pc): " + decoded.get("pc") + "\n");

      
      HomeScreen.textAreaRight.append("Execute parameters: 1- opcode: " + opcode + "\n");
      HomeScreen.textAreaRight.append("Execute parameters: 2- R1: " + "\n");
      HomeScreen.textAreaRight.append("Execute parameters: 3- R2/Imm: " + data2 + "\n");
      HomeScreen.textAreaRight.append("Execute parameters: 4- destination address: " + destination + "\n");
      HomeScreen.textAreaRight.append("Execute parameters: 5- instruction address (old pc): " + decoded.get("pc") + "\n");
      switch (opcode) {
         case 0: // ADD
            result = (byte) (data1 + data2);
            sreg.updateSREG(data1, data2, result, '+');
            break;
         case 1: // SUBTRACT
            result = (byte) (data1 - data2);
            sreg.updateSREG(data1, data2, result, '-');
            break;
         case 2: // MULTIPLY
            result = (byte) (data1 * data2);
            sreg.updateSREG(data1, data2, result, '*');
            break;
         case 3: // LOAD IMM
            result = data2;
            break;
         case 4: // BRANCH IF ZERO
            if (data1 == 0) {
               pc = (short) ((short) decoded.get("pc") + data2 + 1);
               fetched = null;
               decoded = null;
               if (pc < 0) {
                  throw new CpuException("\n\n\n\nPC IS SMALLER THAN 0");
               }
               HomeScreen.textAreaRight.append("New PC value: " + pc + "\n");
               System.out.println("New PC value: " + pc + "\n");
            }
            break;
         case 5: // AND
            result = (byte) (data1 & data2);
            sreg.updateSREG(data1, data2, result, '&');
            break;
         case 6: // OR
            result = (byte) (data1 | data2);
            sreg.updateSREG(data1, data2, result, '|');
            break;
         case 7: // JUMP
            fetched = null;
            decoded = null;
            pc = concatenateByte(data1, data2);
            if (pc < 0) {
               throw new CpuException("\n\n\n\nPC IS SMALLER THAN 0");
            }
            HomeScreen.textAreaRight.append("New PC value: " + pc + "\n");
            System.out.println("pc new value: " + pc + "\n");
            return pc;
         case 8: // CIRC LEFT
//          result = (byte) Integer.rotateLeft(data1, data2);
//          sreg.updateSREG(data1, data2, result, '>');
            result = (byte) Integer.rotateLeft((byte)(data1 & 0xFF),(byte) (data2 & 0xFF));
            sreg.updateSREG(data1, (byte) (data2 & 0xFF), result, '<');
            break;
         case 9: // CIRC RIGHT
//            result = (byte) Integer.rotateRight(data1, data2);
//            sreg.updateSREG(data1, data2, result, '>');
            result = (byte) Integer.rotateRight((byte)(data1 & 0xFF),(byte) (data2 & 0xFF));
            sreg.updateSREG(data1, (byte) (data2 & 0xFF), result, '<');
            break;
         case 10: // LOAD BYTE
            result = dataMemory[data2];
            break;
         case 11: // STORE BYTE
            dataMemory[data2] = data1;
            HomeScreen.textAreaRight.append("M[" + data2 + "] is now: " + data1 + "\n");
            System.out.println("M[" + data2 + "] is now: " + data1 + "\n");
            return pc;
         default:
            throw new CpuException("Invalid Opcode");
      }

      registerFile[destination] = result;

      HomeScreen.textAreaRight.append("Register " + destination + " is now: " + result + "\n");
      System.out.println("register " + destination + " is now: " + result + "\n");

      return -1;
   }



   private short concatenateByte(byte data1, byte data2) {

      short temp1 = (short) (data1 & 0xFF);
      short temp2 = (short) (data2 & 0xFF);

      temp1 = (short) ((temp1 << 8) | temp2);

      return temp1;
   }

   public void run() throws CpuException {
      int clockCycles = 0;
      System.out.println("\n\n------------------------------------------\n");
      HomeScreen.textAreaRight.append("\n\n------------------------------------------\n");
      for (int i = 0; i < 3 + ((instructionMemory.actualSize - 1) * 1); i++) {
         System.out.println("clock cycle: " + clockCycles + "\n");
         HomeScreen.textAreaRight.append("Clock cycle: " + clockCycles + "\n");
         int j = tryExecute();
         if (j != -1) {
            i = j;
         }
         decoded = tryDecode();
         fetched = tryFetch();
         HomeScreen.textAreaRight.append("\n------------------------------------------\n");
         System.out.println("\n------------------------------------------\n");
         clockCycles++;
      }

      System.out.println("\n");

      for (int i = 0; i < registerFile.length; i++) {
         HomeScreen.textAreaReg.append("Register " + i + " = " + registerFile[i] + "\n");
         System.out.println("Register " + i + " = " + registerFile[i]);
      }

      System.out.println();
      SREG.toString(sreg.arr);

      System.out.println("\n\nData Memory: \n\n");
      displayMemory();

      System.out.println("\n\nInstruction Memory: \n\n");
      instructionMemory.displayMemory();
   }

   private int tryExecute() throws CpuException {
      if (decoded != null) {
         return execute();
      }
      return -1;
   }

   private Hashtable<String, Object> tryDecode() {
      if (fetched != null) {
         return decode();
      }
      return null;
   }

   public Vector<Object> tryFetch() {
      Vector<Object> v = fetch();
      if (v != null && v.get(0) != null) {
         fetched = v;
      } else {
         fetched = null;
      }
      return fetched;
   }

   public void displayMemory() {
      int counter = 0;
      for (byte a : dataMemory) {
         HomeScreen.textAreaDataMem.append("Block " + counter + " " + a + "\n");
         System.out.println("Block " + counter + " " + a);
         counter++;
      }
   }

   public static void printBinary(byte value) {
      for (int i = 7; i >= 0; i--) {
         int bit = (value >> i) & 1;
         System.out.print(bit);
      }
      System.out.println();
   }

   public static void main(String[] args) {
      try {
         CPU cpu = new CPU();
         cpu.registerFile[1]=3;  //00000011   11110111   ->>> 0000   0001100 0001101
         cpu.registerFile[2]=4;  //0000 0100  11111011   1111 1100  00111111
         cpu.instructionMemory.loadMemory("CA/instructions.txt");
         cpu.run();
      } catch (CpuException e) {
         System.out.println(e.getMessage());
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }

   }

}
