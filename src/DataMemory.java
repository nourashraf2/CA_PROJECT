public class DataMemory {
    private Word8[] block;
    public DataMemory() throws CpuException {
        this.block=new Word8[2048];

    }

    public Word8[] getBlock() {
        return block;
    }

//    public void setBlock(Short[] block) {
//        this.block = block;
//    }
}
