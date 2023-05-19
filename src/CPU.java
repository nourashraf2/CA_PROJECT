import java.io.IOException;
import java.util.Hashtable;

public class CPU {
   private InstructionMemory instructionMemory;
   private byte[] dataMemory;
   private byte[] registerFile;
   private SREG sreg;
   private int pc;
   private Hashtable pipeline;

   public CPU() throws CpuException {
      this.instructionMemory = new InstructionMemory();
      this.dataMemory = new byte[2048];
      registerFile = new byte[64];
      sreg = new SREG();
      pc = 0;

   }

   public Word16 fetch() {
      Word16 wr = this.instructionMemory.getBlock()[pc];
      pc++;
      return wr;
   }

   public Hashtable decode(String instruction) { // 16 bits
      Hashtable<String, Object> hashtable = new Hashtable<>();

      String opcodeString = instruction.substring(0, 4);
      String r1String = instruction.substring(4, 10);
      String r2String = instruction.substring(10);

      int opcode = Integer.parseInt(opcodeString, 2);
      int r1Address = Integer.parseInt(r1String, 2);

      hashtable.put("opcode", opcode);
      hashtable.put("destination", r1Address);
      hashtable.put("r1", registerFile[r1Address]);

      if (opcode < 3 || (opcode >= 5 && opcode <= 7)) { // R type
         int r2Address = Integer.parseInt(r2String, 2);
         hashtable.put("r2", registerFile[r2Address]);
      } else { // I Type
         hashtable.put("r2", twosBinaryStringToInt(r2String));
      }
      return hashtable;
   }

   public int twosBinaryStringToInt(String binString) { // 1100110
      int numOfBits = binString.length();
      int result = Integer.parseInt(binString.substring(1), 2);
      if (binString.charAt(0) == '1') {
         result -= Math.pow(2, numOfBits - 1);
      }
      return result;
   }

   public void execute(Hashtable<String, Object> hashtable) throws CpuException {
      // Execute ALU operation
      //
      int opcode = (int) hashtable.get("opcode");
      byte data1 = (byte) hashtable.get("r1");
      byte data2 = (byte) hashtable.get("r2");
      int destination = (int) hashtable.get("destination");
      boolean jump = false;
      byte result = 0; // 8 bits

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
               jump = true;
               pc += data2;
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
            pc = concatenateByte(data1, data2);
            return;
         case 8: // CIRC LEFT
            result = (byte) Integer.rotateLeft(data1, data2);
            sreg.updateSREG(data1, data2, result, '<');
            break;
         case 9: // CIRC RIGHT
            result = (byte) Integer.rotateRight(data1, data2);
            sreg.updateSREG(data1, data2, result, '>');
            break;
         case 10: // LOAD BYTE
            result = dataMemory[data2];
            break;
         case 11: // STORE BYTE
            dataMemory[data2] = data1;
            return;
         default:
            throw new CpuException("Invalid Opcode");
      }

      registerFile[destination] = result;

   }

   // private byte concatenateByte(byte data1, byte data2) {
   // return 0;
   // }

   public void run(int clockCycles) {
      for (int i = 0; i < clockCycles; i++) {

         fetch(pc);
         decode("");
         execute();
         pc++;
         // instruction from memory -> fetch
         // update pipeline fetch -> decode, decode -> execute
      }
   }

   public static void main(String[] args) {
      try {
         CPU cpu = new CPU();
         cpu.instructionMemory.loadMemory("instructions.txt");
         // System.out.println(cpu.fetch(cpu.pc));
         cpu.instructionMemory.displayMemory();

      } catch (CpuException e) {
         System.out.println(e.getMessage());
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }

   }

}
