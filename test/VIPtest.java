/**
 * Created by pyggy on 2016/6/26.
 */

import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.VIPcard;
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
    @Test
    public void GetCorrectPointsWhenNormal()throws Exception{
//given
        VIPcard vipCard=new VIPcard("ME0001","熊大",1000);
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '可口可乐',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 3.00\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,vipCard);
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：9.00(元)\n"
                        + "----------------------\n"
                        +"您目前的会员积分：1900，增加：900\n"
                        + "----------------------\n"
                        + "总计：9.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void GetCorrectPointsWhenPromoteAndDiscount()throws Exception{
        //given
        VIPcard vipCard=new VIPcard("ME0001","熊大",1000);
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '可口可乐',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 3.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000001':{\n")
                .append("\"name\": '雪碧',\n")
                .append("\"unit\": '听',\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.8\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000001\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,vipCard);
        // then

        assertThat(vipCard.getPoints(), is(1920));
    }
}
