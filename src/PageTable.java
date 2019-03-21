public class PageTable {

    private PageTableEntry[] pageTable;
    private OS os;

    PageTable(){
        pageTable = new PageTableEntry[256];
    }

    public boolean present(int intAddress) {
        boolean present = false;
        boolean valid = false;

        if(pageTable[intAddress] == null)
            return false;
        if(pageTable[intAddress].getPageFrameNumber() != -1)
            present = true;
        if(pageTable[intAddress].getValid_bit() == 1)
            valid = true;
        else {
            String address = Integer.toHexString(intAddress);
            os.writePage(address);
        }

        if(present == true && valid == true){
            return true;
        }
        return false;
    }

    public void addPage(PageTableEntry entry, int intAddress){
        pageTable[intAddress] = entry;
    }

    public void setValBit(int intAddress, int i) {
        pageTable[intAddress].setValid_bit(i);
    }

    public void setRefBit(int intAddress, int i) {
        pageTable[intAddress].setReference_bit(i);
    }

    public void setDirtyBit(int intAddress, int i) {
        pageTable[intAddress].setDirty_bit(i);
    }

    public int getValBit(int intAddress){
        return pageTable[intAddress].getValid_bit();
    }

    public int getRefBit(int intAddress){
        return pageTable[intAddress].getReference_bit();
    }

    public int getDirtyBit(int intAddress){
        return pageTable[intAddress].getDirty_bit();
    }


}
