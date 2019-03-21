import sun.jvm.hotspot.debugger.Page;

import java.io.*;

class CPU {

    private TLB tlb;
    private OS os;
    private PageTable pt;
    private Memory mem;
    private Copy copy;
    private String testFileName;
    public static FileWriter writer;

    CPU(String[] args) throws IOException {
        //String filename = args[0];
        String filename = "/Users/Ian/Desktop/test_files/test_1.txt";
        this.copy = new Copy();
        this.os = new OS();
        tlb = os.tlb;
        pt = os.pageTable;
        mem = os.mem;
        Main(filename);
    }

    private void Main(String filename){
        String line;
        String current;
        String address;
        String data;
        int counter = 0;

        String[] parts = filename.split("/");
        String temp = parts[parts.length-1];
        char[] arr = temp.toCharArray();
        testFileName = "test_";
        testFileName += arr[5];

        try {
            FileReader fileReader = new FileReader(filename);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                current = line;

                if(current.equals("0")) {
                    address = bufferedReader.readLine();
                    MMU(address);

                } else if(current.equals("1")){
                    address = bufferedReader.readLine();
                    data = bufferedReader.readLine();
                    MMU(address, data);
                }

                counter++;
                if(counter % 5 == 0)
                    os.reset();
            }
            bufferedReader.close();

        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");

        } catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
        }
    }

    private void MMU(String newLine) throws IOException {

        String hardMiss = "0";
        String softMiss = "0";
        String hit = "0";
        String evictedPageNum = null;
        String dirty = "0";
        String address = newLine.substring(0, 2);
        String offset = newLine.substring(2, 4);
        int intAddress = Integer.parseInt(address, 16);
        int intOffset = Integer.parseInt(offset, 16);

        //check if page is in TLB & PT
        boolean tlbPresent = tlb.present(intAddress);
        boolean ptPresent = pt.present(intAddress);


        if(!tlbPresent && !ptPresent){
            os.addTLBpage(address);
            os.addPTPage(address);
            os.writePage(address);
            hardMiss = "1";
            evictedPageNum = os.getEVPageNum();
            if(evictedPageNum == null) {
                evictedPageNum = "null";
                dirty = "null";
            }else
                dirty = os.getEVDirtyBit(evictedPageNum);

        } else if(!tlbPresent && ptPresent){
            os.addTLBpage(address);
            softMiss = "1";
        } else if(tlbPresent && ptPresent){
            hit = "1";
        }

        int pageNum = tlb.getPageNumber(intAddress);
        int idata = mem.getData(pageNum, intOffset);
        String sdata = Integer.toString(idata);

        tlb.setRefBit(intAddress, 1);
        tlb.setValBit(intAddress, 1);
        pt.setRefBit(intAddress, 1);
        pt.setValBit(intAddress, 1);
        os.recentlyUsed(address);

        cvsPrint(address, "0", sdata, softMiss, hardMiss, hit, evictedPageNum, dirty);
    }

    private void MMU(String newLine, String data) throws IOException {
        String hardMiss = "0";
        String softMiss = "0";
        String hit = "0";
        String evictedPageNum = null;
        String dirty = "0";
        String address = newLine.substring(0, 2);
        String offset = newLine.substring(2, 4);
        int intAddress = Integer.parseInt(address, 16);
        int intOffset = Integer.parseInt(offset, 16);

        //check if page is in TLB & PT
        boolean tlbPresent = tlb.present(intAddress);
        boolean ptPresent = pt.present(intAddress);


        if(!tlbPresent && !ptPresent){
            os.addTLBpage(address);
            os.addPTPage(address);
            hardMiss = "1";
            evictedPageNum = os.getEVPageNum();
            if(evictedPageNum == null) {
                evictedPageNum = "null";
                dirty = "null";
            }else
                dirty = os.getEVDirtyBit(evictedPageNum);
        } else if(!tlbPresent && ptPresent){
            os.addTLBpage(address);
            softMiss = "1";
        } else if(tlbPresent && ptPresent){
            hit = "1";
        }

        int pageNum = tlb.getPageNumber(intAddress);
        mem.setData(pageNum, intOffset, data);

        tlb.setRefBit(intAddress, 1);
        tlb.setValBit(intAddress, 1);
        tlb.setDirtyBit(intAddress, 1);
        pt.setRefBit(intAddress, 1);
        pt.setValBit(intAddress, 1);
        pt.setDirtyBit(intAddress, 1);
        os.recentlyUsed(address);

        cvsPrint(address, "1", data, softMiss, hardMiss, hit, evictedPageNum, dirty);
    }

    private void cvsCreate() throws IOException {
        testFileName = testFileName + ".cvs";

        writer = new FileWriter(testFileName);
        writer.append("Address,Read/Write,Data,Soft_Miss,Hard_Miss,Hit,Evicted_Page_Number,Dirty_Bit");
        writer.append("\n");

    }

    private void cvsPrint(String address, String readWrite, String data, String softMiss, String hardMiss, String hit,
                          String evictedPageNum, String dirty) throws IOException {
        String delimiter = ",";

        writer.append(address);
        writer.append(delimiter);
        writer.append(readWrite);
        writer.append(delimiter);
        writer.append(data);
        writer.append(delimiter);
        writer.append(softMiss);
        writer.append(delimiter);
        writer.append(hardMiss);
        writer.append(delimiter);
        writer.append(hit);
        writer.append(delimiter);
        writer.append(evictedPageNum);
        writer.append(delimiter);
        writer.append(dirty);
        writer.append("\n");
    }

}

