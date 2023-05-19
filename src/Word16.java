public class Word16 {
    String wr;

    public Word16(String wr) throws CpuException {
        if (wr.length() != 16)
            throw new CpuException("word size is not 8 bits please enter a word of 8 bits");
        for (int i = 0; i < wr.length(); i++) {
            if (wr.charAt(i) != '0' && wr.charAt(i) != '1')
                throw new CpuException("please enter a word of 0 and 1 only");
        }
        this.wr = wr;
    }

    public String getWord16() {
        return wr;
    }

    @Override
    public String toString() {
        return this.wr.toString();
    }
}
