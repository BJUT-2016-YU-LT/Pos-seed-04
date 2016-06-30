/**
 * Created by pyggy on 2016/6/26.
 */

import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.services.services.VIPinputParser;
import com.thoughtworks.pos.domains.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class VIPtest {
    private File indexFile;
    private File itemsFile;
    private File userFile;

    @Before
    public void setUp()throws IOException{
        indexFile = new File("./sampleIndex.json");
        itemsFile = new File("./itemsFile.json");
        userFile=new File("./userFile.json");
        String userInfos=new StringBuilder()
                .append("{\n")
                .append("'USER0001':{\n")
                .append("\"name\":'USER 001',\n")
                .append("\"isVIP\":true,\n")
                .append("\"points\":100\n")
                .append("},\n")
                .append("'USER0002':{\n")
                .append("\"name\":'USER 002',\n")
                .append("\"isVIP\":false,\n")
                .append("\"points\":0\n")
                .append("},\n")
                .append("'USER0003':{\n")
                .append("\"name\":'USER 003',\n")
                .append("\"isVIP\":true,\n")
                .append("\"points\":0\n")
                .append("},\n")
                .append("'USER0004':{\n")
                .append("\"name\":'USER 004',\n")
                .append("\"isVIP\":true,\n")
                .append("\"points\":300\n")
                .append("},\n")
                .append("'USER0005':{\n")
                .append("\"name\":'USER 005',\n")
                .append("\"isVIP\":true,\n")
                .append("\"points\":600\n")
                .append("}\n")
                .append("}\n")
                .toString();
       WriteToFile(userFile,userInfos);
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000001':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.8\n")
                .append("},\n")
                .append("'ITEM000002':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 3.00,\n")
                .append("\"vipDiscount\": 0.9\n")
                .append("},\n")
                .append("'ITEM000003':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"discount\": 0.8,\n")
                .append("\"vipDiscount\": 0.9\n")
                .append("},\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '雪碧',\n")
                .append("\"unit\": '听',\n")
                .append("\"price\": 2.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

    }
    @After
    public void tearDown() throws Exception {
        if(indexFile.exists()){
            indexFile.delete();
        }
        if(itemsFile.exists()){
            itemsFile.delete();
        }
        if(userFile.exists()){
            userFile.delete();
        }
    }
    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }

@Test
    public void testCountPointsCorrectWhenBetween0and200()throws Exception{
    //given
    String sampleItems = new StringBuilder()
            .append("{\n")
            .append("'user':'USER0001',\n")
            .append("'items':[\n")
            .append("\"ITEM000004\",\n")
            .append("\"ITEM000004\",\n")
            .append("\"ITEM000004\",\n")
            .append("\"ITEM000003\",\n")
            .append("\"ITEM000003\",\n")
            .append("\"ITEM000003\",\n")
            .append("\"ITEM000002\"\n")
            .append("]")
            .toString();
    WriteToFile(itemsFile, sampleItems);
    //then
    ShoppingChart shoppingChart ;




    InputParser inputParser = new InputParser(indexFile, itemsFile);
    shoppingChart = inputParser.parser();
    VIPinputParser viPinputParser=new VIPinputParser(userFile);

    UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
    Pos pos = new Pos();
    pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
   assertThat(userInfos.getUserPoints(),is(103));
}
    @Test
    public void testCountPointsCorrectWhenBetween200and500()throws Exception{
        //given
        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0004',\n")
                .append("'items':[\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000003\",\n")
                .append("\"ITEM000003\",\n")
                .append("\"ITEM000003\",\n")
                .append("\"ITEM000002\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);
        //then
        ShoppingChart shoppingChart ;

        String shoppingList ;


        InputParser inputParser = new InputParser(indexFile, itemsFile);
        shoppingChart = inputParser.parser();
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        assertThat(userInfos.getUserPoints(),is(309));
    }
    @Test
    public void testCountPointsCorrectWhenMoreThan500()throws Exception{
        //given
        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0005',\n")
                .append("'items':[\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000003\",\n")
                .append("\"ITEM000003\",\n")
                .append("\"ITEM000003\",\n")
                .append("\"ITEM000002\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);
        //then
        ShoppingChart shoppingChart ;

        String shoppingList ;


        InputParser inputParser = new InputParser(indexFile, itemsFile);
        shoppingChart = inputParser.parser();
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        assertThat(userInfos.getUserPoints(),is(615));
    }

}
