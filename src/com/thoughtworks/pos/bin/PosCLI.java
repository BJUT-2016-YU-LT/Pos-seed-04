package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.UserInfo;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.services.services.VIPinputParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingCartException {

        ShoppingChart shoppingChart ;

        String shoppingList ;


        InputParser inputParser = new InputParser(new File(args[0]), new File(args[1]));
       shoppingChart = inputParser.parser();
        VIPinputParser viPinputParser=new VIPinputParser(new File(args[2]));

        UserInfo userInfos=viPinputParser.parser(inputParser.getUserBar());
     Pos pos = new Pos();
       shoppingList = pos.getShoppingListAndVIPcard(shoppingChart,userInfos);
       System.out.print("builded from file\n");
        System.out.print(shoppingList);



    }
}
