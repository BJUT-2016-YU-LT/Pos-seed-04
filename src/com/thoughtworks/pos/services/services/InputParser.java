package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class InputParser {
    private File indexFile;
    private File itemsFile;
    private final ObjectMapper objectMapper;

    public InputParser(File indexFile, File itemsFile) {
        this.indexFile = indexFile;
        this.itemsFile = itemsFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException {
        return BuildShoppingChart(getBoughtItemBarCodes(), getItemIndexes());
    }

    private ShoppingChart BuildShoppingChart(String[] barCodes, HashMap<String, Item> itemIndexes) {
        ShoppingChart shoppingChart = new ShoppingChart();
        for (String barcode : barCodes) {

            Item mappedItem = itemIndexes.get(barcode);
            Item item;
            if(mappedItem.getIsPromotion())
                item = new Item(barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), true);
            else{
             item = new Item(barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice());
           if(mappedItem.getDiscount()!=1.00)
               item.setDiscount(mappedItem.getDiscount());
           if(mappedItem.getVipDiscount()!=1.00)
            item.setVipDiscount(mappedItem.getVipDiscount());}
            shoppingChart.add(item);
        }
        return shoppingChart;
    }
    public String getUserBar() throws IOException{
        return getUserBar(itemsFile);
    }
    private String  getUserBar(File file)throws IOException{
        List<String> strings=FileUtils.readLines(file);
        String result=new String();
   for(int i=0;i<strings.get(1).length()-2;i++) {
       if (i >= 8)
           result += strings.get(1).charAt(i);

   }
        return result;
    }
    private String[] getBoughtItemBarCodes() throws IOException {

        List<String> strings=FileUtils.readLines(itemsFile);
        String itemString="[";
        int n=0;
       for(String string:strings){
           if(n>=3)
               itemString+=string;
           n+=1;
       }
        itemString+="]";
        return objectMapper.readValue(itemString, String[].class);
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(indexFile);
        TypeReference<HashMap<String,Item>> typeRefer = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRefer);
    }

}
