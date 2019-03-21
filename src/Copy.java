import java.io.*;
import org.apache.commons.io.FileUtils;

class Copy {
    Copy() throws IOException {

        File srcDir1 = new File("/Users/Ian/Desktop/Project2_test_and_page_files/page_files/");
        File destDir1 = new File("/Users/Ian/Desktop/page_files/");
        FileUtils.copyDirectory(srcDir1, destDir1);

        File srcDir2 = new File("/Users/Ian/Desktop/Project2_test_and_page_files/test_files/");
        File destDir2 = new File("/Users/Ian/Desktop/test_files/");
        FileUtils.copyDirectory(srcDir2, destDir2);
    }
}
