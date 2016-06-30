
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.UserInfo;
import com.thoughtworks.pos.services.services.VIPinputParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.domains.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pyggy on 2016/6/30.
 */
public class VipInvolvedTest {
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
    public void ShowVIPinfoCorrectWhenNotVIP()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"vipDiscount\": 0.80\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0002',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +time.toString()
                        + "名称：袜子，数量：2双，单价：5.00(元)，小计：10.00(元)\n"
                        + "----------------------\n"
                        + "总计：10.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void ShowVIPinfoCorrectWhenSimpleSituation()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0001',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0001 会员积分:102\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：2双，单价：5.00(元)，小计：10.00(元)\n"
                        + "----------------------\n"
                        + "总计：10.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void ShowVIPinfoCorrectWhendDiscount()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"discount\": 0.80\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0001',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0001 会员积分:101\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：2双，单价：5.00(元)，小计：8.00(元)\n"
                        + "----------------------\n"
                        + "总计：8.00(元)\n"
                        + "节省：2.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void ShowVIPinfoCorrectWhendVIPDiscount()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"vipDiscount\": 0.80\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0001',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0001 会员积分:101\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：2双，单价：5.00(元)，小计：8.00(元)\n"
                        + "----------------------\n"
                        + "总计：8.00(元)\n"
                        + "节省：2.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void ShowVIPinfoCorrectWhendDIscountAndVIPDiscount()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"discount\": 0.80,\n")
                .append("\"vipDiscount\": 0.90\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0001',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0001 会员积分:101\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：2双，单价：5.00(元)，小计：7.20(元)\n"
                        + "----------------------\n"
                        + "总计：7.20(元)\n"
                        + "节省：2.80(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void ShowVIPinfoPromotionCorrectWhenCountInsufficient()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0001',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
       String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0001 会员积分:101\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：1双，单价：5.00(元)，小计：5.00(元)\n"
                        + "----------------------\n"
                        + "总计：5.00(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }

    @Test
    public void ShowInfoVIPPromotionCorrectWhenSingleType() throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0004',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0004 会员积分:306\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：3双，单价：5.00(元)，小计：10.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：袜子，数量：1双\n"
                        + "----------------------\n"
                        + "总计：10.00(元)\n"
                        + "节省：5.00(元)\n"
                        + "**********************\n";

        assertThat(actualShoppingList, is(expectedShoppingList));

    }

    @Test
    public void ShowVIPinfoPromoteCorrectWhenMultiplyTypes()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000001':{\n")
                .append("\"name\": '可乐',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 3.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0005',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0005 会员积分:615\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：3双，单价：5.00(元)，小计：10.00(元)\n"
                        + "名称：可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：袜子，数量：1双\n"
                        + "名称：可乐，数量：1瓶\n"
                        + "----------------------\n"
                        + "总计：16.00(元)\n"
                        + "节省：8.00(元)\n"
                        + "**********************\n";

        assertThat(actualShoppingList, is(expectedShoppingList));

    }

    @Test
    public void ShowVIPinfoPromoteAndDiscountCorrectWhenBoth()throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000001':{\n")
                .append("\"name\": '脉动',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 5.00,\n")
                .append("\"discount\": 0.80\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0005',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0005 会员积分:615\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：3双，单价：5.00(元)，小计：10.00(元)\n"
                        + "名称：脉动，数量：2瓶，单价：5.00(元)，小计：8.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：袜子，数量：1双\n"
                        + "----------------------\n"
                        + "总计：18.00(元)\n"
                        + "节省：7.00(元)\n"
                        + "**********************\n";

        assertThat(actualShoppingList, is(expectedShoppingList));


    }
    @Test
    public void ShowVIPinfoPromoteCorrectWhenOnePromotedWhileOthersAreNot() throws Exception{
        //given
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '袜子',\n")
                .append("\"unit\": '双',\n")
                .append("\"price\": 5.00,\n")
                .append("\"promotion\": true\n")
                .append("},\n")
                .append("'ITEM000001':{\n")
                .append("\"name\": '脉动',\n")
                .append("\"unit\": '瓶',\n")
                .append("\"price\": 5.00\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("'user':'USER0005',\n")
                .append("'items':[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000001\",\n")
                .append("\"ITEM000000\"\n")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        InputParser inputParser = new InputParser(indexFile,itemsFile);
        ShoppingChart shoppingChart = inputParser.parser();
        //when
        VIPinputParser viPinputParser=new VIPinputParser(userFile);

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
        // then
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String expectedShoppingList =
                "***商店购物清单***\n"
                        +"会员编号:USER0005 会员积分:620\n"
                        +"----------------------\n"
                        +time.toString()
                        + "名称：袜子，数量：3双，单价：5.00(元)，小计：10.00(元)\n"
                        + "名称：脉动，数量：2瓶，单价：5.00(元)，小计：10.00(元)\n"
                        + "----------------------\n"
                        +"挥泪赠送商品：\n"
                        + "名称：袜子，数量：1双\n"
                        + "----------------------\n"
                        + "总计：20.00(元)\n"
                        + "节省：5.00(元)\n"
                        + "**********************\n";

        assertThat(actualShoppingList, is(expectedShoppingList));
}
}
