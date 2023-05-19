import java.io.IOException;
import java.util.Hashtable;

public class CPU {
   private InstructionMemory instructionMemory;
   private DataMemory dataMemory;
   private Word8[] registerFile;
   private SREG sreg;
   private int pc;
   private Hashtable pipeline;

   public CPU() throws CpuException {
      this.instructionMemory = new InstructionMemory();
      this.dataMemory = new DataMemory();
      registerFile = new Word8[64];
      sreg = new SREG();
      pc = 0;

   }

   public Word16 fetch(int currPC) {
      Word16 wr = this.instructionMemory.getBlock()[currPC];
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
      hashtable.put("r1", registerFile[r1Address]);

      if (opcode < 3 || (opcode >= 5 && opcode <= 7)) { // R type
         int r2Address = Integer.parseInt(r2String, 2);
         hashtable.put("r2", registerFile[r2Address]);
      } else { // I Type
         hashtable.put("r2", twosBinaryStringToInt(r2String, 6));
      }
      return hashtable;
   }

   public int twosBinaryStringToInt(String binString) { // 1100110
      int numOfBits = binString.length();
      int result = Integer.parseInt(binString.substring(1), 2);
      if (binString.charAt(0) == '1') {
         result -= Math.pow(2, numOfBits-1);
      } 
      return result;
   }

   public void execute() {
      // Execute ALU operation
      //
   }

   // Execute

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
