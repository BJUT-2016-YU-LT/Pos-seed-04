package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.domains.Item;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PosCLI {
    public static void main (String args[]) throws IOException, EmptyShoppingCartException {
        Item cokeCola = new Item("ITEM000000", "可口可乐", "瓶", 3.00,0.8);
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(cokeCola);
        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart);
        System.out.print("build from simple input\n");
        System.out.print(shoppingList);


        InputParser inputParser = new InputParser(new File(args[0]), new File(args[1]));
       shoppingChart = inputParser.parser();

      pos = new Pos();
       shoppingList = pos.getShoppingList(shoppingChart);
       System.out.print("builded from file\n");
        System.out.print(shoppingList);
    }
}
