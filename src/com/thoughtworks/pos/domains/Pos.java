package com.thoughtworks.pos.domains;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.services.services.ReportDataGenerator;
import com.thoughtworks.pos.domains.UserInfo;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/6/22.
 */
public class Pos {
    static public boolean VIPactive=false;
    public String getShoppingList(ShoppingChart shoppingChart) throws EmptyShoppingCartException {

        Report report = new ReportDataGenerator(shoppingChart).generate();
        boolean needPromote=false;
        StringBuilder shoppingListBuilder = new StringBuilder()
                .append("***商店购物清单***\n");


        for (ItemGroup itemGroup : report.getItemGroupies()) {
            if(!needPromote&&itemGroup.GroupIsPromotion()&&itemGroup.groupSize()>1)
                needPromote=true;
            shoppingListBuilder.append(

                    new StringBuilder()
                            .append("名称：").append(itemGroup.groupName()).append("，")
                            .append("数量：").append(itemGroup.groupSize()).append(itemGroup.groupUnit()).append("，")
                            .append("单价：").append(String.format("%.2f", itemGroup.groupPrice())).append("(元)").append("，")
                            .append("小计：").append(String.format("%.2f", itemGroup.subTotal())).append("(元)").append("\n")
                            .toString());
        }

        if(needPromote){
            shoppingListBuilder
                    .append("----------------------\n")
                    .append("挥泪赠送商品：\n");
            for (ItemGroup itemGroup : report.getItemGroupies()) {
            if(itemGroup.GroupIsPromotion()){
                shoppingListBuilder.append(
                        new StringBuilder()
                                .append("名称：").append(itemGroup.groupName()).append("，")
                                .append("数量：").append("1").append(itemGroup.groupUnit()).append("\n")
                                .toString());
            }

            }}

        StringBuilder subStringBuilder = shoppingListBuilder
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", report.getTotal())).append("(元)").append("\n");

        double saving = report.getSaving();
        if (saving == 0) {
            return subStringBuilder
                    .append("**********************\n")
                    .toString();
        }
        return subStringBuilder
                .append("节省：").append(String.format("%.2f", saving)).append("(元)").append("\n")
                .append("**********************\n")
                .toString();
    }


    public String getShoppingListAndVIPcard(ShoppingChart shoppingChart,UserInfo userInfo) throws EmptyShoppingCartException {
       VIPactive= userInfo.getUserIsVIP();
        Report report = new ReportDataGenerator(shoppingChart).generate();
        boolean needPromote=false;
        StringBuilder shoppingListBuilder = new StringBuilder()
                .append("***商店购物清单***\n");

        if(userInfo.getUserIsVIP()) {
            shoppingListBuilder.append("会员编号:").append(userInfo.getUserBar()+" ")
                    .append("会员积分:").append(String.valueOf(userInfo.ChangePoint(report.getTotal()))).append("\n")
                    .append("----------------------\n" ).toString();}

       Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        shoppingListBuilder.append(time.toString());
        for (ItemGroup itemGroup : report.getItemGroupies()) {
            if(!needPromote&&itemGroup.GroupIsPromotion()&&itemGroup.groupSize()>1)
                needPromote=true;
            shoppingListBuilder.append(

                    new StringBuilder()
                            .append("名称：").append(itemGroup.groupName()).append("，")
                            .append("数量：").append(itemGroup.groupSize()).append(itemGroup.groupUnit()).append("，")
                            .append("单价：").append(String.format("%.2f", itemGroup.groupPrice())).append("(元)").append("，")
                            .append("小计：").append(String.format("%.2f", itemGroup.subTotal())).append("(元)").append("\n")
                            .toString());
        }

        if(needPromote){
            shoppingListBuilder
                    .append("----------------------\n")
                    .append("挥泪赠送商品：\n");
            for (ItemGroup itemGroup : report.getItemGroupies()) {
                if(itemGroup.GroupIsPromotion()){
                    shoppingListBuilder.append(
                            new StringBuilder()
                                    .append("名称：").append(itemGroup.groupName()).append("，")
                                    .append("数量：").append("1").append(itemGroup.groupUnit()).append("\n")
                                    .toString());
                }

            }}

        StringBuilder subStringBuilder = shoppingListBuilder
                .append("----------------------\n")
                .append("总计：").append(String.format("%.2f", report.getTotal())).append("(元)").append("\n");


        double saving = report.getSaving();
        if (saving == 0) {
            return subStringBuilder
                    .append("**********************\n")
                    .toString();
        }
        return subStringBuilder
                .append("节省：").append(String.format("%.2f", saving)).append("(元)").append("\n")
                .append("**********************\n")
                .toString();
    }
}
