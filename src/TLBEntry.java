public class TLBEntry {
    private int vPageNumber = 0;
    private int pageFrameNumber = 0;
    private int valid_bit = 0;
    private int reference_bit = 0;
    private int dirty_bit = 0;

    TLBEntry(String vPageNumber, int valid_bit, int reference_bit, int dirty_bit, int pageFrameNumber){
        this.vPageNumber = Integer.parseInt(vPageNumber, 16);
        this.pageFrameNumber = pageFrameNumber;
        this.valid_bit = valid_bit;
        this.reference_bit = reference_bit;
        this.dirty_bit = dirty_bit;
    }

    public void setvPageNumber(String vPageNumber) {
        this.vPageNumber = Integer.parseInt(vPageNumber, 16);
    }

    public void setvPageNumber(int vPageNumber){
        this.vPageNumber = vPageNumber;
    }

    public void setFrameNumber(int pageFrameNumber){
        this.pageFrameNumber = pageFrameNumber;
    }

    public void setValid_bit(int valid_bit){
        this.valid_bit = valid_bit;
    }

    public void setReference_bit(int reference_bit) {
        this.reference_bit = reference_bit;
    }

    public void setDirty_bit(int dirty_bit) {
        this.dirty_bit = dirty_bit;
    }

    public int getvPageNumber() {
        return vPageNumber;
    }

    public int getValid_bit() {
        return valid_bit;
    }

    public int getReference_bit() {
        return reference_bit;
    }

    public int getDirty_bit() {
        return dirty_bit;
    }

    public int getPageFrameNumber() { return pageFrameNumber; }
}
