/**
 * Created by pyggy on 2016/6/26.
 */

import com.thoughtworks.pos.domains.ShoppingChart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.domains.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class VIPtest {
    private File indexFile;
    private File itemsFile;

    @Before
    public void setUp(){
        indexFile = new File("./sampleIndex.json");
        itemsFile = new File("./itemsFile.json");
    }
    @After
    public void tearDown() throws Exception {
        if(indexFile.exists()){
            indexFile.delete();
        }
        if(itemsFile.exists()){
            itemsFile.delete();
        }
    }
    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }



}
