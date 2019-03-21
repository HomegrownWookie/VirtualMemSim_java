import java.io.*;

public class OS {

    public Memory mem;
    public PageTable pageTable;
    private CPU cpu;
    public TLB tlb;
    private Node current;
    private linkedList linked;
    private CLL cll;
    private String evPageNum;

    OS() {
        cll = new CLL();
        current = cll.getStart();
        clock();
        tlb = new TLB();
        pageTable = new PageTable();
        mem = new Memory();
    }

    private void clock() {
        while(true) {
            int r_bit = current.getRefBit();
            if (r_bit == 1)
                current.setRefBit(0);
            else if(r_bit == 0)
                break;

            current = current.getLink();
        }
    }

    private void evictPage() {
        if (current.getRefBit() != 0)
            clock();

        evPageNum = current.getAddress();
        if(evPageNum == null)
            return;
        pageTable.setValBit(Integer.parseInt(evPageNum, 16), 0);
        int dirty = pageTable.getDirtyBit(Integer.parseInt(evPageNum, 16));

        if (dirty == 1) {
            writeToDisk(evPageNum);
        }

        int page = Integer.parseInt(evPageNum, 16);
        for(int i = 0; i < 256; i++) {
            mem.mem[page][i] = 0;
        }
    }

    public void recentlyUsed(String address){
        Node temp1 = current;
        while(temp1.getLink() != current){
            if(temp1.getAddress().equalsIgnoreCase(address)) {
                temp1.setRefBit(1);
                break;
            } else
                temp1 = temp1.getLink();
        }
    }

    public void addTLBpage(String address){
        TLBEntry entry = new TLBEntry(address, 1, 1, 0, current.index);
        tlb.addPage(entry);
    }

    public void addPTPage(String address) {
        PageTableEntry entry = new PageTableEntry(1, 1, 0, current.index);
        pageTable.addPage(entry, Integer.parseInt(address, 16));
    }

    public String getEVPageNum() {
        return evPageNum;
    }

    public String getEVDirtyBit(String address) {
        int intAddress = Integer.parseInt(address, 16);
        int dirtyBit = pageTable.getDirtyBit(intAddress);
        return Integer.toString(dirtyBit);
    }

    public void reset(){
        for(int i = 0; i < 8; i++){
            tlb.resetRefBit(i, 0);
        }
        for(int j = 0; j < 256; j++){
            pageTable.setRefBit(j, 0);
        }
        Node temp1 = current;
        while(temp1.getLink() != current){
            temp1.setRefBit(0);
            temp1 = temp1.getLink();
        }
    }

    public void writePage(String address){
        evictPage();
        loadPage(address);
    }

    private void loadPage(String address){

        clock();
        int pageNum = current.index;

        String filename = address.toUpperCase() + ".pg";
        String filepath = "/Users/Ian/Desktop/page_files/" + filename;

        String line;
        int counter = 0;

        try {
            FileReader fileReader = new FileReader(filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                int current = Integer.parseInt(line);
                mem.mem[pageNum][counter] = current;
                counter++;
            }
            bufferedReader.close();

        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");

        } catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
        }

        current.setAddress(address);
        current.setRefBit(1);

        current = current.getLink();
    }

    private void writeToDisk(String address) {
        String filename = address.toUpperCase() + ".pg";
        String filepath = "/page_files/" + filename;

        int page = Integer.parseInt(evPageNum, 16);

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filepath)));
            for(int i = 0; i < 256; i++){
                writer.println(mem.mem[page][i]);
            }
            writer.close();

        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");

        } catch(IOException ex) {
            System.out.println("Error writing to file '" + filename + "'");
        }
    }
}

class Node {
    protected int index;
    private String address;
    private int refBit;
    private Node link;

    public Node(){
        index = 0;
        address = null;
        refBit = 0;
        link = null;
    }

    Node(int ind, String addr, int r_Bit, Node lin){
        index = ind;
        address = addr;
        refBit = r_Bit;
        link = lin;
    }


    public void setIndex(int ind){
        index = ind;
    }

    public void setAddress(String addr){
        address = addr;
    }

    public void setRefBit(int r_bit){
        refBit = r_bit;
    }
    public void setLink(Node n){
        link = n;
    }

    public Node getLink(){
        return link;
    }

    public int getIndex(){
        return index;
    }

    public String getAddress(){
        return address;
    }

    public int getRefBit(){
        return refBit;
    }
}

class linkedList{
    protected Node start ;
    private Node end ;
    private int size ;

    /* Constructor */
    linkedList(){
        start = null;
        end = null;
        size = 0;
    }

    public void insertAtStart(int ind){
        Node nptr = new Node(ind, null, 0, null);
        nptr.setLink(start);
        if(start == null){
            start = nptr;
            nptr.setLink(start);
            end = start;
        } else{
            end.setLink(nptr);
            start = nptr;
        }
        size++ ;
    }

    public void insertAtEnd(int ind){
        Node nptr = new Node(ind, null, 0, null);
        nptr.setLink(start);
        if(start == null){
            start = nptr;
            nptr.setLink(start);
            end = start;
        } else{
            end.setLink(nptr);
            end = nptr;
        }
        size++ ;
    }

    public void insertAtPos(int ind, String addr, int pos){
        Node nptr = new Node(ind, addr, 0, null);
        Node ptr = start;
        pos = pos - 1 ;
        for (int i = 0; i < size - 1; i++){
            if (i == pos){
                Node tmp = ptr.getLink() ;
                ptr.setLink( nptr );
                nptr.setLink(tmp);
                break;
            }
            ptr = ptr.getLink();
        }
        size++;
    }
}

class CLL{
    public linkedList list;

    CLL(){
        list = new linkedList();
        list.insertAtStart(0);
        list.insertAtEnd(15);
        for(int i = 1; i < 15; i++){
            list.insertAtPos(i, null, i);
        }
    }

    public Node getStart(){
        return list.start;
    }
}
