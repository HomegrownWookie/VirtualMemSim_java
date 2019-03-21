public class TLB {

    private TLBEntry[] tlb;
    private int pointer = 0;

    TLB(){
        tlb = new TLBEntry[8];
    }

    public boolean present(int address){
        for(int i = 0; i < 8; i++){
            if(tlb[i] == null)
                continue;

            int temp = tlb[i].getvPageNumber();

            if(temp == address)
                return true;
        }
        return false;
    }

    public void addPage(TLBEntry entry){
        tlb[pointer] = entry;
        pointer++;
        if(pointer == 8)
            pointer = 0;
    }

    public int getPageNumber(int intAddress) {
        int pageNum = -1;
        for(int i = 0; i < 8; i++){
            if(intAddress == tlb[i].getvPageNumber())
                pageNum = tlb[i].getPageFrameNumber();
        }

        return pageNum;
    }

    public void setRefBit(int intAddress, int b) {
        for(int i = 0; i < 8; i++){
            if(intAddress == tlb[i].getvPageNumber())
                tlb[i].setReference_bit(b);
        }
    }

    public void resetRefBit(int index, int b){
        tlb[index].setReference_bit(b);
    }

    public void setValBit(int intAddress, int b) {
        for(int i = 0; i < 8; i++){
            if(intAddress == tlb[i].getvPageNumber())
                tlb[i].setValid_bit(b);
        }
    }

    public void setDirtyBit(int intAddress, int b) {
        for(int i = 0; i < 8; i++){
            if(intAddress == tlb[i].getvPageNumber())
                tlb[i].setDirty_bit(b);
        }
    }
}
