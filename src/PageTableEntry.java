public class PageTableEntry {
    private int pageFrameNumber;
    private int valid_bit;
    private int reference_bit;
    private int dirty_bit;

    PageTableEntry(int valid_bit, int reference_bit, int dirty_bit, int pageFrameNumber){
        this.pageFrameNumber = pageFrameNumber;
        this.valid_bit = valid_bit;
        this.reference_bit = reference_bit;
        this.dirty_bit = dirty_bit;
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

    public int getValid_bit() {
        return valid_bit;
    }

    public int getReference_bit() {
        return reference_bit;
    }

    public int getDirty_bit() {
        return dirty_bit;
    }

    public int getPageFrameNumber() {
        return pageFrameNumber;
    }
}
