import java.io.IOException;

public class CPU {
  private  InstructionMemory instructionMemory;
  private  DataMemory dataMemory;
  private Word8[]  registerFile;
  private SREG sreg;
  private int pc;

   public CPU() throws CpuException {
      this.instructionMemory=new InstructionMemory();
      this.dataMemory=new DataMemory();
      registerFile=new Word8[64];
      sreg=new SREG();
      pc=0;

   }

   public Word16 fetch(int currPC){
      Word16 wr=this.instructionMemory.getBlock()[currPC];
      return wr;

   }
//   public Hashtable decode(String instruction){
//      Hashtable hashtable=new Hashtable<>();
//
//
//
//
//
//   }


    //Execute

    //run
    //3 + ((n − 1) ∗ 1)
    //for(int i=0;i<clockCycles;i++){
    //
    //
    //
    //
    //
    //
    //
    // }
   public static void main(String[] args) {
      try {
         CPU cpu=new CPU();
         cpu.instructionMemory.loadMemory("instructions.txt");
//         System.out.println(cpu.fetch(cpu.pc));
        cpu.instructionMemory.displayMemory();

      } catch (CpuException e) {
         System.out.println(e.getMessage());
      } catch (IOException e) {
         System.out.println(e.getMessage());
      }


   }

}
