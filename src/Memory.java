public class Memory {

    int[][] mem;

    Memory(){
        mem = new int[16][256];
    }

    public int getData(int intAddress, int intOffset) {
        return mem[intAddress][intOffset];
    }

    public void setData(int pageNum, int intOffset, String data) {
        int new_data = Integer.parseInt(data);
        mem[pageNum][intOffset] = new_data;
    }
}
