public class Word8 {
   private String wr;


    public Word8(String wr) throws CpuException {
        if(wr.length()!=8)
            throw new CpuException("word size is not 8 bits please enter a word of 8 bits");
        for(int i=0;i<wr.length();i++) {
            if (wr.charAt(i) !='0'&& wr.charAt(i)!='1' )
                throw new CpuException("please enter a word of 0 and 1 only");
        }
        this.wr=wr;


    }


    @Override
    public String toString() {
        return this.wr.toString();
    }
}
