
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

/**
 * Created by pyggy on 2016/6/23.
 */
public class ReadFileTest {
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
    public void PromotionCorrectWhenCountInsufficient()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '可口可乐',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 3.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：1瓶，单价：3.00(元)，小计：3.00(元)\n"
                        + "----------------------\n"
                        + "总计：3.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    @Test
    public void PromotionCorrectWhenSingleType() throws Exception{
       //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '可口可乐',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 3.00,\n")
                .append("\"promotion\": true\n")
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
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：可口可乐，数量：1瓶\n"
                        + "----------------------\n"
                        + "总计：6.00(元)\n"
                        + "节省：3.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));

    }

    @Test
   public void PromoteCorrectWhenMultiplyTypes()throws Exception{
        //given
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
                .append("\"promotion\": true\n")
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
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "名称：雪碧，数量：2听，单价：2.00(元)，小计：2.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：可口可乐，数量：1瓶\n"
                        + "名称：雪碧，数量：1听\n"
                        + "----------------------\n"
                        + "总计：8.00(元)\n"
                        + "节省：5.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));

    }

    @Test
    public void PromoteAndDiscountCorrectWhenBoth()throws Exception{
        //given
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
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "名称：雪碧，数量：2听，单价：2.00(元)，小计：3.20(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：可口可乐，数量：1瓶\n"
                        + "----------------------\n"
                        + "总计：9.20(元)\n"
                        + "节省：3.80(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));


    }
    @Test
    public void PromoteCorrectWhenOnePromotedWhileOthersAreNot() throws Exception{
        //given
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
                .append("\"price\": 2.00\n")
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
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "名称：雪碧，数量：2听，单价：2.00(元)，小计：4.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：可口可乐，数量：1瓶\n"
                        + "----------------------\n"
                        + "总计：10.00(元)\n"
                        + "节省：3.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));

    }
}
